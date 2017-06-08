package com.jwcjlu.oannes.transport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <pre>
 * 
 *  File: Ser.java
 * 
 *  Copyright (c) 2017, globalegrow.com All Rights Reserved.
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
public final class SerializeUtil {

	public static byte[] serialize(Object target) throws IOException {
		byte[] buff = null;
		try (ByteArrayOutputStream os = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(os);) {
			oos.writeObject(target);
			buff = os.toByteArray();
		}
		return buff;
	}

	public static Object derialize(byte[] buff) throws IOException,
			ClassNotFoundException {
		Object obj = null;
		try (ByteArrayInputStream os = new ByteArrayInputStream(buff);
				ObjectInputStream oos = new ObjectInputStream(os);) {
			obj = oos.readObject();
		}
		return obj;
	}

	public static void main(String[] args) throws ClassNotFoundException,
			IOException {
		String msg = "aaaaa";
		System.out.println(derialize(serialize(msg)));
	}

}
