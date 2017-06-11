package com.jwcjlu.oannes.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.jwcjlu.oannes.common.spring.SpringBeanUtils;
import com.jwcjlu.oannes.register.Register;
import com.jwcjlu.oannes.register.RegisterDirection;
import com.jwcjlu.oannes.register.ZookeeperRegister;
import com.jwcjlu.oannes.rpc.proxy.JdkProxy;
import com.jwcjlu.oannes.rpc.proxy.JdkProxyHandler;
import com.jwcjlu.oannes.rpc.proxy.ProxyHandler;
import com.oannes.common.URL;


public class ConsumerBean <T> extends ConfigBean{
	private ApplicationContext context;
	public ConsumerBean(ApplicationContext context){
		this.context=context;
	}
	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T getObject() throws Exception {
		// TODO Auto-generated method stub

		URL url=builderURL();
		url.setBackupAddress(SpringBeanUtils.getBean(RegisterBean.class).getString("register"));
		Register  register=ZookeeperRegister.getInstance(url);
		RegisterDirection direction=new RegisterDirection();
		direction.setRegister(register);
		List<URL>urls=new ArrayList<URL>();
		urls.add(url);
		register.subscribe(urls, direction);
//		ProxyFactory proxyFacotry=context.getBean(ProxyFactory.class);
	    Class<?>[] interfaces =new Class<?>[]{getInterfaces()};
	    ProxyHandler handler=new JdkProxyHandler(getInterfaces(),direction,url);
		return (T) new JdkProxy().newProxyInstance(ConsumerBean.class.getClassLoader(),interfaces ,handler);
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}
	


}
