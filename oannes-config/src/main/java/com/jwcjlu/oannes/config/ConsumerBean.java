package com.jwcjlu.oannes.config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.jwcjlu.oannes.common.Constants;
import com.jwcjlu.oannes.register.OannesListener;
import com.jwcjlu.oannes.register.ZookeeperRegister;
import com.jwcjlu.oannes.transport.ProxyHandler;


public class ConsumerBean <T> extends ConfigBean implements FactoryBean<T>,InitializingBean{
	private volatile static boolean subscribe=false;
	private static ReentrantLock lock=new ReentrantLock();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public T getObject() throws Exception {
		// TODO Auto-generated method stub
		 Class<?>[] interfaces =new Class<?>[]{getInterfaces()};
		 InvocationHandler handler=new ProxyHandler(getInterfaces());
		 RegisterBean  rb=getRegisterBean();
		 ZookeeperRegister  zr=ZookeeperRegister.getInstance(rb.getString("register"));
		 String subject="/"+Constants.ROOT+"/"+getInterfaces().getName()+"/"+Constants.PROVIDER;
		if(!subscribe){
			subscribe=true;
		  zr.subscribe(subject, new OannesListener<T>(getInterfaces()),getInterfaces());
		  TimeUnit.SECONDS.sleep(2);
		}
		return (T) Proxy.newProxyInstance(ConsumerBean.class.getClassLoader(),interfaces ,handler);
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
	
	}

}
