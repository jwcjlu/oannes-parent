package com.oannes.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class RpcInvocation implements Invocation {
	private Method method;
	private Object[]agrs;
	private Class<?> interfaces;
	private boolean asyn;
	private Map<String,Object> attributes=new HashMap<String,Object>();

	
	public RpcInvocation(Method method, Object[] agrs, Class<?> interfaces) {
		this.method = method;
		this.agrs = agrs;
		this.interfaces = interfaces;
		asyn=method.getReturnType().isAssignableFrom(CompletableFuture.class);
	}

	@Override
	public Method getMethod() {
		// TODO Auto-generated method stub
		return method;
	}

	@Override
	public Object[] getAgrs() {
		// TODO Auto-generated method stub
		return agrs;
	}

	@Override
	public void addAttribute(String key, Object value) {
		// TODO Auto-generated method stub
		attributes.put(key, value);
	}

	@Override
	public void removeAttribute(String key) {
		// TODO Auto-generated method stub
		attributes.remove(key);
	}

	@Override
	public Class<?> getInterface() {
		// TODO Auto-generated method stub
		return interfaces;
	}
	public boolean isAsyn(){
		return asyn;
	}
	public Result newResult(){
		if(asyn){
			return new AsynResult();
		}else {
			return new Result();
		}
	}

}
