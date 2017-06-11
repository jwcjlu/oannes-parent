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
	
	private ConcurrentMap<String,StatItem> map=new ConcurrentHashMap<String,StatItem>();
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
	        StatItem item=	map.get(serviceName);
	        if(item==null){
	        item=new StatItem(serviceName, interval, rate);
	        map.put(serviceName, item);
	        }
	        return item.isAllowing();
		}
		return true;
	}

}

