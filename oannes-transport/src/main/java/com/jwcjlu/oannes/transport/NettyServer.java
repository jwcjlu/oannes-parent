package com.jwcjlu.oannes.transport;

import com.jwcjlu.oannes.transport.excption.RemoteException;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.jwcjlu.oannes.transport.codec.OannesEncoder;
import com.jwcjlu.oannes.transport.codec.OannesServerDecoder;
import com.jwcjlu.oannes.transport.exchange.ExchangeServer;

import java.net.InetSocketAddress;

public class NettyServer {
	private Channel channel;
	private NioEventLoopGroup binsgroup=new NioEventLoopGroup();
	private EventLoopGroup boss=new NioEventLoopGroup(2);
	private EventLoopGroup worker=new NioEventLoopGroup();
	private ExchangeServer server;
	private  ServerBootstrap  serBootstrap;
	public NettyServer(String host, int port,ExchangeServer server) {
		this.server=server;
		doOpen(new InetSocketAddress(host,port));

	}
	public void colse() throws RemoteException {
		channel.close();
		
	}
	public void doOpen(InetSocketAddress address) {
		serBootstrap=new ServerBootstrap();
		serBootstrap.group(boss, worker);
		serBootstrap.localAddress(address).channel(NioServerSocketChannel.class)
		.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline()
				.addLast( new OannesEncoder())
                .addLast( new OannesServerDecoder(8192, 14, 4))
				.addLast(binsgroup,new ServerHandler(server))
				;
				
			}
		});
		ChannelFuture f= serBootstrap.bind();
		f.awaitUninterruptibly();
		channel=f.channel();
		
		System.out.println(NettyServer.class.getName() + "started and listen on “" + f.channel(
				).localAddress());


	}
}
