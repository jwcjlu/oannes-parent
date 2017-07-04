package com.jwcjlu.oannes.filter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 * 统计项
 * 
 *  File: StateItem.java
 * 
 *  Copyright (c) 2017,jwcjlu.com All Rights Reserved.
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
public class StatItem {
	private String name;
	private long lastResetTime;
	private long interval;
	private AtomicInteger token;
	private int rate;

	/**
	 * StatItem Constructor.
	 *
	 * @param name
	 * @param lastRestTime
	 * @param interval
	 * @param token
	 * @param rate
	 */
	public StatItem(String name, long interval, int rate) {
		super();
		this.name = name;
		this.interval = interval;
		this.rate = rate;
		this.lastResetTime = System.currentTimeMillis();
	    this.token = new AtomicInteger(rate);
	}
	public boolean isAllowing(){
		long now=System.currentTimeMillis();
		if(now>lastResetTime+interval){
			lastResetTime=now;
			token.set(rate);
		}
		int value=token.get();
		boolean flag=false;
		while(value>0&&!flag){
			flag=token.compareAndSet(value, value-1);
			value=token.get();
		}
		return flag;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static void main(String[] args) throws InterruptedException {
		long last=System.currentTimeMillis();
		TimeUnit.SECONDS.sleep(2);
		long now=System.currentTimeMillis();
		System.out.println(now-last);
	}

}
