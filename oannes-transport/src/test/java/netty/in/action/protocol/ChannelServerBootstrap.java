package netty.in.action.protocol;

import com.jwcjlu.oannes.transport.NettyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * <pre>
 * 
 *  File: ChannelServerBootstrap.java
 * 
 *  Copyright (c) 2017, globalegrow.com All Rights Reserved.
 * 
 *  Description:
 *  TODO
 * 
 *  Revision History
 *  Date,					Who,					What;
 *  2017年6月8日				Jinwei				Initial.
 *
 * </pre>
 */
public class ChannelServerBootstrap {
	private ServerBootstrap serverBootstrap;
	private EventLoopGroup boss;
	private EventLoopGroup worker;
	public ChannelServerBootstrap(String host,int port){
		boss=new NioEventLoopGroup();
		worker=new NioEventLoopGroup();
		serverBootstrap=new ServerBootstrap();
		serverBootstrap.group(boss, worker);
		serverBootstrap.channel(NioServerSocketChannel.class);
		serverBootstrap.localAddress(host,port)
		.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				// TODO Auto-generated method stub
				ch.pipeline().addLast(new NettyMessageDecoder(2048, 5, 4))
				.addLast(new NettyMessageEncoder())
				.addLast(new NettyServerHandler())
				;
			}
		});
		ChannelFuture f;
		try {
			f = serverBootstrap.bind().sync();
			System.out.println(NettyServer.class.getName() + "started and listen on “" + f.channel(
					).localAddress());
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
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

	public static void main(String[] args) {
		new  ChannelServerBootstrap("10.33.4.117", 6666);
	}

}

