package com.jwcjlu.oannes.transport.clientpool;


import com.oannes.common.URL;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public enum ClientPoolManager {
    INSTANCE;
    private ConcurrentMap<URL, ClientPool> clientPools;
    private EventLoopGroup executors=new NioEventLoopGroup();

    ClientPoolManager() {
         clientPools = new ConcurrentHashMap<>(200);
    }

    public ClientPool getClientPool(URL server) {
        return getClientPool(server, null);
    }

    /**
     * 得到某个服务的连接池
     *
     * @param server
     * @param poolConfig
     * @return
     */
    public ClientPool getClientPool(URL server, GenericObjectPoolConfig poolConfig) {
        ClientPool clientPool = clientPools.computeIfAbsent(server, s -> {
                return new DefaultClientPool(server, poolConfig, executors);
            }

        );
        return clientPool;
    }

    public void shutdown() {
        if (Objects.isNull(clientPools)) {
            return;
        }
        clientPools.values().forEach(client -> client.shutdown());
    }
    public void  close(URL url){
        getClientPool(url).shutdown();
    }


    public void setExecutors(EventLoopGroup executors) {
        this.executors = executors;
    }

}
