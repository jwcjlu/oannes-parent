package netty.in.action.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {

	public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		// TODO Auto-generated method stub
		return super.decode(ctx, in);
	}



}
