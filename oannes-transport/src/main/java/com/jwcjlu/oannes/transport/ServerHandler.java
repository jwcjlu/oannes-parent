package com.jwcjlu.oannes.transport;

import java.lang.reflect.Method;

import com.jwcjlu.oannes.common.RpcRequest;
import com.jwcjlu.oannes.common.RpcResponse;
import com.jwcjlu.oannes.common.spring.SpringBeanUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<RpcRequest>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
		// TODO Auto-generated method stub
		 Object obj=SpringBeanUtils.getBean(msg.getType());
		 Method method= obj.getClass().getDeclaredMethod(msg.getMethod(), msg.getParameterTypes());
		 Object result=method.invoke(obj, msg.getArgs());
		 RpcResponse res=new RpcResponse();
		 res.setId(msg.getId());
		 res.setCode(1000);
		 res.setResult(result);
		 ctx.writeAndFlush(res);
		
	}

}
