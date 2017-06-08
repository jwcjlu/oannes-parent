package com.oannes.common.loadBalance;

import java.util.List;

import com.oannes.common.Invocation;
import com.oannes.common.Invoker;
import com.oannes.common.URL;
import com.oannes.common.exception.RpcException;

public interface LoadBalance {
	
	<T> Invoker<T> select(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException;

}
