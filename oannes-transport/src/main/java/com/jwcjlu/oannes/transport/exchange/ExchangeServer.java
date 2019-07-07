package com.jwcjlu.oannes.transport.exchange;

import com.oannes.common.RpcRequest;
import com.oannes.common.URL;

import io.netty.channel.Channel;

public interface ExchangeServer {
	 void bind(URL url);
	
	  void reply(Channel channel,RpcRequest request);

}
