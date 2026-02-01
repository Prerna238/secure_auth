package com.website.authentication.infrastructure;

import lombok.Getter;

import java.util.Map;

@Getter
public class LogEvent {

    private String message;
    private Map<String,Object> fields;

    public LogEvent(String message, Map<String,Object> fields){
        this.message=message;
        this.fields=fields;
    }

}
