package com.jwcjlu.oannes.rpc.proxy;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.jwcjlu.oannes.rpc.proxy.Proxy;
import com.jwcjlu.oannes.rpc.proxy.ProxyFactory;

public abstract class AbstractProxy implements Proxy{
	@Autowired
	private ProxyFactory  factory;
	@PostConstruct
	public void register(){
		factory.registerProxy(getName(), this);
	}

}
