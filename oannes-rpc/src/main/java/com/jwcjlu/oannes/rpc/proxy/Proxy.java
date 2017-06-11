package com.jwcjlu.oannes.rpc.proxy;

import com.jwcjlu.oannes.rpc.proxy.ProxyHandler;

public interface Proxy {

	@SuppressWarnings("rawtypes")
	<T>T newProxyInstance(ClassLoader loader,Class<?>[] interfaces,ProxyHandler handler);
	String getName();

}
