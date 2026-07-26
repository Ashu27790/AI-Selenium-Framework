package com.ashutosh.ai.framework.config.exceptions;


/**
 * Exception thrown when configuration loading or
 * configuration validation fails.
 */
public class ConfigurationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
    /**
     * Constructs a ConfigurationException with a message.
     *
     * @param message exception message
     */
    public ConfigurationException(final String message) {
        super(message);
    }
    /**
     * Constructs a ConfigurationException with a cause.
     *
     * @param cause root cause
     */
    public ConfigurationException(final Throwable cause) {
        super(cause);
    }
    /**
     * Constructs a ConfigurationException with a message and cause.
     *
     * @param message exception message
     * @param cause root cause
     */
    public ConfigurationException(final String message,final Throwable cause) {
        super(message, cause);

    }
}