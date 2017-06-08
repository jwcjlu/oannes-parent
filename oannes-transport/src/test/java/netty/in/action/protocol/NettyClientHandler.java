package netty.in.action.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import com.jwcjlu.oannes.transport.Hander;

/**
 * <pre>
 * 
 *  File: NettyClientHandler.java
 * 
 *  Copyright (c) 2017, globalegrow.com All Rights Reserved.
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
public class NettyClientHandler extends SimpleChannelInboundHandler<NettyMessage>{

	/* (non-Javadoc)
	 * @see io.netty.channel.SimpleChannelInboundHandler#channelRead0(io.netty.channel.ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("回调没有呢！！"+ctx.channel().id());
		 if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {  
	            IdleStateEvent event = (IdleStateEvent) evt;  
	            if (event.state() == IdleState.READER_IDLE)  
	                System.out.println("read idle");  
	            else if (event.state() == IdleState.WRITER_IDLE)  
	                System.out.println("write idle");  
	            else if (event.state() == IdleState.ALL_IDLE) { 
	            	Header header=new Header((byte) 0,1,0);
	            	NettyMessage msg=new NettyMessage();
	            	msg.setHeader(header);;
	            	ctx.writeAndFlush(msg);
	            }
	                
	        } 
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	  
		super.channelActive(ctx);
		Header header=new Header((byte) 1,1,0);
    	NettyMessage msg=new NettyMessage();
    	msg.setHeader(header);
    	msg.setBody("开始连接！！！");
    	ctx.writeAndFlush(msg);
		
	}
	

}

