package com.jwcjlu.oannes.common.proxy;

public interface Proxy {

	<T>T newProxyInstance(ClassLoader loader,Class<?>[] interfaces,ProxyHandler handler);
	String getName();

}
