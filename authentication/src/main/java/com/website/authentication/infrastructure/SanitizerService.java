package com.website.authentication.infrastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SanitizerService {

    public static final Set<String> FIELDS = Set.of(
            "password",
            "token",
            "secret",
            "apiKey"
    );

    public static Map<String,Object> sanitize(Map<String,Object> inputMap){
        Map<String,Object> sanitized = new HashMap<>();
        for(String input : inputMap.keySet()){
            if(FIELDS.contains(input.toLowerCase()))
                sanitized.put(input,"******");
            else
                sanitized.put(input,inputMap.get(input));
        }
        return sanitized;
    }
}
