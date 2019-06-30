package com.oannes.common;

import com.jwcjlu.oannes.common.services.BootService;
import com.oannes.common.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ProtocolFactory  implements BootService {
    private Map<String,Protocol> protocoleMap=new HashMap<String,Protocol>();
    public  Protocol getProtocol(String name){
        if(StringUtils.isNotEmpty(name)){
            return protocoleMap.get(name);
        }else{
            return protocoleMap.get("default");
        }

    }
    public  void registerProtocol(String name,Protocol protocol){
        protocoleMap.put(name, protocol);
    }
}
