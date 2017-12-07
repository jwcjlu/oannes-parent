package com.jwcjlu.oannes.filter.support;

import com.jwcjlu.oannes.filter.Filter;
import com.oannes.common.Invocation;
import com.oannes.common.Invoker;
import com.oannes.common.Result;
import com.oannes.common.exception.RpcException;

/**
 * <pre>
 * 
 *  File: HystrixFilter.java
 * 
 *  Copyright (c) 2017, globalegrow.com All Rights Reserved.
 * 
 *  Description:
 *  TODO
 * 
 *  Revision History
 *  Date,					Who,					What;
 *  2017年7月16日				jinwei				Initial.
 *
 * </pre>
 */
public class HystrixFilter implements Filter{

	@Override
	public Result invoke(Invoker invoker, Invocation invocation) throws RpcException {
		   OannesHystrixCommand command = new OannesHystrixCommand(invoker, invocation);
	        return command.execute();
	}

}

