package com.jwcjlu.oannes.transport;

import com.jwcjlu.oannes.transport.exchange.ExchangeServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer extends AbstractServer{
	
	public NettyServer(String host, int port,ExchangeServer server) {	
		super(host, port,server);
		// TODO Auto-generated constructor stub	
	}
	private  ServerBootstrap  serBootstrap;

	@Override
	public void colse() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
 
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
				.addLast( MarshallingCodeCFactory.buildMarshallingDecoder())
				.addLast( MarshallingCodeCFactory.buildMarshallingEncoder())
				.addLast(new ServerHandler(server))
				;
				
			}
		});
		ChannelFuture f= serBootstrap.bind().sync();
		
		System.out.println(NettyServer.class.getName() + "started and listen on “" + f.channel(
				).localAddress());
		f.channel().closeFuture().sync();
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