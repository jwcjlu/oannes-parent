package com.oannes.common.loadBalance;

import java.util.List;

import com.oannes.common.Invocation;
import com.oannes.common.Invoker;
import com.oannes.common.URL;
import com.oannes.common.exception.RpcException;

public abstract class AbstractLoadBalance implements LoadBalance{
	public <T> Invoker<T> select(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException{
		   if (invokers == null || invokers.size() == 0)
	            return null;
	        if (invokers.size() == 1)
	            return invokers.get(0);
	        return doSelect(invokers, url, invocation);
	}
	public abstract <T> Invoker<T> doSelect(List<Invoker<T>> invokers,URL url, Invocation invocation);
}
