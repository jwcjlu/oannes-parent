package com.jwcjlu.oannes.transport;

import com.jwcjlu.oannes.transport.exchange.ExchangeClient;
import com.jwcjlu.oannes.transport.futrue.DefaultResponseFuture;
import com.oannes.common.RpcResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ClientHandler extends SimpleChannelInboundHandler<RpcResponse>{
	private ExchangeClient client;
	public ClientHandler(ExchangeClient client){
		this.client=client;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
		// TODO Auto-generated method stub
	 DefaultResponseFuture.getResponsefutures().get(msg.getId()).receive(msg);
		
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("回调没有呢！！");
		 if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {  
	            IdleStateEvent event = (IdleStateEvent) evt;  
	            if (event.state() == IdleState.READER_IDLE)  
	                System.out.println("read idle");  
	            else if (event.state() == IdleState.WRITER_IDLE)  
	                System.out.println("write idle");  
	            else if (event.state() == IdleState.ALL_IDLE)  
	                System.out.println("all idle");  
	        } 
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
		System.out.println("dsafsdf");
		client.countDown();
	}
	

}