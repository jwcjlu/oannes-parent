package com.jwcjlu.oannes.cluster;

import java.util.List;

import com.oannes.common.Invocation;
import com.oannes.common.Invoker;
import com.oannes.common.LoadBalance;
import com.oannes.common.URL;
import com.oannes.common.exception.RpcException;

/**
 * <pre>
 * 
 *  File: Cluster.java
 * 
 *  Copyright (c) 2017,jwcjlu.com All Rights Reserved.
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
public interface Cluster {

	
	public void registerFactory();
	
	public String getName();
	
	@SuppressWarnings("rawtypes")
	Invoker select(List<Invoker> ts,LoadBalance lb,Invocation  invocation,URL url)throws RpcException;

}

