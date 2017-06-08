package com.jwcjlu.oannes.transport.exchange;

import java.lang.reflect.Method;

import com.jwcjlu.oannes.common.spring.SpringBeanUtils;
import com.jwcjlu.oannes.transport.NettyServer;
import com.oannes.common.RpcRequest;
import com.oannes.common.RpcResponse;
import com.oannes.common.URL;

import io.netty.channel.Channel;

public class ExchangeServerImpl implements ExchangeServer {

	@Override
	public void bind(URL url) {
		// TODO Auto-generated method stub
		new NettyServer(url.getHost(),url.getPort(),this);
	}

	@Override
	public void reply(Channel channel, RpcRequest request) {
		// TODO Auto-generated method stub
		 Object obj=SpringBeanUtils.getBean(request.getType());
		 Method method;
		 RpcResponse res=new RpcResponse();
		try {
			method = obj.getClass().getDeclaredMethod(request.getMethod(), request.getParameterTypes());
			 Object result=method.invoke(obj, request.getArgs());
			 res.setId(request.getId());
			 res.setCode(1000);
			 res.setResult(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			res.setCode(500);
			res.setResult(e);
		
		}
		 channel.writeAndFlush(res);
	}

}
