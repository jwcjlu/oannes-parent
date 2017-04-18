package com.jwcjlu.oannes.common;

import java.util.Map;

public class URL {
	private final String host;

	private final int port;

	private final String path;
	
	private final Map<String, String> parameters;
    protected URL() {
        this.host = "127.0.0.1";
        this.port = 8888;
        this.path = null;
        this.parameters = null;
    }
    public URL(String path,Map<String, String> parameters){
    	  this.host = "127.0.0.1";
          this.port = 8888;
          this.path = path;
          this.parameters = parameters;
    }
    public URL(String  host,int port,String path,Map<String, String> parameters){
  	    this.host = host;
        this.port = port;
        this.path = path;
        this.parameters = parameters;
  }
	public String getHost() {
		return host;
	}
	public int getPort() {
		return port;
	}
	public String getPath() {
		return path;
	}
	public Map<String, String> getParameters() {
		return parameters;
	}
    
}
