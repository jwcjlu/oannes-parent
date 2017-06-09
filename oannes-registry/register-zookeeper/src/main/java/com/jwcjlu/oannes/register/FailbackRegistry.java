package com.jwcjlu.oannes.register;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.jwcjlu.oannes.register.listener.NotifyListener;
import com.oannes.common.ConcurrentHashSet;
import com.oannes.common.Constants;
import com.oannes.common.URL;
import com.oannes.common.threadpool.NamedThreadFactory;

public abstract class FailbackRegistry extends AbstractRegister{
	private Logger logger=Logger.getLogger(FailbackRegistry.class);
	private final ScheduledFuture retryFuture;
	  // 定时任务执行器
    private final ScheduledExecutorService retryExecutor = Executors.newScheduledThreadPool(1, new NamedThreadFactory("OannesRegistryFailedRetryTimer", true));
   
    private final Set<URL> failedRegistered = new ConcurrentHashSet<URL>();

    private final Set<URL> failedUnregistered = new ConcurrentHashSet<URL>();

    private final ConcurrentMap<URL, Set<NotifyListener>> failedSubscribed = new ConcurrentHashMap<URL, Set<NotifyListener>>();

    private final ConcurrentMap<URL, Set<NotifyListener>> failedUnsubscribed = new ConcurrentHashMap<URL, Set<NotifyListener>>();

    private final ConcurrentMap<URL, Map<NotifyListener, List<URL>>> failedNotified = new ConcurrentHashMap<URL, Map<NotifyListener, List<URL>>>();


    
  
