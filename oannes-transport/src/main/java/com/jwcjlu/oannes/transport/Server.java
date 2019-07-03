package com.jwcjlu.oannes.transport;

import java.net.InetSocketAddress;
import java.util.Collection;

import io.netty.channel.Channel;

public interface Server {
	
	   boolean isBound() throws RemoteException;

	    /**
	     * get channels.
	     * 
	     * @return channels
	     */
	    Collection<Channel> getChannels()throws RemoteException;

	    /**
	     * get channel.
	     * 
	     * @param remoteAddress
	     * @return channel
	     */
	    Channel getChannel(InetSocketAddress remoteAddress)throws RemoteException;
	    
	    
	    
	    void close()throws RemoteException;

}
