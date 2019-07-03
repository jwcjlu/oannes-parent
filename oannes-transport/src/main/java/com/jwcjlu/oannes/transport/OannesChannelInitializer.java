package com.jwcjlu.oannes.transport;

import com.jwcjlu.oannes.transport.codec.OannesClientDecoder;
import com.jwcjlu.oannes.transport.codec.OannesEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.function.Supplier;

public class OannesChannelInitializer extends ChannelInitializer<SocketChannel> {
    private Supplier<Client>clientSupplier;

@Override
protected void initChannel(SocketChannel ch) throws Exception {
        // TODO Auto-generated method stub
        ch.pipeline()
        .addLast( new OannesEncoder())
        .addLast( new OannesClientDecoder(8192, 14, 4))
        .addLast("idleStateHandler",new IdleStateHandler(0, 0, 90))
        .addLast(new HeartbeatHander())
        .addLast(new ClientHandler(clientSupplier.get()));
 }
}
