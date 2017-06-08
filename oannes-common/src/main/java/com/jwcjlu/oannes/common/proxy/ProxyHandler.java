package com.jwcjlu.oannes.common.proxy;

import java.util.List;

import com.oannes.common.Invoker;

public interface ProxyHandler<T> {
	
	void refrashInvoker(List<Invoker<T>> invokers);
	
	List<Invoker<T>>  getInvokers();

	void disponseInvoker(List<Invoker<T>> oldInvokers);

}
