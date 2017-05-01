package com.jwcjlu.oannes.transport;

import java.util.concurrent.TimeUnit;

import com.jwcjlu.oannes.common.RpcRequest;
import com.jwcjlu.oannes.transport.futrue.DefaultResponseFuture;
import com.jwcjlu.oannes.transport.futrue.ResponseFuture;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class NettyClient extends AbstractClient{
	private Bootstrap bootstrap;
	private volatile Channel channel;
	private String key;
	private EventLoopGroup group;

	public NettyClient(String host, int port) {
		super(host, port);
	    key=host+":"+port;
	}


	@Override
	public boolean isConnected() throws RemoteException {
		// TODO Auto-generated method stub
		return channel!=null&&channel.isWritable();
	}

	@Override
	public boolean isClose() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void doConnect() {
		// TODO Auto-generated method stub
		ChannelFuture f;
		try {
			
		f = bootstrap.connect().sync();
			channel=f.channel();
			channel.closeFuture().sync();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			
		}finally {
			reconnect();
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
		}
		
	}

	@Override
	public void doOpen() throws RemoteException {
		// TODO Auto-generated method stub
		 group =new NioEventLoopGroup();
		bootstrap=new Bootstrap();
		
		bootstrap.group(group).channel(NioSocketChannel.class)
		.remoteAddress(getConnectAddress())
		.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				// TODO Auto-generated method stub
				ch.pipeline()
				.addLast("idleStateHandler",new IdleStateHandler(0, 0, 15))
				.addLast( MarshallingCodeCFactory.buildMarshallingDecoder())
				.addLast( MarshallingCodeCFactory.buildMarshallingEncoder())
				.addLast(new ClientHandler())
				;
				
			}
		});
		
	}


	@Override
	public void send(Object msg) {
		// TODO Auto-generated method stub
		channel.writeAndFlush(msg);
	}


	@Override
	public ResponseFuture request(RpcRequest msg) {
		// TODO Auto-generated method stub
		   send(msg);
		return new DefaultResponseFuture(msg);
	}


	@Override
	protected void doClose() throws InterruptedException {
		// TODO Auto-generated method stub
		if(channel!=null){
			try {
				channel.close().sync();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(bootstrap!=null){
			bootstrap.group().shutdownGracefully().sync();
		}
	}
	public String getKey(){
		return key;
	}


	@Override
	protected void doReconnect() {
		// TODO Auto-generated method stub
		try {
			if(isConnected()){
				return;
			}
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(channel!=null){
			try {
				System.out.println("开始重连！！！");
				channel.disconnect().sync();
				super.connect();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			System.out.println("dafdsafsd");
			try {
				super.connect();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
