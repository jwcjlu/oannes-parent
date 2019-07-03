package com.jwcjlu.oannes.transport;

import io.netty.bootstrap.ServerBootstrap;
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
	
	public NettyServer(String host, int port,ExchangeServer server) {	
		super(host, port,server);
		// TODO Auto-generated constructor stub	
	}
	private  ServerBootstrap  serBootstrap;

 
	@Override
	public void doOpen() {
		// TODO Auto-generated method stub
		EventLoopGroup boss=new NioEventLoopGroup();
		EventLoopGroup worker=new NioEventLoopGroup();
		try{
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

	  }catch(Exception e){
		  e.printStackTrace();
	}finally{
		try {
			boss.shutdownGracefully().sync();
			worker.shutdownGracefully().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	}
}
