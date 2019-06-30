package com.jwcjlu.oannes.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import com.oannes.common.Constants;
import com.oannes.common.NetUtil;
import com.oannes.common.URL;
import com.oannes.common.util.MapSortUtil;
import com.oannes.common.util.StringUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.env.Environment;

@Getter
@Setter
public abstract class ConfigBean  implements Serializable{

	/**
	 * 
	 */
    private static List<String> filterKeys=new ArrayList<String>();
	protected static ReentrantLock  lock=new ReentrantLock();
	private static final long serialVersionUID = 1L;
	protected String id;
	protected Class interfaces;
	protected String provider;
	protected String host;
	protected int port;
	protected String path;
	protected String applicationName;
	protected String registerUrl;
	protected Environment  environment;
	static{
	    
	    filterKeys.add("interface");
	    filterKeys.add("host");
	    filterKeys.add("port");
	}

	protected Map<String,Object> parameter=new HashMap<String,Object>();
	public void setter(OannService service){
		parameter.put("group", service.group());
		parameter.put("interface", service.interfaces().getName());
		parameter.put("version", service.version());
		host=NetUtil.getRemoteAddress().getHostAddress();
		parameter.put("host",host );
		path=service.interfaces().getName();
		interfaces=service.interfaces();
	
	}
	public void setter(OannConsumer consumer){
		interfaces=consumer.interfaces();
		parameter.put("group", consumer.group());
		path=consumer.interfaces().getName();
		parameter.put("interface", consumer.interfaces().getName());
		parameter.put("version", consumer.version());
		parameter.put("host", consumer.host());

		
			
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
		   if(obj!=null&&!filterParam(key)){
			   if(obj instanceof String){
				   if(StringUtils.isNotEmpty(obj.toString())){
					   if (isFirst) {
						sb.append("?").append(key).append("=").append(obj);
						isFirst=false;
					}else{
		                   sb.append("&").append(key).append("=").append(obj);  
		               }
				   }
			 
			   }
		   }
	   }
	   URL url=URL.valueOf(sb.toString());
	   url.setBackupAddress(environment.getProperty("oannes.registry.address"));
	
	  return url;
   }
   private boolean filterParam(String key)
{
    // TODO Auto-generated meth
    return filterKeys.contains(key);
}
public void setAttribute(String key,Object obj){
	   parameter.put(key, obj);
   }
   
 

}
