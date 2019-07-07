package com.jwcjlu.oannes.rpc.invoke;

import java.util.UUID;

import com.jwcjlu.oannes.rpc.proxy.JdkProxyHandler;
import com.jwcjlu.oannes.transport.exchange.ExchangeClient;
import com.oannes.common.Invocation;
import com.oannes.common.Invoker;
import com.oannes.common.Result;
import com.oannes.common.RpcRequest;
import com.oannes.common.URL;

/**
 * <pre>
 * 
 *  File: OannesInvoker.java
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
@SuppressWarnings("rawtypes")
public class OannesInvoker implements Invoker{
	private URL url;
	private ExchangeClient client;
	public OannesInvoker(URL url,ExchangeClient client){
		this.url=url;
		this.client=client;
	}

	@Override
	public Result invoke(Invocation invocation) {
		// TODO Auto-generated method stub
		RpcRequest request=new RpcRequest();
		request.setArgs(invocation.getAgrs());
		request.setId(UUID.randomUUID().toString());
		request.setMethod(invocation.getMethod().getName());
		request.setType(invocation.getInterface());
		Object obj=null;
		Result result=invocation.newResult();
		try{
          obj=client.request(invocation);
		  result.setSuccessful(true);
		  result.setObj(obj);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setException(e);
			result.setSuccessful(false);
		}
		return result;
	}

	@Override
	public URL getURL() {
		// TODO Auto-generated method stub
		return url;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj==null||! (obj instanceof JdkProxyHandler)){
			return false;
		}
		return this.getURL().equals(((Invoker)obj).getURL());
	}
}

