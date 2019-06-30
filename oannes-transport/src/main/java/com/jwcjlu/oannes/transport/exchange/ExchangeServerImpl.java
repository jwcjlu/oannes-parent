package com.jwcjlu.oannes.transport.exchange;

import com.jwcjlu.oannes.common.spring.SpringBeanUtils;
import io.netty.channel.Channel;

import java.lang.reflect.Method;

import com.jwcjlu.oannes.transport.Header;
import com.jwcjlu.oannes.transport.MsgType;
import com.jwcjlu.oannes.transport.NettyServer;
import com.jwcjlu.oannes.transport.OannesMessage;
import com.oannes.common.RpcRequest;
import com.oannes.common.RpcResponse;
import com.oannes.common.URL;

public class ExchangeServerImpl implements ExchangeServer {

	@Override
	public void bind(URL url) {
		// TODO Auto-generated method stub
		new NettyServer(url.getHost(),url.getPort(),this);
	}

	@Override
	public void reply(Channel channel, RpcRequest request) {
		// TODO Auto-generated method stub
		Method method;
		RpcResponse res=new RpcResponse();
		OannesMessage  msg=new OannesMessage();
		try {
			Object obj= SpringBeanUtils.getBean(request.getType());
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
		Header header =new Header();
		header.setType(MsgType.RPC_RESP.getValue());
		msg.setHeader(header);
		msg.setBody(res);;
	    channel.writeAndFlush(msg);
	}

}
