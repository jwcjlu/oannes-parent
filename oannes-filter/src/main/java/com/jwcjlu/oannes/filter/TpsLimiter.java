package com.jwcjlu.oannes.filter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <pre>
 * 
 *  File: TpsLimiter.java
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
public class TpsLimiter {
	
	private ConcurrentMap<String,StateItem> map=new ConcurrentHashMap<String,StateItem>();
	/**
	 * 是否超过流量了
	 * TODO.
	 *
	 * @param serviceName
	 * @param rate
	 * @param interval
	 * @return
	 */
	public boolean isAllowing(String serviceName,int rate, long interval){
		if(rate>0){
	        StateItem item=	map.get(serviceName);
	        if(item==null){
	        item=new StateItem(serviceName, interval, rate);
	        map.put(serviceName, item);
	        }
	        return item.isAllowing();
		}
		return true;
	}

}

