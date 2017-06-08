package com.jwcjlu.oannes.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.context.ApplicationContext;

import com.jwcjlu.oannes.common.proxy.JdkProxy;
import com.jwcjlu.oannes.common.proxy.JdkProxyHandler;
import com.jwcjlu.oannes.common.proxy.ProxyFactory;
import com.jwcjlu.oannes.common.proxy.ProxyHandler;
import com.jwcjlu.oannes.register.Register;
import com.jwcjlu.oannes.register.ZookeeperRegister;
import com.oannes.common.URL;


public class ConsumerBean <T> extends ConfigBean{

	private volatile static boolean subscribe=false;
	private static ReentrantLock lock=new ReentrantLock();
	private ProxyHandler handler;
	private RegisterDirection<T> direction;
	private ApplicationContext context;
	public ConsumerBean(ApplicationContext context){
		this.context=context;
	}
	private static final long serialVersionUID = 1L;

	public T getObject() throws Exception {
		OannesProtocol protocol=context.getBean(OannesProtocol.class);
		// TODO Auto-generated method stub
	    Class<?>[] interfaces =new Class<?>[]{getInterfaces()};
		handler=new JdkProxyHandler(getInterfaces());
		Register  register=ZookeeperRegister.getInstance(builderURL());
		direction=new RegisterDirection<T>();
		direction.setRegister(register);
		direction.setProxyHandler(handler);
		URL url=builderURL();
		List<URL>urls=new ArrayList<URL>();
		urls.add(url);
		register.subscribe(urls, direction);
		ProxyFactory proxyFacotry=context.getBean(ProxyFactory.class);
		return (T) new JdkProxy().newProxyInstance(ConsumerBean.class.getClassLoader(),interfaces ,handler);
	}


}
