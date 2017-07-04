package com.jwcjlu.oannes.transport;

import java.io.Serializable;

/**
 * <pre>
 * 
 *  File: Hander.java
 * 
 *  Copyright (c) 2017,jwcjlu.com All Rights Reserved.
 * 
 *  Description:
 *  TODO
 * 
 *  Revision History
 *  Date,					Who,					What;
 *  2017年6月8日				Jinwei				Initial.
 *
 * </pre>
 */
public class Hander implements Serializable{

	/**
	 * Comment for &lt;code&gt;serialVersionUID&lt;/code&gt;
	 */
	private static final long serialVersionUID = 4442811953462072462L;
	private byte type;
	private int version;
	private int length;
	
	/**
	 * Hander Constructor. 
	 *
	 * @param type
	 * @param version
	 * @param length
	 */
	public Hander(byte type, int version, int length) {
		super();
		this.type = type;
		this.version = version;
		this.length = length;
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
}

