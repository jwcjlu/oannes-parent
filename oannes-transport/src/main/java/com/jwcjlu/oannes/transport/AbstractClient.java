package com.jwcjlu.oannes.transport;

import com.jwcjlu.oannes.transport.clientpool.ClientFactory;
import com.jwcjlu.oannes.transport.excption.RemoteException;
import com.jwcjlu.oannes.transport.futrue.DefaultResponseFuture;
import com.jwcjlu.oannes.transport.futrue.ResponseFuture;
import com.jwcjlu.oannes.transport.protocol.Header;
import com.jwcjlu.oannes.transport.protocol.MsgType;
import com.jwcjlu.oannes.transport.protocol.OannesMessage;
import com.oannes.common.RpcRequest;
import com.oannes.common.RpcResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class AbstractClient extends SimpleChannelInboundHandler<RpcResponse> implements Client {
    private ClientFactory clientFactory;

    public AbstractClient(ClientFactory factory) {
        this.clientFactory = factory;
    }

    public static final String READ_TIMEOUT_HANDLER_NAME = "readTimeoutHandler";
    private Channel channel;
    private ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        try {
            channel = ctx.channel();
            this.ctx = ctx;
        } finally {
            super.channelActive(ctx);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse msg) throws Exception {
        try {
            DefaultResponseFuture.getResponsefutures().get(msg.getId()).receive(msg);
        } finally {
            removeReadTimeoutHandler();

        }


    }

    public void closeChannel(Promise<Void> promise) {
        channel.close().addListener(
                new GenericFutureListener<Future<? super Void>>() {
                    public void operationComplete(
                            Future<? super Void> future)
                            throws Exception {
                        if (future
                                .isSuccess()) {
                            promise.setSuccess(null);
                        } else {
                            promise.setFailure(future
                                    .cause());
                        }
                    }

                    ;
                });
    }

    public void disconnect() {
        if (channel == null) {
        } else {
            closeChannel(channel.newPromise());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        handlerExcption(cause);
        super.exceptionCaught(ctx, cause);
    }

    protected void handlerExcption(Throwable cause) {
        log.info("Disconnecting open connection to server");
        disconnect();
    }

    @Override
    public void close() throws RemoteException {
        disconnect();
    }

    @Override
    public boolean isConnected() throws RemoteException {
        return channel.isActive();
    }

    @Override
    public void send(Object msg) {
        OannesMessage omsg=new OannesMessage();
        Header header=new Header();
        header.setType(MsgType.RPC_REQ.getValue());
        omsg.setHeader(header);
        omsg.setBody(msg);
        channel.writeAndFlush(omsg);

    }

    public void startReadTimeoutHandler(long readTimeout) {
        channel.pipeline().addBefore("handler", READ_TIMEOUT_HANDLER_NAME, new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS));
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            // Log some info about this.
            removeReadTimeoutHandler();
        }
    }

    private void removeReadTimeoutHandler() {
        try {
            final ChannelPipeline pipeline = channel.pipeline();
            if (pipeline.get(READ_TIMEOUT_HANDLER_NAME) != null) {
                pipeline.remove(READ_TIMEOUT_HANDLER_NAME);
            }
        } finally {
            clientFactory.getClientPool().returnObject(this);
        }

    }

    @Override
    public ResponseFuture request(RpcRequest msg, CompletableFuture future) {
        send(msg);
        return new DefaultResponseFuture(msg,future);
    }

}
