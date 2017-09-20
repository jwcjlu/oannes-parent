package com.jwcjlu.oannes.transport;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.jwcjlu.oannes.transport.exchange.ExchangeServer;
import com.oannes.common.RpcRequest;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<RpcRequest>{
	private final ConcurrentMap<String, Channel> channelMap=new ConcurrentHashMap<String,Channel>();
	private ExchangeServer server;
	public ServerHandler(ExchangeServer server){
		this.server=server;
	}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
		// TODO Auto-generated method stub
	    if(msg==null){
	        return;
	    }
		
		 server.reply(ctx.channel(), msg);
		
		
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelRegistered(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
		
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.handlerAdded(ctx);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.handlerRemoved(ctx);
	}
	

}
