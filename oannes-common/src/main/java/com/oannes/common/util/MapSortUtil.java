package com.oannes.common.util;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public final class MapSortUtil {
	public static Map<String, Object> sortMapByKey(Map<String, Object> oriMap) {  
	    if (oriMap == null || oriMap.isEmpty()) {  
	        return null;  
	    }  
	    Map<String, Object> sortedMap = new TreeMap<String, Object>(new Comparator<String>() {  
	        public int compare(String key1, String key2) {  
	        	char[]charArray1=key1.toCharArray();
	        	char[]charArray2=key2.toCharArray();
	        	int length =Math.min(charArray1.length,charArray2.length);
	        	for(int i=0;i<length;i++){
	        		if(charArray1[i]==charArray2[i]){
	        		}else{
	        			return charArray1[i]-charArray2[i];
	        		}
	        	}
	        	return -1;
	        }});  
	    sortedMap.putAll(oriMap);  
	    return sortedMap;  
	}  
	  
  
}