    public FailbackRegistry(URL url){
    	super(url);
        int retryPeriod = url.getParameter(Constants.REGISTRY_RETRY_PERIOD_KEY, Constants.DEFAULT_REGISTRY_RETRY_PERIOD);
        this.retryFuture = retryExecutor.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                // 检测并连接注册中心
                try {
                    retry();
                } catch (Throwable t) { // 防御性容错
                    logger.error("Unexpected error occur at failed retry, cause: " + t.getMessage(), t);
                }
            }
        }, retryPeriod, retryPeriod, TimeUnit.MILLISECONDS);
    }
    
    @Override
    protected void notify(URL url, NotifyListener listener, List<URL> urls) {
        if (url == null) {
            throw new IllegalArgumentException("notify url == null");
        }
        if (listener == null) {
            throw new IllegalArgumentException("notify listener == null");
        }
        try {
        	doNotify(url, listener, urls);
        } catch (Exception t) {
            // 将失败的通知请求记录到失败列表，定时重试
            Map<NotifyListener, List<URL>> listeners = failedNotified.get(url);
            if (listeners == null) {
                failedNotified.putIfAbsent(url, new ConcurrentHashMap<NotifyListener, List<URL>>());
                listeners = failedNotified.get(url);
            }
            listeners.put(listener, urls);
            logger.error("Failed to notify for subscribe " + url + ", waiting for retry, cause: " + t.getMessage(), t);
        }
    }
	@Override
	public void register(URL url) throws Exception {
		// TODO Auto-generated method stub
	    doRegister(url);

	}

	@Override
	public void unRegister(URL url) throws Exception {
		// TODO Auto-generated method stub
	    doUnregister(url);
	}

	@Override
	public void subscribe(List<URL> urls, NotifyListener listener) {
		// TODO Auto-generated method stub
		for(URL url:urls){
			try {
				doSubscribe(url, listener);
			} catch (RpcException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			}
		}
		
	}

	@Override
	public void unSubscribe(List<URL> urls, NotifyListener listener) {
		// TODO Auto-generated method stub
		for(URL url:urls){
			doUnsubscribe(url, listener);
		}
	}
	public void destroy(){
		retryFuture.cancel(true);
	}
	private void retry(){
		if(!CollectionUtils.isEmpty(failedRegistered)){
			Set<URL> fails=new HashSet<URL>(failedRegistered);
			for(URL url:fails){
				try{
					doRegister(url);
					failedRegistered.remove(url);
				}catch(Exception e){
					failedRegistered.add(url);
				}
			}
			
		}
		if(!CollectionUtils.isEmpty(failedUnregistered)){
			Set<URL> fails=new HashSet<URL>(failedUnregistered);
			for(URL url:fails){
				try{
					doUnregister(url);
					failedUnregistered.remove(url);
				}catch(Exception e){
					failedUnregistered.add(url);
				}
			}
			
		}
		if(!CollectionUtils.isEmpty(failedSubscribed)){
			Set<Entry<URL,Set<NotifyListener>>> entryskeys= 	failedSubscribed.entrySet();
			for(Entry<URL,Set<NotifyListener>> entry:entryskeys){
				Set<NotifyListener>listeners=entry.getValue();
				URL url=entry.getKey();
				if(CollectionUtils.isEmpty(listeners)){
					failedSubscribed.remove(entry.getKey());
				}
				
				for(NotifyListener listener:listeners){
					try {
						doSubscribe(entry.getKey(), listener);
						listeners.remove(listener);
					} catch (RpcException e) {
						// TODO Auto-generated catch block
						
					}
				}
			}
		}
		if(!CollectionUtils.isEmpty(failedUnsubscribed)){
			Set<Entry<URL,Set<NotifyListener>>> entryskeys= 	failedUnsubscribed.entrySet();
			for(Entry<URL,Set<NotifyListener>> entry:entryskeys){
				Set<NotifyListener>listeners=entry.getValue();
				URL url=entry.getKey();
				if(CollectionUtils.isEmpty(listeners)){
					failedUnsubscribed.remove(entry.getKey(), entry.getValue());
				}
				
				for(NotifyListener listener:listeners){
					try {
						doUnsubscribe(entry.getKey(), listener);
						listeners.remove(listener);
					} catch (Exception e) {
					
					}
				}
			}
		}
		if(!CollectionUtils.isEmpty(failedNotified)){
			  Map<URL, Map<NotifyListener, List<URL>>> failed = new HashMap<URL, Map<NotifyListener, List<URL>>>(failedNotified);
	            for (Map.Entry<URL, Map<NotifyListener, List<URL>>> entry : new HashMap<URL, Map<NotifyListener, List<URL>>>(failed).entrySet()) {
	                if (entry.getValue() == null || entry.getValue().size() == 0) {
	                    failed.remove(entry.getKey());
	                }
	            }
	            if (failed.size() > 0) {
	                if (logger.isInfoEnabled()) {
	                    logger.info("Retry notify " + failed);
	                }
	                try {
	                    for (Map<NotifyListener, List<URL>> values : failed.values()) {
	                        for (Map.Entry<NotifyListener, List<URL>> entry : values.entrySet()) {
	                            try {
	                                NotifyListener listener = entry.getKey();
	                                List<URL> urls = entry.getValue();
	                                listener.notify(urls);
	                                values.remove(listener);
	                            } catch (Throwable t) { // 忽略所有异常，等待下次重试
	                                logger.warn("Failed to retry notify " + failed + ", waiting for again, cause: " + t.getMessage(), t);
	                            }
	                        }
	                    }
	                } catch (Throwable t) { // 忽略所有异常，等待下次重试
	                    logger.warn("Failed to retry notify " + failed + ", waiting for again, cause: " + t.getMessage(), t);
	                }
	            }
		}
		  
	}
	public Set<URL> getFailedRegistered() {
		return failedRegistered;
	}

	public Set<URL> getFailedUnregistered() {
		return failedUnregistered;
	}

	public ConcurrentMap<URL, Set<NotifyListener>> getFailedSubscribed() {
		return failedSubscribed;
	}

	public ConcurrentMap<URL, Set<NotifyListener>> getFailedUnsubscribed() {
		return failedUnsubscribed;
	}

	public ConcurrentMap<URL, Map<NotifyListener, List<URL>>> getFailedNotified() {
		return failedNotified;
	}
    // ==== 模板方法 ====

    protected abstract void doRegister(URL url) throws Exception;

    

	protected abstract void doUnregister(URL url) throws Exception;

    protected abstract void doSubscribe(URL url, NotifyListener listener) throws RpcException;

    protected abstract void doUnsubscribe(URL url, NotifyListener listener);
    

}
