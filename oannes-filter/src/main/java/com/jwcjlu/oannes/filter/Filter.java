package com.jwcjlu.oannes.filter;

import com.oannes.common.Invocation;
import com.oannes.common.Invoker;
import com.oannes.common.Result;
import com.oannes.common.exception.RpcException;

/**
 * <pre>
 * 
 *  File: Filter.java
 * 
 *  Copyright (c) 2017, globalegrow.com All Rights Reserved.
 * 
 *  Description:
 *  TODO
 * 
 *  Revision History
 *  Date,					Who,					What;
 *  2017年6月9日				Jinwei				Initial.
 *
 * </pre>
 */
public interface Filter {
	/**
	 * 过滤器
	 * TODO.
	 *
	 * @param invoker
	 * @param invocation
	 * @throws RpcException 
	 */
	Result invoke(Invoker invoker,Invocation invocation) throws RpcException;
	
}

