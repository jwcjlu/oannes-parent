package com.jwcjlu.oannes.filter;

import com.jwcjlu.oannes.Invocation;
import com.jwcjlu.oannes.Result;

/**
 * <pre>
 * 
 *  File: TpsLimitedFilter.java
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
public class TpsLimitedFilter implements Filter{
	private TpsLimiter limiter=new TpsLimiter();

	/* (non-Javadoc)
	 * @see com.jwcjlu.oannes.filter.Filter#invoke(com.jwcjlu.oannes.filter.Invoker, com.jwcjlu.oannes.Invocation)
	 */
	@Override
	public Result invoke(Invoker invoker, Invocation invocation) {
		//limiter.isAllowing(serviceName, rate, interval)
		// TODO Auto-generated method stub
		return invoker.invoke(invocation);
		
	}

}

