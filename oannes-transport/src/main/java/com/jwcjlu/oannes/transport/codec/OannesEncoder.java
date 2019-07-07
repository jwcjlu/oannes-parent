package com.jwcjlu.oannes.transport.codec;

import com.oannes.common.util.SerializeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.jwcjlu.oannes.transport.protocol.OannesMessage;
import com.jwcjlu.oannes.transport.excption.OannesMessageException;

/**
 * <pre>
 * 
 *  File: OannesEncoder.java
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
public class OannesEncoder extends MessageToByteEncoder<OannesMessage>
{

    @Override
    protected void encode(ChannelHandlerContext chc, OannesMessage msg, ByteBuf out) throws Exception
    {
        // TODO Auto-generated method stub
        if(msg==null||msg.getHeader()==null){
            throw new OannesMessageException();
        }
        out.writeLong(msg.getHeader().getSessionID());
        out.writeInt(msg.getHeader().getVersion());
        out.writeByte(msg.getHeader().getType());
        out.writeByte(msg.getHeader().getPriority());
        if(msg.getBody()==null){
           out.writeInt(0);
            return ;
        }
        byte[]body= SerializeUtil.serialize(msg.getBody());
        out.writeInt(body.length);
        out.writeBytes(body);
    }

}

