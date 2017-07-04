package com.oannes.common;
/**
 * <pre>
 * 
 *  File: Protocol.java
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
public interface Protocol {
	/**
	 * 引用
	 * TODO.
	 *
	 * @param url
	 * @return
	 * @throws InterruptedException
	 */
	public Object refer(final URL url) throws InterruptedException;
	/**
	 * 暴露服务
	 * TODO.
	 *
	 * @param url
	 */
	public void export(URL url);
}

