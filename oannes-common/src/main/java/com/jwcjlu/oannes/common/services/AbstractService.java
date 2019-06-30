package com.jwcjlu.oannes.common.services;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public abstract class AbstractService implements Service {
    private Logger logger = LoggerFactory.getLogger(AbstractService.class);
    private STATE state = STATE.NOTINITED;
    private final Object stateChangeLock = new Object();
    private long startTime;
    private Set<ServiceListener> serviceListenerList = Collections.synchronizedSet(new HashSet<ServiceListener>());
    private final boolean[][] statemap =
            {
                    //                uninited inited started stopped
                    /* uninited  */    {false, true, false, true},
                    /* inited    */    {false, true, true, true},
                    /* started   */    {false, false, true, true},
                    /* stopped   */    {false, false, false, true},
            };

    public STATE currentState() {
        return state;
    }

    public boolean isInState(Service.STATE proposed) {

        return state.equals(proposed);
    }

    @Override
    public void initialize() throws Throwable {

        if (isInState(STATE.INITED)) {
            return;
        }
        synchronized (stateChangeLock) {
            if (state != STATE.INITED) {
                try {
                    serviceInit();
                    transferState(STATE.INITED);
                    if (isInState(STATE.INITED)) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("Service " + getName() + " is inited");
                        }
                        notifyListeners(ServiceAction.INIT);
                    }
                } catch (Exception e) {
                    noteFailure(e);
                }
            }
        }
    }

    @Override
    public void boot() {
        if (isInState(STATE.STARTED)) {
            return;
        }
        synchronized (stateChangeLock) {

            try {
                startTime = System.currentTimeMillis();
                serviceStart();
                transferState(STATE.STARTED);
                if (isInState(STATE.STARTED)) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Service " + getName() + " is started");
                    }
                    notifyListeners(ServiceAction.BOOT);
                }
            } catch (Exception e) {
                noteFailure(e);
            }
        }
    }

    protected final void noteFailure(Exception exception) {
        logger.info("Service " + getName()
                        + " failed in state " + state
                        + "; cause: " + exception,
                exception);

    }

    private void notifyListeners(ServiceAction action) {
        for (ServiceListener serviceListener : serviceListenerList) {
            try {
                serviceListener.notifyListener(this, action);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

    }

    public void registerListener(ServiceListener listener) {
        serviceListenerList.add(listener);
    }

    /**
     * @throws IOException
     */
    public void shutdown() throws IOException {
        synchronized (stateChangeLock) {
            if (state != STATE.STOPPED) {
                try {
                    serviceStop();
                    transferState(STATE.STOPPED);
                    if (isInState(STATE.STOPPED)) {
                        notifyListeners(ServiceAction.STOPED);
                    }
                } catch (Exception e) {
                    noteFailure(e);
                }
            }
        }
    }

    protected void transferState(STATE newState) {
        if (isVaildState(newState)) {
            state = newState;
        }

    }

    private boolean isVaildState(STATE state) {
        return statemap[currentState().getValue()][state.getValue()];

    }

    @Override
    public void onComplete() {
        notifyListeners(ServiceAction.COMPELTE);
        if (isInState(STATE.STARTED)) {
            BootServiceManager.INSTANCE.registerService(this);
        }

    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public void serviceInit() {

    }

    @Override
    public void serviceStart() {

    }

    @Override
    public void serviceStop() {

    }

    @Override
    public long getStartTime() {
        return startTime;
    }

}
