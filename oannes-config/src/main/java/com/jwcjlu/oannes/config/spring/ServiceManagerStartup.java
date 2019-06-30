package com.jwcjlu.oannes.config.spring;

import com.jwcjlu.oannes.common.services.BootServiceManager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.PreDestroy;

public class ServiceManagerStartup implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        BootServiceManager.INSTANCE.boot();
    }
    @PreDestroy
    public void destory(){
        BootServiceManager.INSTANCE.shutdown();
    }
}
