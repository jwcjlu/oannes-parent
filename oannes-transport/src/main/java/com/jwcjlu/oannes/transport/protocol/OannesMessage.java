package com.jwcjlu.oannes.transport.protocol;
/**
 * <pre>
 * 
 *  File: OannesMessage.java
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
public class OannesMessage
{
    private Header header;
    private Object body;
    public Header getHeader()
    {
        return header;
    }
    public void setHeader(Header header)
    {
        this.header = header;
    }
    public Object getBody()
    {
        return body;
    }
    public void setBody(Object body)
    {
        this.body = body;
    }
    

}

