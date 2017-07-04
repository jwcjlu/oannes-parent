package netty.in.action.protocol;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * <pre>
 * 
 *  File: ChannelClientBootstrap.java
 * 
 *  Copyright (c) 2017,jwcjlu.com All Rights Reserved.
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
public class ChannelClientBootstrap {
	ScheduledExecutorService  service=Executors.newScheduledThreadPool(1);
	private Bootstrap bootstrap;
	private EventLoopGroup group;
	private String inetHost;
	private int port;
	private Channel channel;
	public ChannelClientBootstrap(String host,int port){
		inetHost=host;
		this.port=port;
		group = new NioEventLoopGroup();
		bootstrap = new Bootstrap();
		bootstrap.group(group);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel channel) throws Exception {
				ChannelPipeline pipeline = channel.pipeline();
				pipeline.addLast(new NettyMessageDecoder(2048, 5, 4));
				pipeline.addLast(new NettyMessageEncoder());
				pipeline.addLast(new IdleStateHandler(0, 0, 10));
				pipeline.addLast(new NettyClientHandler());
				pipeline.addLast(new ChannelInboundHandlerAdapter() {
					@Override
					public void channelInactive(ChannelHandlerContext ctx)throws Exception {
						 reconnect();
						
					}
					 @Override
					    public void channelActive(ChannelHandlerContext ctx) throws Exception {
						 
					    }
					
				});
			}
		});
		
	}
	public synchronized void connect(){
		if(channel!=null){
			channel.close();
		}
		channel=bootstrap.connect(inetHost, port).channel();
		
		
	}
	private void reconnect(){
		
	    boolean retry=false;
		java.util.concurrent.Future<Boolean> future=service.submit(reConnectTask);
		try {
			retry=future.get(1, TimeUnit.SECONDS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		} 
		if(!retry){
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
			reconnect();
		}
	}
	public static void main(String[] args) {
		new ChannelClientBootstrap("10.33.4.117", 6666).connect();
	}
	Callable<Boolean> reConnectTask= new Callable<Boolean>() {

		@Override
		public Boolean call() throws Exception {
			// TODO Auto-generated method stub
			System.out.println("reconnect");
			Boolean flag=false;
			try{
				if(channel==null||!channel.isActive()||!channel.isRegistered()||!channel.isWritable()){
					System.out.println("retry");
					connect();
				}else{
					flag=true;
				}
			}catch(Exception e){
				
			}
			return flag;
		}


	
		
	};

}

