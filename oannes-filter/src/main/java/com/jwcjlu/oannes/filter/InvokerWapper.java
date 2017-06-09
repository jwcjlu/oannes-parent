package com.jwcjlu.oannes.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.oannes.common.Invocation;
import com.oannes.common.Invoker;
import com.oannes.common.Result;
import com.oannes.common.URL;
import com.oannes.common.exception.RpcException;

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
	@SuppressWarnings("rawtypes")
	public Invoker buildFilterChain(Invoker invoker){
		Invoker last=invoker;
		List<Filter> filters=getFilters();
		if(CollectionUtils.isEmpty(filters)){
			return last;
		}
		for(Filter f:filters){
			final Filter fi=f;
			final Invoker next=last;
			last=new Invoker() {
				@Override
				public Result invoke(Invocation invocation) throws RpcException {
					// TODO Auto-generated method stub
					return fi.invoke(next, invocation);
					
				}
				public URL getURL(){
					return next.getURL();
				}
			}; 
		}
		return last;
		
	}
	public List<Filter> getFilters(){
		List<Filter> filters=new ArrayList<Filter>();
		filters.add(new TpsLimitedFilter());
		return filters;
	}

}

