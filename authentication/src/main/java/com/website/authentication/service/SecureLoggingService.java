package com.website.authentication.service;

import com.website.authentication.configuration.LogEvent;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import java.util.Map;


public class SecureLoggingService {

    public static final Logger logger = LoggerFactory.getLogger(SecureLoggingService.class);

    public static void info (LogEvent log){
        Map<String,Object> sanitized = SanitizerService.sanitize(log.getFields());
        logger.info(log.getMessage() + " | " + sanitized);
    }

    public static void error(LogEvent error){
        Map<String,Object> sanitized = SanitizerService.sanitize(error.getFields());
        logger.error("{} | {}", error.getMessage(),sanitized);
    }
}
