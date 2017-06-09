package com.jwcjlu.oannes.filter;

import com.oannes.common.Invocation;
import com.oannes.common.Invoker;
import com.oannes.common.Result;
import com.oannes.common.exception.RpcException;

/**
 * <pre>
 * 
 *  File: TimeoutFilter.java
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
public class TimeoutFilter implements Filter {

	/* (non-Javadoc)
	 * @see com.jwcjlu.oannes.filter.Filter#invoke(com.jwcjlu.oannes.filter.Invoker, com.jwcjlu.oannes.Invocation)
	 */
	@Override
	public Result invoke(Invoker invoker, Invocation invocation) throws RpcException {
		// TODO Auto-generated method stub
        return invoker.invoke(invocation);
	}

}

