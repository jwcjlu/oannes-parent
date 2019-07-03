package com.jwcjlu.oannes.transport;

import com.jwcjlu.oannes.transport.codec.OannesClientDecoder;
import com.jwcjlu.oannes.transport.codec.OannesEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    private ConcurrentHashMap<InetSocketAddress,ConcurrentHashMap<Integer,CompletableFuture<Client>>>connectionPool=
            new ConcurrentHashMap<>();
    private EventLoopGroup eventExecutors;
    private Bootstrap bootstrap;
    private int maxConnectionsPerHosts;
    private Random random=new Random();
    public ConnectionManager(NettyConfiguration configuration,EventLoopGroup eventExecutors){
        this.maxConnectionsPerHosts=configuration.maxConnectionsPerHosts();
        this.eventExecutors=eventExecutors;
        bootstrap=new Bootstrap();
        if(eventExecutors instanceof EpollEventLoopGroup){
            bootstrap.group(eventExecutors).channel(NioSocketChannel.class);
        }else{
            bootstrap.group(eventExecutors).channel(NioSocketChannel.class);
        }
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, configuration.getConnectionTimeoutMs());
        bootstrap.option(ChannelOption.TCP_NODELAY, configuration.isUseTcpNoDelay());
        bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    // TODO Auto-generated method stub
                    ch.pipeline()
                            .addLast( new OannesEncoder())
                            .addLast( new OannesClientDecoder(8192, 14, 4))
                            .addLast("idleStateHandler",new IdleStateHandler(0, 0, 90))
                            .addLast(new HeartbeatHander())
                            .addLast(new ClientHandler(client));
                }
                });
    }
    public CompletableFuture<Client> getClient(InetSocketAddress rometeAddress){
        if(maxConnectionsPerHosts==0){
            return createClient(rometeAddress);
        }
        final int randomKey = signSafeMod(random.nextInt(), maxConnectionsPerHosts);
       return connectionPool.computeIfAbsent(rometeAddress,a->new ConcurrentHashMap<>()).computeIfAbsent(randomKey
        ,k->createClient(randomKey,rometeAddress));

    }

    private CompletableFuture<Client> createClient(int randomKey, InetSocketAddress rometeAddress) {
       int port= rometeAddress.getPort();
       InetAddress ipAddress=rometeAddress.getAddress();
        return null;
    }
    private CompletableFuture<Client> createClient( InetSocketAddress rometeAddress) {
        int port= rometeAddress.getPort();
        InetAddress ipAddress=rometeAddress.getAddress();
        return null;
    }

    private CompletableFuture<Channel> connectToAddress(InetAddress ipAddress, int port) {
        CompletableFuture<Channel> future = new CompletableFuture<>();
        bootstrap.connect(ipAddress, port).addListener((ChannelFuture channelFuture) -> {
            if (channelFuture.isSuccess()) {
                future.complete(channelFuture.channel());
            } else {
                future.completeExceptionally(channelFuture.cause());
            }
        });

        return future;
    }
    public void close(){
        eventExecutors.shutdownGracefully();
    }
    public static int signSafeMod(long dividend, int divisor) {
        int mod = (int) (dividend % (long) divisor);
        if (mod < 0) {
            mod += divisor;
        }
        return mod;
    }
}
