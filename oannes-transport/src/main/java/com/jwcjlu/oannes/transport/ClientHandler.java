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
		 if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {  
	            IdleStateEvent event = (IdleStateEvent) evt;  
	         
	        } 
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
		client.countDown();
	}
	

}
