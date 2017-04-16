package com.jwcjlu.oannes.transport;

public interface Client {
	void connect(String ip,int port);
	
	void reconnect();
	
	void  colse();

}
