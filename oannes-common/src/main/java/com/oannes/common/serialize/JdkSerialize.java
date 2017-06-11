package com.oannes.common.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <pre>
 * 
 *  File: JdkSerialize.java
 * 
 *  Copyright (c) 2017, globalegrow.com All Rights Reserved.
 * 
 *  Description:
 *  TODO
 * 
 *  Revision History
 *  Date,					Who,					What;
 *  2017年6月11日				jinwei				Initial.
 *
 * </pre>
 */
public class JdkSerialize {
	
	public byte[] serialize(Object obj) throws IOException{
		byte[] buff=null;
		try(
		ByteArrayOutputStream  os=new ByteArrayOutputStream();
		ObjectOutputStream  oos=new ObjectOutputStream(os);){
		oos.writeObject(obj);
		buff=os.toByteArray();
		}
		return buff;
	}
	public Object derialize(byte[]buff) throws IOException, ClassNotFoundException{
		Object obj=null;
		try(
				ByteArrayInputStream  bais=new ByteArrayInputStream(buff);
				ObjectInputStream ois=new ObjectInputStream(bais);
				){
			obj=ois.readObject();
		}
		return obj;
	}

}

