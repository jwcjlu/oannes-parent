package com.jwcjlu.oannes.filter;

import com.oannes.common.Constants;
import com.oannes.common.Invocation;
import com.oannes.common.Invoker;
import com.oannes.common.Result;
import com.oannes.common.URL;
import com.oannes.common.exception.RpcException;

/**
 * <pre>
 * 
 *  File: TpsLimitedFilter.java
 * 
 *  Copyright (c) 2017,jwcjlu.com All Rights Reserved.
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
	@SuppressWarnings("rawtypes")
	@Override
	public Result invoke(Invoker invoker, Invocation invocation) throws RpcException {
		//limiter.isAllowing(serviceName, rate, interval)
		// TODO Auto-generated method stub
		URL url=invoker.getURL();
		 int rate=url.getParameter("rate",Constants.RATE);
		 int interval=url.getParameter("interval",Constants.INTERVAL);
		 boolean flag=limiter.isAllowing(invocation.getInterface()+invocation.getMethod().getName(), rate, interval);
		if(!flag){
			System.out.println("tps is full");
			throw new RpcException(
                    new StringBuilder(64)
                            .append("Failed to invoke service ")
                            .append(invocation.getInterface().getName())
                            .append(".")
                            .append(invocation.getMethod())
                            .append(" because exceed max service tps.")
                            .toString());
		}
		 return invoker.invoke(invocation);
		
	}

}

