package com.jwcjlu.oannes.common;

import java.net.InetAddress;
import java.net.UnknownHostException;

public final class NetUtil {
  public static InetAddress getRemoteAddress() {

	  try {
		return InetAddress.getLocalHost();
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return null;
  }
  public static void main(String[] args)  {
	System.out.println(getRemoteAddress());
}
}
