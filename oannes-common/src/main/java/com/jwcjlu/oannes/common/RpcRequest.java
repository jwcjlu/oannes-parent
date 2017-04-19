package com.jwcjlu.oannes.common;

import java.io.Serializable;

public class RpcRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private Class<?> type;
	private Object[] args;
	private String method;
	private Class<?>[] parameterTypes;


	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
