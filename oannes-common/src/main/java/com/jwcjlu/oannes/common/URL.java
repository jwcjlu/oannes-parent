package com.jwcjlu.oannes.common;

public class URL {
	private final String host;

	private final int port;

	private final String path;
	
	private final String persisPath;
	
	

	public URL() {
        this.host = "127.0.0.1";
        this.port = 8888;
        this.path = null;
    
        this.persisPath="";
    }
    public URL(String path,String persisPath){
        this("127.0.0.1",8888,path,persisPath);
    }
    public URL(String  host,int port,String path,String persisPath){
  	    this.host = host;
        this.port = port;
        this.path = path;
        this.persisPath=persisPath;
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

	public String getPersisPath() {
		return persisPath;
	}
    
}
