package com.dubboclub.admin.sync.util;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bieber on 2015/6/3.
 */
public class Tool {
    
    
    public static Map<String,String> convertParametersMap(String params){
        String[] parts = params.split("\\&");
        Map<String,String> parameters = new HashMap<String, String>();
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
        return parameters;
    }

    //serviceKey=group/interface:version
    public static String getInterface(String serviceKey){
        if(StringUtils.isEmpty(serviceKey)){
            throw  new IllegalArgumentException("serviceKey must not be null");
        }
        int groupIndex = serviceKey.indexOf("/");
        int versionIndex = serviceKey.indexOf(":");
        if(groupIndex>0&&versionIndex>0){
            return serviceKey.substring(groupIndex+1,versionIndex);
        }else if(groupIndex>0&&versionIndex<0){
            return serviceKey.substring(groupIndex+1);
        }else if(groupIndex<0&&versionIndex>0){
            return serviceKey.substring(0,versionIndex);
        }else{
            return serviceKey;
        }
    }

    //serviceKey=group/interface:version
    public static String getGroup(String serviceKey){
        if(StringUtils.isEmpty(serviceKey)){
            throw  new IllegalArgumentException("serviceKey must not be null");
        }
        int groupIndex = serviceKey.indexOf("/");
        if(groupIndex>0){
            return serviceKey.substring(0,groupIndex);
        }else{
            return null;
        }
    }

    //serviceKey=group/interface:version
    public static String getVersion(String serviceKey){
        if(StringUtils.isEmpty(serviceKey)){
            throw  new IllegalArgumentException("serviceKey must not be null");
        }
        int versionIndex = serviceKey.indexOf(":");
        if(versionIndex>0){
            return serviceKey.substring(versionIndex+1);
        }else{
            return null;
        }
    }

    public static Map<String,String> serviceName2Map(String serviceKey){
        Map<String,String> params = new HashMap<String,String>();
        if(StringUtils.isEmpty(serviceKey)){
            return params;
        }
        int groupIndex = serviceKey.indexOf("/");
        int versionIndex = serviceKey.indexOf(":");
        if(groupIndex>0){
            params.put(Constants.GROUP_KEY,serviceKey.substring(0,groupIndex));
        }
        if(versionIndex>0){
            params.put(Constants.VERSION_KEY,serviceKey.substring(versionIndex+1));
        }
        params.put(Constants.INTERFACE_KEY,getInterface(serviceKey));
        return params;
    }
}
