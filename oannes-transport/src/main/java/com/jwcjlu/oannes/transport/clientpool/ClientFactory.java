package com.jwcjlu.oannes.transport.clientpool;


import com.jwcjlu.oannes.transport.Client;
import com.jwcjlu.oannes.transport.NettyClient;
import com.oannes.common.URL;
import io.netty.channel.EventLoopGroup;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClientFactory extends BasePooledObjectFactory<Client> {
    private Logger logger= LoggerFactory.getLogger(ClientFactory.class);

    private final URL origin;
    private final ClientPool clientPool;
    private EventLoopGroup executors;

    public ClientFactory(URL origin, ClientPool clientPool, EventLoopGroup eventExecutorse) {
        this.origin = origin;
        this.clientPool = clientPool;
        this.executors = eventExecutorse;
    }

    @Override
    public Client  create() throws Exception {
        logger.info("create connection pool");
        return new NettyClient(origin, this, executors);
    }

    @Override
    public PooledObject<Client> wrap( Client obj) {
        return new DefaultPooledObject(obj);
    }


    @Override
    public void destroyObject(PooledObject<Client> p) throws Exception {
        logger.info("destroy connection pool");
        System.out.println("destroy connection pool");
        p.getObject().disconnect();
    }

    @Override
    public boolean validateObject(PooledObject<Client> p) {
        try {
            return (p == null || p.getObject() == null) ? false : p.getObject().isConnected();
        }catch (Throwable t){
            return false;
        }
    }

    public ClientPool getClientPool() {
        return clientPool;
    }


}
