package com.jwcjlu.oannes.cluster;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.oannes.common.Constants;
import com.oannes.common.Invocation;
import com.oannes.common.Invoker;
import com.oannes.common.LoadBalance;
import com.oannes.common.Result;
import com.oannes.common.RpcConext;
import com.oannes.common.URL;
import com.oannes.common.exception.RpcException;

/**
 * <pre>
 * 解决模式之覆盖模式  
 *调用失败可以重试相应的次数
 *  File: FailoverCluster.java
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
@SuppressWarnings("rawtypes")
@Component
public class FailoverClusterInvoker extends AbstractCluster implements Invoker {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "default";
	}

	@Override
	public Invoker select(List<Invoker> invokers, LoadBalance loadBalance, Invocation invocation,URL url) throws RpcException {
		// TODO Auto-generated method stub
		RpcConext.put(new RpcConext(invokers, url, loadBalance));
		return this;
	}

	@Override
	public URL getURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result invoke(Invocation invocation) throws RpcException {
		// TODO Auto-generated method stub
		RpcConext context=RpcConext.get();
		List<Invoker> invokers=context.getInvokers();
		if(CollectionUtils.isEmpty(invokers)){
			throw new RpcException("no provider");
		}
		Result result=null;
		boolean flag=true;
		int retry =context.getUrl().getParameter("retry",Constants.RETRY);
		while(retry>0&&flag){
			retry--;
			try{
		      result=context.getLoadBalance().select(invokers, invocation).invoke(invocation);
		      flag=false;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}

}

