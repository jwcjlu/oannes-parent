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

public class NettyServer extends AbstractServer{
	private Channel channel;
	
	public NettyServer(String host, int port,ExchangeServer server) {	
		super(host, port,server);
		// TODO Auto-generated constructor stub	
	}
	private  ServerBootstrap  serBootstrap;

	@Override
	public void colse() throws RemoteException {
		// TODO Auto-generated method stub
		channel.close();
		
	}
 
	@Override
	public void doOpen() {
		// TODO Auto-generated method stub
		EventLoopGroup boss=new NioEventLoopGroup();
		EventLoopGroup worker=new NioEventLoopGroup();

		serBootstrap=new ServerBootstrap();
		serBootstrap.group(boss, worker);
		serBootstrap.localAddress(getBindAddress()).channel(NioServerSocketChannel.class)
		.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				// TODO Auto-generated method stub
				ch.pipeline()
				.addLast( new OannesEncoder())
                .addLast( new OannesServerDecoder(8192, 14, 4))
				.addLast(new ServerHandler(server))
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
