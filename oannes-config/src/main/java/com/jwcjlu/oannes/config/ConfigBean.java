package com.jwcjlu.oannes.config;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.support.ManagedMap;

public class ConfigBean implements Serializable{

	/**
	 * 
	 */
	protected static Collection<String> services= Collections.synchronizedCollection(new HashSet<String>());
	protected static ReentrantLock  lock=new ReentrantLock();
	private static final long serialVersionUID = 1L;
	protected String id;
	protected Class interfaces;
	protected String provider;
	protected String host;
	protected int port;
	protected RegisterBean registerBean;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Class getInterfaces() {
		return interfaces;
	}
	public void setInterfaces(Class interfaces) {
		this.interfaces = interfaces;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public RegisterBean getRegisterBean() {
		return registerBean;
	}
	public void setRegisterBean(RegisterBean registerBean) {
		this.registerBean = registerBean;
	}

	

}
