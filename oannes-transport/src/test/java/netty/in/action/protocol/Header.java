package netty.in.action.protocol;


public final class Header {
		//版本
		private int version=1;
		//消息长度
		private int length;
		//小心类型
		private byte type;
		public int getVersion() {
			return version;
		}
		public void setVersion(int version) {
			this.version = version;
		}
		public int getLength() {
			return length;
		}
		public void setLength(int length) {
			this.length = length;
		}
		public byte getType() {
			return type;
		}
		public void setType(byte type) {
			this.type = type;
		}
		@Override
		public String toString() {
			return "Header [version=" + version + ", length=" + length
					+ ", type=" + type + "]";
		}
		/**
		 * Header Constructor. 
		 *
		 * @param version
		 * @param length
		 * @param type
		 */
		public Header( byte type,int version, int length) {
			super();
			this.version = version;
			this.length = length;
			this.type = type;
		}
	
	

	
       
}
