package com.jwcjlu.oannes.transport;

public interface Server {
	void send();
	
	void bind(String ip,int port);

}
