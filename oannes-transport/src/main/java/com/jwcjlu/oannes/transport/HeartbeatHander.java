package com.jwcjlu.oannes.transport;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * <pre>
 * 
 *  File: HeartbeatHander.java
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
public class HeartbeatHander  extends ChannelInboundHandlerAdapter
{
    private Client client;
    public HeartbeatHander(Client client){
        this.client=client;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // TODO Auto-generated method stub
         if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {  
                IdleStateEvent event = (IdleStateEvent) evt; 
                if(event.state()==IdleState.ALL_IDLE){
                    ChannelFuture future= ctx.channel().writeAndFlush(buildHeartbeatReq());
                    future.addListener(new GenericFutureListener<Future<? super Void>>()
                    {
                        @Override
                        public void operationComplete(Future<? super Void> f) throws Exception
                        {
                            // TODO Auto-generated method stu
                            System.out.println("心跳包发送完毕！！！");
                            
                        }
                    });
                }
             
            } 
    }

    private Object buildHeartbeatReq()
    {
        // TODO Auto-generated method stub
        OannesMessage msg=new OannesMessage();
        Header header=new Header();
        header.setType(MsgType.HEART_BEAT.getValue());
        msg.setHeader(header);
        return msg;
    }

}

