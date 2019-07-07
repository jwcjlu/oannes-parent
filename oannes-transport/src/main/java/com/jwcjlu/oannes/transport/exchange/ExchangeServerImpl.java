package com.jwcjlu.oannes.transport.exchange;

import com.jwcjlu.oannes.common.spring.SpringBeanUtils;
import com.jwcjlu.oannes.transport.NettyServer;
import com.jwcjlu.oannes.transport.protocol.Header;
import com.jwcjlu.oannes.transport.protocol.MsgType;
import com.jwcjlu.oannes.transport.protocol.OannesMessage;
import com.oannes.common.ResultType;
import com.oannes.common.RpcRequest;
import com.oannes.common.RpcResponse;
import com.oannes.common.URL;
import io.netty.channel.Channel;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

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
			 res.setCode(ResultType.OK);
			 if(request.isAsyn()){
				CompletableFuture<Object> r=(CompletableFuture<Object>) result;
				r.whenComplete((v,t)->{
					if(t==null){
						res.setResult(v);
					}else{
						res.setCode(ResultType.FAIL);
						res.setResult(t);
					}
				});
			 }else{
				 res.setResult(result);
			 }

		} catch (Exception e) {
			res.setCode(ResultType.FAIL);
			res.setResult(e);
		
		}
		Header header =new Header();
		header.setType(MsgType.RPC_RESP.getValue());
		msg.setHeader(header);
		msg.setBody(res);;
	    channel.writeAndFlush(msg);

	}

}
