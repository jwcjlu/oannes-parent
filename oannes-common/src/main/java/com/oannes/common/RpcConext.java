package com.oannes.common;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 
 *  File: RpcConext.java
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
public class RpcConext {
	private static ThreadLocal<RpcConext> local=new ThreadLocal<RpcConext>();
	public static void put(RpcConext context){
		local.set(context);
	}
	public static RpcConext get(){
		return local.get();
	}
	
	public RpcConext(List<Invoker> invokers, URL url, LoadBalance loadBalance) {
		super();
		this.invokers = invokers;
		this.url = url;
		this.loadBalance = loadBalance;
	}

	private List<Invoker>invokers;
	private URL url;
	private LoadBalance loadBalance;

	public List<Invoker> getInvokers() {
		return invokers;
	}
	public void setInvokers(List<Invoker> invokers) {
		this.invokers = invokers;
	}
	public URL getUrl() {
		return url;
	}
	public void setUrl(URL url) {
		this.url = url;
	}
	public LoadBalance getLoadBalance() {
		return loadBalance;
	}
	public void setLoadBalance(LoadBalance loadBalance) {
		this.loadBalance = loadBalance;
	}
	


}

