package com.oannes.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.StringUtils;

import com.oannes.common.util.CollectionUtils;

public  class URL {
	private final String host;

	private final int port;

	private final String path;
	private  String url;
	
	
    private final String protocol;

	private final String username;

	private final String password;

    private final Map<String, String> parameters;

	public URL() {
        this.protocol = null;
        this.username = null;
        this.password = null;
        this.host = null;
        this.port = 0;
        this.path = null;
        this.parameters = null;
        this.url=null;
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
	public String getAddress(){
		return host+":"+port;
	}

	public String getBackupAddress() {
		// TODO Auto-generated method stub
		return parameters.get("backupAddress");
	}
	public String getAuthority() {
		// TODO Auto-generated method stub
		return null;
	}
    public static String encode(String value) {
        if (value == null || value.length() == 0) { 
            return "";
        }
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    public static String decode(String value) {
        if (value == null || value.length() == 0) { 
            return "";
        }
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    public static URL valueOf(String url) {
    	String u=url;
        if (url == null || (url = url.trim()).length() == 0) {
            throw new IllegalArgumentException("url == null");
        }
        String protocol = null;
        String username = null;
        String password = null;
        String host = null;
        int port = 0;
        String path = null;
        Map<String, String> parameters = null;
        int i = url.indexOf("?"); // seperator between body and parameters 
        if (i >= 0) {
            String[] parts = url.substring(i + 1).split("\\&");
            parameters = new HashMap<String, String>();
            for (String part : parts) {
                part = part.trim();
                if (part.length() > 0) {
                    int j = part.indexOf('=');
                    if (j >= 0) {
                        parameters.put(part.substring(0, j), part.substring(j + 1));
                    } else {
                        parameters.put(part, part);
                    }
                }
            }
            url = url.substring(0, i);
        }
        i = url.indexOf("://");
        if (i >= 0) {
            if(i == 0) throw new IllegalStateException("url missing protocol: \"" + url + "\"");
            protocol = url.substring(0, i);
            url = url.substring(i + 3);
        }
        else {
            // case: file:/path/to/file.txt
            i = url.indexOf(":/");
            if(i>=0) {
                if(i == 0) throw new IllegalStateException("url missing protocol: \"" + url + "\"");
                protocol = url.substring(0, i);
                url = url.substring(i + 1);
            }
        }
        
        i = url.indexOf("/");
        if (i >= 0) {
            path = url.substring(i + 1);
            url = url.substring(0, i);
        }
        i = url.indexOf("@");
        if (i >= 0) {
            username = url.substring(0, i);
            int j = username.indexOf(":");
            if (j >= 0) {
                password = username.substring(j + 1);
                username = username.substring(0, j);
            }
            url = url.substring(i + 1);
        }
        i = url.indexOf(":");
        if (i >= 0 && i < url.length() - 1) {
            port = Integer.parseInt(url.substring(i + 1));
            url = url.substring(0, i);
        }
        if(url.length() > 0) host = url;
       URL urll= new URL(protocol, username, password, host, port, path, parameters);
       urll.setUrl(u);
        return urll;
    }
	public URL(String protocol, String host, int port) {
	    this(protocol, null, null, host, port, null, (Map<String, String>) null);
	}
	
	public URL(String protocol, String host, int port, String[] pairs) { // 变长参数...与下面的path参数冲突，改为数组
        this(protocol, null, null, host, port, null, CollectionUtils.toStringMap(pairs));
    }
	
	public URL(String protocol, String host, int port, Map<String, String> parameters) {
        this(protocol, null, null, host, port, null, parameters);
    }
	
	public URL(String protocol, String host, int port, String path) {
	    this(protocol, null, null, host, port, path, (Map<String, String>) null);
	}

	public URL(String protocol, String host, int port, String path, String... pairs) {
        this(protocol, null, null, host, port, path, CollectionUtils.toStringMap(pairs));
    }
	
	public URL(String protocol, String host, int port, String path, Map<String, String> parameters) {
		this(protocol, null, null, host, port, path, parameters);
	}
	
	public URL(String protocol, String username, String password, String host, int port, String path) {
        this(protocol, username, password, host, port, path, (Map<String, String>) null);
    }
	
	public URL(String protocol, String username, String password, String host, int port, String path, String... pairs) {
	    this(protocol, username, password, host, port, path, CollectionUtils.toStringMap(pairs));
	}
	public URL(String protocol, String username, String password, 
			String host, int port, String path, Map<String, String> parameters) {
		if ((username == null || username.length() == 0) 
				&& password != null && password.length() > 0) {
			throw new IllegalArgumentException("Invalid url, password without username!");
		}
		this.protocol = protocol;
		this.username = username;
		this.password = password;
		this.host = host;
		this.port = (port < 0 ? 0 : port);
		this.path = path;
		// trim the beginning "/"
		while(path != null && path.startsWith("/")) {
		    path = path.substring(1);
		}
		if (parameters == null) {
		    parameters = new HashMap<String, String>();
		} else {
		    parameters = new HashMap<String, String>(parameters);
		}
		this.parameters = Collections.unmodifiableMap(parameters);
	}

	public String getProtocol() {
		return protocol;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}
	   public String getServiceInterface() {
	        return getParameter(Constants.INTERFACE_KEY, path);
	    }
	   public String getParameter(String key,String defaultValue){
		   String value=getParameter(key);
		   if(StringUtils.isEmpty(value)){
			   return defaultValue;
		   }
		   return value;
	   }
	   public String getParameter(String key){
		   return parameters.get(key);
		 
	   }
	   public double getParameter(String key, double defaultValue) {
	        Number n = getNumbers().get(key);
	        if (n != null) {
	            return n.doubleValue();
	        }
	        String value = getParameter(key);
	        if (value == null || value.length() == 0) {
	            return defaultValue;
	        }
	        double d = Double.parseDouble(value);
	        getNumbers().put(key, d);
	        return d;
	    }
	    
	    public float getParameter(String key, float defaultValue) {
	        Number n = getNumbers().get(key);
	        if (n != null) {
	            return n.floatValue();
	        }
	        String value = getParameter(key);
	        if (value == null || value.length() == 0) {
	            return defaultValue;
	        }
	        float f = Float.parseFloat(value);
	        getNumbers().put(key, f);
	        return f;
	    }

	    public long getParameter(String key, long defaultValue) {
	        Number n = getNumbers().get(key);
	        if (n != null) {
	            return n.longValue();
	        }
	        String value = getParameter(key);
	        if (value == null || value.length() == 0) {
	            return defaultValue;
	        }
	        long l = Long.parseLong(value);
	        getNumbers().put(key, l);
	        return l;
	    }

	    public int getParameter(String key, int defaultValue) {
	        Number n = getNumbers().get(key);
	        if (n != null) {
	            return n.intValue();
	        }
	        String value = getParameter(key);
	        if (value == null || value.length() == 0) {
	            return defaultValue;
	        }
	        int i = Integer.parseInt(value);
	        getNumbers().put(key, i);
	        return i;
	    }

	    public short getParameter(String key, short defaultValue) {
	        Number n = getNumbers().get(key);
	        if (n != null) {
	            return n.shortValue();
	        }
	        String value = getParameter(key);
	        if (value == null || value.length() == 0) {
	            return defaultValue;
	        }
	        short s = Short.parseShort(value);
	        getNumbers().put(key, s);
	        return s;
	    }

	    public byte getParameter(String key, byte defaultValue) {
	        Number n = getNumbers().get(key);
	        if (n != null) {
	            return n.byteValue();
	        }
	        String value = getParameter(key);
	        if (value == null || value.length() == 0) {
	            return defaultValue;
	        }
	        byte b = Byte.parseByte(value);
	        getNumbers().put(key, b);
	        return b;
	    }

	    public float getPositiveParameter(String key, float defaultValue) {
	        if (defaultValue <= 0) {
	            throw new IllegalArgumentException("defaultValue <= 0");
	        }
	        float value = getParameter(key, defaultValue);
	        if (value <= 0) {
	            return defaultValue;
	        }
	        return value;
	    }

	    public double getPositiveParameter(String key, double defaultValue) {
	        if (defaultValue <= 0) {
	            throw new IllegalArgumentException("defaultValue <= 0");
	        }
	        double value = getParameter(key, defaultValue);
	        if (value <= 0) {
	            return defaultValue;
	        }
	        return value;
	    }

	    public long getPositiveParameter(String key, long defaultValue) {
	        if (defaultValue <= 0) {
	            throw new IllegalArgumentException("defaultValue <= 0");
	        }
	        long value = getParameter(key, defaultValue);
	        if (value <= 0) {
	            return defaultValue;
	        }
	        return value;
	    }

	    public int getPositiveParameter(String key, int defaultValue) {
	        if (defaultValue <= 0) {
	            throw new IllegalArgumentException("defaultValue <= 0");
	        }
	        int value = getParameter(key, defaultValue);
	        if (value <= 0) {
	            return defaultValue;
	        }
	        return value;
	    }

	    public short getPositiveParameter(String key, short defaultValue) {
	        if (defaultValue <= 0) {
	            throw new IllegalArgumentException("defaultValue <= 0");
	        }
	        short value = getParameter(key, defaultValue);
	        if (value <= 0) {
	            return defaultValue;
	        }
	        return value;
	    }

	    public byte getPositiveParameter(String key, byte defaultValue) {
	        if (defaultValue <= 0) {
	            throw new IllegalArgumentException("defaultValue <= 0");
	        }
	        byte value = getParameter(key, defaultValue);
	        if (value <= 0) {
	            return defaultValue;
	        }
	        return value;
	    }

	    public char getParameter(String key, char defaultValue) {
	        String value = getParameter(key);
	        if (value == null || value.length() == 0) {
	            return defaultValue;
	        }
	        return value.charAt(0);
	    }

	    public boolean getParameter(String key, boolean defaultValue) {
	        String value = getParameter(key);
	        if (value == null || value.length() == 0) {
	            return defaultValue;
	        }
	        return Boolean.parseBoolean(value);
	    }

	    private Map<String, Number> getNumbers() {
	        if (numbers == null) { // 允许并发重复创建
	            numbers = new ConcurrentHashMap<String, Number>();
	        }
	        return numbers;
	    }
	    
	    public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		private volatile transient Map<String, Number> numbers;
	    public static void main(String[] args) throws UnsupportedEncodingException {
			String str="dubbo%3A%2F%2F192.168.1.104%3A20880%2Fcom.alibaba.dubbo.demo.DemoService%3Fanyhost%3Dtrue%26applica"
			            +"tion%3Ddemo-provider%26dubbo%3D2.5.4-SNAPSHOT%26generic%3Dfalse%26interface%3Dcom.alibaba.dubbo.demo"
			            +".DemoService%26loadbalance%3Droundrobin%26methods%3DsayHello%26owner%3Dwilliam%26pid%3D2812%26side%3"
			            +"Dprovider%26timestamp%3D1496670545035";
			System.out.println(URLDecoder.decode(str, "UTF-8"));
		}
}
