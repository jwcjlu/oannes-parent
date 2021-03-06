package netty.in.action.protocol;

import com.oannes.common.util.SerializeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * <pre>
 * 
 *  File: NettyMessageEncoder.java
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
public class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage>{

	/* (non-Javadoc)
	 * @see io.netty.handler.codec.MessageToByteEncoder#encode(io.netty.channel.ChannelHandlerContext, java.lang.Object, io.netty.buffer.ByteBuf)
	 */
	@Override
	protected void encode(ChannelHandlerContext ctx, NettyMessage msg,
			ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
		out.writeByte(msg.getHeader().getType());
		out.writeInt(msg.getHeader().getVersion());
		byte[]buff=null;
		if(msg.getBody()!=null){
			buff= SerializeUtil.serialize(msg.getBody());
			out.writeInt(buff.length);
			out.writeBytes(buff);
		}else{
			out.writeInt(0);
		}	
	}

}

