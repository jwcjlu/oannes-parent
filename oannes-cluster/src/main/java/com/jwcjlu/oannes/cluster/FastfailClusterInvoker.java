package com.jwcjlu.oannes.cluster;

import java.util.List;

import org.springframework.stereotype.Component;

import com.oannes.common.Invocation;
import com.oannes.common.Invoker;
import com.oannes.common.LoadBalance;
import com.oannes.common.URL;
import com.oannes.common.exception.RpcException;

/**
 * <pre>
 * 快速失败模式  
 * 调用失败即失败 不在重试
 *  File: FastfailCluster.java
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
@Component
public class FastfailClusterInvoker  extends AbstractCluster{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "fastfail";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Invoker select(List<Invoker> ts, LoadBalance lb, Invocation invocation,URL url) throws RpcException {
		// TODO Auto-generated method stub
		return lb.select(ts, invocation);
	}

}

