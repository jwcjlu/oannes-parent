package com.jwcjlu.oannes.transport;

import com.jwcjlu.oannes.transport.clientpool.ClientFactory;
import com.jwcjlu.oannes.transport.codec.OannesClientDecoder;
import com.jwcjlu.oannes.transport.codec.OannesEncoder;
import com.oannes.common.URL;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class NettyClient extends AbstractClient implements Client {

    private Bootstrap bootstrap;

    public NettyClient(URL origin, ClientFactory clientFactory, EventLoopGroup eventExecutors) throws InterruptedException {
        super(clientFactory);
        bootstrap = new Bootstrap();
        bootstrap.group(eventExecutors);
        if (eventExecutors instanceof NioEventLoopGroup) {
            bootstrap.channel(NioSocketChannel.class);
        } else {
            bootstrap.channel(EpollSocketChannel.class);
        }
        ChannelInitializer<Channel> initializer = new ChannelInitializer<Channel>() {
            protected void initChannel(Channel ch) throws Exception {
                initChannelPipeline(ch.pipeline());
            }

            ;
        };
        bootstrap.option(ChannelOption.TCP_NODELAY, true)
                // 如果是延时敏感型应用，建议关闭Nagle算法
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT);
        bootstrap.handler(initializer);
        ChannelFuture future = bootstrap.connect(origin.getHost(), origin.getPort()).sync();
        if (future.isSuccess()) {
            future.await(1000, TimeUnit.MILLISECONDS);
        }

    }


    public void startReadTimeoutHandler() {
        startReadTimeoutHandler(60 * 1000);
    }


    private void initChannelPipeline(ChannelPipeline pipeline) {
        pipeline.addLast( new OannesEncoder())
                .addLast( new OannesClientDecoder(8192, 14, 4))
                .addLast("idleStateHandler",new IdleStateHandler(0, 0, 90))
                .addLast(new HeartbeatHander(this))
                .addLast("handler", this);
    }

}
