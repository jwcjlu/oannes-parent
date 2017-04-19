package com.jwcjlu.oannes.transport;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import com.jwcjlu.oannes.common.NetUtil;

import io.netty.channel.Channel;

public abstract class AbstractServer implements Server {

    private InetSocketAddress              localAddress;

    private InetSocketAddress              bindAddress;
	private ConcurrentHashMap<SocketAddress,Channel> channels=new ConcurrentHashMap<SocketAddress,Channel>();
	public abstract void doOpen();
	public AbstractServer(String host,int port){
		bindAddress=new InetSocketAddress(host,port);
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
		return null;
	}
	@Override
	public Channel getChannel(InetSocketAddress remoteAddress) {
		// TODO Auto-generated method stub
		String key=remoteAddress.getHostString()+remoteAddress.getPort();
		return channels.get(key);
	}
	public static void main(String[] args) {
		InetSocketAddress sa=new InetSocketAddress(1111);
		System.out.println(NetUtil.getRemoteAddress());
	}

}