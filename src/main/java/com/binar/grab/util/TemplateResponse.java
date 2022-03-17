package com.binar.grab.util;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component // IOC - Beans
public class TemplateResponse {

    public Map templateSukses(Object objek){
        Map map = new HashMap();
        map.put("data", objek);
        map.put("message", "sukses");
        map.put("status", "200");
        return map;
    }

    public Map templateEror(Object objek){
        Map map = new HashMap();
        map.put("message", objek);
        map.put("status", "404");
        return map;
    }
    public Map notFound(Object objek){
        Map map = new HashMap();
        map.put("message", objek);
        map.put("status", "404");
        return map;
    }


    public boolean chekNull(Object obj){
        if(obj == null){
            return true;
        }
        return  false;
    }
}
