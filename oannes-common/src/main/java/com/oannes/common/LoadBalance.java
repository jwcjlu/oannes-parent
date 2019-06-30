package com.oannes.common;

import java.util.List;

import com.jwcjlu.oannes.common.services.BootService;
import com.oannes.common.Invocation;
import com.oannes.common.Invoker;
import com.oannes.common.exception.RpcException;

public interface LoadBalance extends BootService {
	
	 @SuppressWarnings("rawtypes")
	Invoker select(List<Invoker> invokers, Invocation invocation) throws RpcException;
	public void registerFactory();
	
	public String getName();
}
