package com.jwcjlu.oannes.config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.jwcjlu.oannes.common.Constants;
import com.jwcjlu.oannes.common.Handler;
import com.jwcjlu.oannes.common.URL;
import com.jwcjlu.oannes.register.OannesListener;
import com.jwcjlu.oannes.register.ZookeeperRegister;
import com.jwcjlu.oannes.register.listener.NotifyListener;


public class ConsumerBean <T> extends ConfigBean implements FactoryBean<T>,InitializingBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public T getObject() throws Exception {
		// TODO Auto-generated method stub
		 Class<?>[] interfaces =new Class<?>[]{getInterfaces()};
		 InvocationHandler handler=new Handler(getInterfaces());
		 RegisterBean  rb=getRegisterBean();
		 ZookeeperRegister  zr=ZookeeperRegister.getInstance(rb.getString("register"));
		 String subject="/"+Constants.ROOT+"/"+getInterfaces().getName()+"/"+Constants.PROVIDER;
		 zr.subscribe(subject, new OannesListener<T>(getInterfaces()),getInterfaces());
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
