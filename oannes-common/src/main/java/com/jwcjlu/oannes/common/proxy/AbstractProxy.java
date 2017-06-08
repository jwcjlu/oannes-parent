package com.jwcjlu.oannes.common.proxy;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractProxy implements Proxy{
	@Autowired
	private ProxyFactory  factory;
	@PostConstruct
	public void register(){
		factory.registerProxy(getName(), this);
	}

}
