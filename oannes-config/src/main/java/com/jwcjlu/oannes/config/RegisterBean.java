package com.jwcjlu.oannes.config;

import org.springframework.beans.factory.support.ManagedMap;

public class RegisterBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4951033760390728834L;
	private String  id;
	private  ManagedMap parameters;
	private int port;
	public ManagedMap getParameters() {
		return parameters;
	}
	public void setParameters(ManagedMap parameters) {
		this.parameters = parameters;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public Object get(String key){
		return getParameters().get(key);
	}
	public String getString(String key){
		return getParameters().get(key)==null?"":getParameters().get(key).toString();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
