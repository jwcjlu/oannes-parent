package com.jwcjlu.oannes.transport.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import com.jwcjlu.oannes.transport.MsgType;
import com.jwcjlu.oannes.transport.SerializeUtil;
import com.oannes.common.RpcResponse;

/**
 * <pre>
 * 
 *  File: OannesClientDecoder.java
 * 
 *  Copyright (c) 2017, globalegrow.com All Rights Reserved.
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
public class OannesClientDecoder extends LengthFieldBasedFrameDecoder
{

    public OannesClientDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength)
    {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected RpcResponse decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception
    {
        // TODO Auto-generated method stub
        in = (ByteBuf) super.decode(ctx, in);
        if (in == null) {
            return null;
        }
        if(in.readableBytes()<18){
            return null; 
        }
        long sessionID=in.readLong();
        int version=in.readInt();
        byte type=in.readByte();
        byte priority=in.readByte();
        if(type==MsgType.HEART_BEAT.getValue()){
            System.out.println("心跳检查包");
        }
        int length=in.readInt();
        if(length>0&&type==MsgType.RPC_RESP.getValue()){
            byte[]dst=new byte[length];
            in.readBytes(dst);
            return  (RpcResponse) SerializeUtil.derialize(dst);
            
        }
        return null;
    }
    

}


