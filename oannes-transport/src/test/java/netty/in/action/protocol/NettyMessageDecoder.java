package netty.in.action.protocol;

import com.jwcjlu.oannes.transport.SerializeUtil;

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
		ByteBuf  buf=(ByteBuf) super.decode(ctx, in);
		if(buf==null){
			throw new Exception("after decode ,in is null");
		}
		Object obj=null;
		byte type=buf.readByte();
		int version=buf.readInt();
		int length=buf.readInt();
		if(type!=0&&version==1){
			ByteBuf msgs=buf.readBytes(length);
			byte[]dst=new byte[msgs.readableBytes()];
			msgs.readBytes(dst);
			obj=SerializeUtil.derialize(dst);
			NettyMessage msg=new NettyMessage();
			Header header=new Header(type,version,length);
			msg.setHeader(header);
			msg.setBody(obj);
			return msg;
		}
		
	    return obj;
	}



}
