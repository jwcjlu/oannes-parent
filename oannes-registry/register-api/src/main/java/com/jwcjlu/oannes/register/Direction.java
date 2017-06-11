package com.jwcjlu.oannes.register;

import com.oannes.common.Invocation;
import com.oannes.common.Invoker;
import com.oannes.common.URL;
import com.oannes.common.exception.RpcException;

/**
 * <pre>
 * 
 *  File: Direction.java
 * 
 *  Copyright (c) 2017, globalegrow.com All Rights Reserved.
 * 
 *  Description:
 *  TODO
 * 
 *  Revision History
 *  Date,					Who,					What;
 *  2017年6月11日				jinwei				Initial.
 *
 * </pre>
 */
public interface Direction {
	/**
	 * 找到相应的可执行点
	 * TODO.
	 *
	 * @param url
	 * @return
	 * @throws RpcException 
	 */
  @SuppressWarnings("rawtypes")
public Invoker lookUp(URL url,Invocation invocation) throws RpcException;
}

