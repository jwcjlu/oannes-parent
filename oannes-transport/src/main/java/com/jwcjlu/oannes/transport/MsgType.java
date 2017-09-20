package com.jwcjlu.oannes.transport;
/**
 * <pre>
 * 
 *  File: MsgType.java
 * 
 *  Copyright (c) 2017, jwcjlu.com All Rights Reserved.
 * 
 *  Description:
 *  TODO
 * 
 *  Revision History
 *  Date,					Who,					What;
 *  2017年9月20日				jinwei				Initial.
 *
 * </pre>
 */
public enum MsgType
{
    LOGIN((byte)1),
    HEART_BEAT((byte)2),
    RPC_REQ((byte)3),
    RPC_RESP((byte)4);
    private byte value;
    MsgType(byte value){
        this.value=value;
    }
    public byte getValue()
    {
        return value;
    }
    public void setValue(byte value)
    {
        this.value = value;
    }
    

}

