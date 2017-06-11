package com.jwcjlu.oannes.config;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantLock;

import com.oannes.common.Constants;
import com.oannes.common.NetUtil;
import com.oannes.common.URL;
import com.oannes.common.util.MapSortUtil;
import com.oannes.common.util.StringUtils;

public abstract class ConfigBean  implements Serializable{

	/**
	 * 
	 */
	protected static ReentrantLock  lock=new ReentrantLock();
	private static final long serialVersionUID = 1L;
	protected String id;
	protected Class interfaces;
	protected String provider;
	protected String host;
	protected int port;
	protected String path;

	protected Map<String,Object> parameter=new HashMap<String,Object>();

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Class getInterfaces() {
		return interfaces;
	}
	public void setInterfaces(Class interfaces) {
		this.interfaces = interfaces;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public void setter(OannService service,RegisterBean register){
		parameter.put("group", service.group());
		parameter.put("interface", service.interfaces().getName());
		parameter.put("version", service.version());
		parameter.put("port", register.get("port"));
		port=Integer.parseInt(register.get("port").toString());;
		host=NetUtil.getRemoteAddress().getHostAddress();
		parameter.put("host",host );
		path=service.interfaces().getName();
	
	}
	public void setter(OannConsumer consumer,RegisterBean register){
		parameter.put("group", consumer.group());
		path=consumer.interfaces().getName();
		parameter.put("interface", consumer.interfaces().getName());
		parameter.put("version", consumer.version());
		parameter.put("port",register.get("port"));
		parameter.put("host", consumer.host());
		port=Integer.parseInt(register.get("port").toString());
		
			
	}
   @SuppressWarnings("rawtypes")
public  URL builderURL(){
	   StringBuilder sb=new StringBuilder(Constants.ROOT+"://"+host+":"+port);
	   sb.append(Constants.PATH_SEPARATOR).append(path);
	   Map<String,Object> map=MapSortUtil.sortMapByKey(parameter);
	   boolean isFirst=true;
	   Iterator iter=map.entrySet().iterator();  
	   while(iter.hasNext()){
		 Map.Entry ent=(Map.Entry )iter.next();  
       String key=ent.getKey().toString();  
		   Object obj=ent.getValue();
		   if(obj!=null){
			   if(obj instanceof String){
				   if(StringUtils.isNotEmpty(obj.toString())){
					   if (isFirst) {
						sb.append("?").append(key).append("=").append(obj);
						isFirst=false;
					}
				   }
			   }else{
			   if(isFirst){
				   sb.append("?").append(key).append("=").append(obj);
					isFirst=false;
			   }else{
				   sb.append("&").append(key).append("=").append(obj);  
			   }
			   }
		   }
	   }
	   URL url=URL.valueOf(sb.toString());
	
	  return url;
   }
   public void setAttribute(String key,Object obj){
	   parameter.put(key, obj);
   }
   
 

}
