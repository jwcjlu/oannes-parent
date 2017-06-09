package com.jwcjlu.oannes.filter;

import com.jwcjlu.oannes.Invocation;
import com.jwcjlu.oannes.Result;

/**
 * <pre>
 * 
 *  File: Invoker.java
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
public interface Invoker {
	/**
	 * 
	 * TODO.
	 *
	 * @param invocation  会话
	 * @return
	 */
   Result invoke(Invocation invocation);
}

