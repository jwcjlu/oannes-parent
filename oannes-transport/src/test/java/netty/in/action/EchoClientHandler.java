package netty.in.action;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf>{
	public void channelActive(ChannelHandlerContext ctx) throws Exception{
		ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!",CharsetUtil.UTF_8));
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Client received: " + 
		ByteBufUtil.hexDump(msg.readBytes(msg.readableBytes())));
	}
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
