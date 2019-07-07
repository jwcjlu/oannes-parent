package com.jwcjlu.oannes.transport.clientpool;


import com.jwcjlu.oannes.common.services.BootService;
import com.oannes.common.util.ConfigUtils;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.Properties;

public  class DefaultGenericObjectPoolConfig implements BootService {
    private Properties properties=new Properties();

    public  GenericObjectPoolConfig getDefaultGenericObjectPoolConfig() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(ConfigUtils.getInt(properties,"oannes.connetionpool.maxTotal",20));
        //最大空闲连接
        config.setMaxIdle(ConfigUtils.getInt(properties,"oannes.connetionpool.maxIdle",10));
        //最小空闲连接
        config.setMinIdle(ConfigUtils.getInt(properties,"oannes.connetionpool.minIdle",1));
        //连接满时最多等待时间
        config.setMaxWaitMillis(5000L);
        // 高级功能：
        //使用时检查对象（默认不检查）
        config.setTestWhileIdle(true);
        config.setTestOnCreate(true);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        //jmx启用 之后可以实时的查看线程池对象的状态
        config.setJmxEnabled(false);
        config.setJmxNameBase("clientPool");
        config.setJmxNamePrefix("clientPool_");
        //驱逐线程每次检查对象个数
        config.setNumTestsPerEvictionRun(2);
        //空闲连接被驱逐前能够保留的时间
        config.setMinEvictableIdleTimeMillis(10000L);
        //当空闲线程大于minIdle 空闲连接能够保留时间，同时指定会被上面的覆盖
        config.setSoftMinEvictableIdleTimeMillis(600000L);
        //驱逐线程执行间隔时间
        config.setTimeBetweenEvictionRunsMillis(300000L);
        return config;


    }

    public  AbandonedConfig getDefaultAbandonedConfig() {
        //放弃长时间占用连接的对象
        AbandonedConfig abandonedConfig = new AbandonedConfig();
        abandonedConfig.setLogAbandoned(true);
        abandonedConfig.setUseUsageTracking(false);
        abandonedConfig.setRemoveAbandonedOnBorrow(true);
        abandonedConfig.setRemoveAbandonedOnMaintenance(true);
        abandonedConfig.setRemoveAbandonedTimeout(180);//second
        return abandonedConfig;
    }

    @Override
    public void initialize() throws Throwable {

    }
}
