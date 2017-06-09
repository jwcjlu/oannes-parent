package com.jwcjlu.oannes.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.jwcjlu.oannes.common.proxy.ProxyHandler;
import com.jwcjlu.oannes.common.spring.SpringBeanUtils;
import com.jwcjlu.oannes.register.Register;
import com.jwcjlu.oannes.register.listener.NotifyListener;
import com.oannes.common.Invoker;
import com.oannes.common.URL;

public class RegisterDirection<T> implements NotifyListener{
	private Register register;
	private ProxyHandler<T> proxyHandler;
			

	@Override
	public void notify(List<URL> urls) {
		// TODO Auto-generated method stub
		refrashInvoker(urls);
	}

	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}
	
	public ProxyHandler<T> getProxyHandler() {
		return proxyHandler;
	}

	public void setProxyHandler(ProxyHandler<T> proxyHandler) {
		this.proxyHandler = proxyHandler;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	void refrashInvoker(List<URL> urls){
		if(CollectionUtils.isEmpty(urls)){
			return ;
		}
		List<Invoker<T>> invokers=new ArrayList<Invoker<T>>();
		List<Invoker<T>> oldInvokers=proxyHandler.getInvokers();
		OannesProtocol protocol=SpringBeanUtils.getBean(OannesProtocol.class);
		for(URL url:urls){
			Invoker invoker=null;
			try {
				invoker = (Invoker) protocol.refer(url);
				invokers.add(invoker);
				oldInvokers.remove(invoker);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		proxyHandler.refrashInvoker(invokers);
		proxyHandler.disponseInvoker(oldInvokers);
	}
	

}
