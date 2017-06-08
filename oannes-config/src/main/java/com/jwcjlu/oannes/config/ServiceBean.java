package com.jwcjlu.oannes.config;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

import com.jwcjlu.oannes.register.Register;
import com.jwcjlu.oannes.register.ZookeeperRegister;
import com.jwcjlu.oannes.transport.NettyServer;
import com.jwcjlu.oannes.transport.Server;
import com.oannes.common.Constants;
import com.oannes.common.NetUtil;
import com.oannes.common.URL;

public class ServiceBean<T> extends ConfigBean implements InitializingBean{
	/**
	 * 
	 */
	private static ExecutorService  service=Executors.newFixedThreadPool(10);
	private static Server server;
	private  static  volatile boolean export=false;
	private static final long serialVersionUID = 1L;
	private static ApplicationContext context;
	public ServiceBean(ApplicationContext context){
		this.context=context;
	}
	public ApplicationContext getContext() {
		return context;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
	     URL url =builderURL();
	     context.getBean(OannesProtocol.class).export(url);
	}


	

}
