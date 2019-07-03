package com.jwcjlu.oannes.transport;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import com.jwcjlu.oannes.transport.exchange.ExchangeServer;
import com.oannes.common.NetUtil;

import io.netty.channel.Channel;

public abstract class AbstractServer implements Server {

    private InetSocketAddress              localAddress;
    protected  ExchangeServer server;
    private InetSocketAddress              bindAddress;
	private ConcurrentHashMap<SocketAddress,Channel> channels=new ConcurrentHashMap<SocketAddress,Channel>();
	public abstract void doOpen();
	public AbstractServer(String host,int port, ExchangeServer server){
		bindAddress=new InetSocketAddress(host,port);
		this.server=server;
		doOpen();
		
	}
	public SocketAddress getBindAddress(){
		return bindAddress;
	}
	@Override
	public boolean isBound() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Collection<Channel> getChannels() {
		// TODO Auto-generated method stub
		return channels.values();
	}
	@Override
	public Channel getChannel(InetSocketAddress remoteAddress) {
		// TODO Auto-generated method stub
		String key=remoteAddress.getHostString()+remoteAddress.getPort();
		return channels.get(key);
	}
	@Override
	public void close() throws RemoteException{
		Collection<Channel> channels=getChannels();
		if(channels==null){
			return;
		}
		for(Channel channel:channels){
			channel.close();
		}

	}


}