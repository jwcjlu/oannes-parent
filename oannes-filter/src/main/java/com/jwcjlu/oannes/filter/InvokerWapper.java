package com.jwcjlu.oannes.filter;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.jwcjlu.oannes.Invocation;
import com.jwcjlu.oannes.Result;

/**
 * <pre>
 * 
 *  File: InvokerWapper.java
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
public class InvokerWapper {
	public Invoker buildFilterChain(Invoker invoker,Invocation invocation){
		Invoker last=invoker;
		List<Filter> filters=getFilters();
		if(!CollectionUtils.isEmpty(filters)){
			return last;
		}
		for(Filter f:filters){
			final Filter fi=f;
			final Invoker next=last;
			last=new Invoker() {
				
				@Override
				public Result invoke(Invocation invocation) {
					// TODO Auto-generated method stub
					return fi.invoke(next, invocation);
					
				}
			}; 
		}
		return last;
		
	}
	public List<Filter> getFilters(){
		return null;
	}

}

