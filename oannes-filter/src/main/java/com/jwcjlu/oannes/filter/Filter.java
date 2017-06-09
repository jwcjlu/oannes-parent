package com.jwcjlu.oannes.filter;

import com.jwcjlu.oannes.Invocation;
import com.jwcjlu.oannes.Result;

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
	 */
	Result invoke(Invoker invoker,Invocation invocation);
	
}

