package com.jwcjlu.oannes.transport.clientpool;


import com.jwcjlu.oannes.common.services.BootServiceManager;
import com.jwcjlu.oannes.transport.Client;
import com.oannes.common.URL;
import io.netty.channel.EventLoopGroup;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.Objects;

public class DefaultClientPool implements ClientPool {
    private final URL origin;
    private GenericObjectPool<Client> clientPool;
    private GenericObjectPoolConfig poolConfig;
    private BasePooledObjectFactory<Client> factory;

    public DefaultClientPool(URL server, GenericObjectPoolConfig poolConfig, EventLoopGroup eventExecutors) {
        this.origin = server;
        this.factory = new ClientFactory(server, this, eventExecutors);
        this.poolConfig=poolConfig;
        if (Objects.isNull(poolConfig)) {
           this.poolConfig= BootServiceManager.INSTANCE.findBootService(DefaultGenericObjectPoolConfig.class).getDefaultGenericObjectPoolConfig();
        }
        this.clientPool = new GenericObjectPool(factory, this.poolConfig,
        BootServiceManager.INSTANCE.findBootService(DefaultGenericObjectPoolConfig.class).getDefaultAbandonedConfig());
    }


    @Override
    public Client borrowObject( long timeout) throws Exception {
        Client client = clientPool.borrowObject();
        client.startReadTimeoutHandler(timeout);
        return client;
    }

    @Override
    public Client borrowObject() throws Exception {
        Client client = clientPool.borrowObject();
        client.startReadTimeoutHandler();
        return client;
    }

    @Override
    public void returnObject(Client obj) {
        clientPool.returnObject(obj);
    }

    @Override
    public void invalidateObject(Client obj) throws Exception {
        clientPool.invalidateObject(obj);
    }

    @Override
    public void shutdown() {
        clientPool.close();
    }

}
