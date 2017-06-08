package com.oannes.common.loadBalance;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.oannes.common.Invocation;
import com.oannes.common.Invoker;
import com.oannes.common.URL;

public class RoundRobinLoadBalance extends AbstractLoadBalance{
	private final ConcurrentMap<String, AtomicInteger> sequences = new ConcurrentHashMap<String, AtomicInteger>();
	@Override
	public <T> Invoker<T> doSelect(List<Invoker<T>> invokers,URL url, Invocation invocation) {
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

}
