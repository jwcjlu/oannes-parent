package com.jwcjlu.oannes.config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.jwcjlu.oannes.common.Handler;


public class ConsumerBean <T> extends ConfigBean implements FactoryBean<T>,InitializingBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public T getObject() throws Exception {
		// TODO Auto-generated method stub
		 Class<?>[] interfaces =new Class<?>[]{getInterfaces()};
		 InvocationHandler handler=new Handler(getInterfaces(),getHost(),getPort());
		return (T) Proxy.newProxyInstance(ConsumerBean.class.getClassLoader(),interfaces ,  handler);
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
