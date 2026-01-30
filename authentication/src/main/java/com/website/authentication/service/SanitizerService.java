package com.website.authentication.service;

import com.website.authentication.model.SensitiveFields;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SanitizerService {

    public static Map<String,Object> sanitize(Map<String,Object> inputMap){
        Map<String,Object> sanitized = new HashMap<>();
        for(String input : inputMap.keySet()){
            if(SensitiveFields.FIELDS.contains(input.toLowerCase()))
                sanitized.put(input,"******");
            else
                sanitized.put(input,inputMap.get(input));
        }
        return sanitized;
    }
}
