package com.ashutosh.ai.framework.config.exceptions;


/**
 * Exception thrown when configuration loading or
 * configuration validation fails.
 */
public class ConfigurationException extends RuntimeException {

    public ConfigurationException(String message) {
        super(message);
    }

}