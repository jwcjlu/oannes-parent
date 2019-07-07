package com.jwcjlu.oannes.transport.codec;

import com.oannes.common.util.SerializeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import com.jwcjlu.oannes.transport.protocol.MsgType;
import com.oannes.common.RpcRequest;

/**
 * <pre>
 * 
 *  File: OannesDecoder.java
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
public class OannesServerDecoder extends LengthFieldBasedFrameDecoder
{

    public OannesServerDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength)
    {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected RpcRequest decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception
    {
        // TODO Auto-generated method stub
        /*if(in!=null&&in.readableBytes()<18){*/
        /*    String msg=in.toString(Charset.defaultCharset());
            System.out.println(msg);*/
      
            //ctx.channel().writeAndFlush(msg);
     /*   }*/
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
            return null;
        }
        int length=in.readInt();
        System.out.println("type="+type+":length="+length);
        if(length>0&&type==MsgType.RPC_REQ.getValue()){
            byte[]dst=new byte[length];
            in.readBytes(dst);
            return  (RpcRequest) SerializeUtil.derialize(dst);
        }
        return null;
    }
    

}

