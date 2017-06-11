package com.jwcjlu.oannes.cluster.loadBalance;

import java.util.List;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.oannes.common.Invocation;
import com.oannes.common.Invoker;
/**
 * 轮询负载均衡
 * <pre>
 * 
 *  File: RoundRobinLoadBalance.java
 * 
 *  Copyright (c) 2017, com.jwcjlu All Rights Reserved.
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
public class RoundRobinLoadBalance extends AbstractLoadBalance{
	private final ConcurrentMap<String, AtomicInteger> sequences = new ConcurrentHashMap<String, AtomicInteger>();
	@SuppressWarnings("rawtypes")
	@Override
	public Invoker doSelect(List<Invoker> invokers, Invocation invocation) {
		// TODO Auto-generated method stub
		int size=invokers.size();
		String key=invocation.getInterface()+invocation.getMethod().getName();
		AtomicInteger current=sequences.get(key);
		if(current==null){
			sequences.put(key, new AtomicInteger(0));
			current=sequences.get(key);
		}
		int currentIndex=current.incrementAndGet();
		return invokers.get(currentIndex%size);
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "default";
	}

}
