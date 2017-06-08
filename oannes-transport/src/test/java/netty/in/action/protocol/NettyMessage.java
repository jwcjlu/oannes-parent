package netty.in.action.protocol;

import java.io.Serializable;

public final class NettyMessage implements Serializable{
	/**
	 * Comment for &lt;code&gt;serialVersionUID&lt;/code&gt;
	 */
	private static final long serialVersionUID = 4169403285528815496L;
	private Header header;// 消息头
	private Object body;// 消息体

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "NettyMessage [header=" + header + ", body=" + body + "]";
	}

}
