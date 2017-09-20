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
public class Header implements Serializable{

	/**
	 * Comment for &lt;code&gt;serialVersionUID&lt;/code&gt;
	 */
	private static final long serialVersionUID = 4442811953462072462L;
	private byte type;
	private int version;
	private int length;
	private byte priority;
	private long sessionID;
	
	
	public Header()
    {
        super();
        // TODO Auto-generated constructor stub
    }
    public Header(byte type, int version, int length, byte priority, long sessionID)
    {
        super();
        this.type = type;
        this.version = version;
        this.length = length;
        this.priority = priority;
        this.sessionID = sessionID;
    }
    /**
	 * 
	 * Hander Constructor. 
	 *
	 * @param type
	 * @param version
	 * @param length
	 */
	public Header(byte type, int version, int length) {
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
    public byte getPriority()
    {
        return priority;
    }
    public void setPriority(byte priority)
    {
        this.priority = priority;
    }
    public long getSessionID()
    {
        return sessionID;
    }
    public void setSessionID(long sessionID)
    {
        this.sessionID = sessionID;
    }
	
}

