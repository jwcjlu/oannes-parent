package com.oannes.common;

import com.oannes.common.exception.RpcException;

public interface Invoker<T> {
	
	URL getURL();
	
	Result invoke(Invocation invocation) throws RpcException;

}
