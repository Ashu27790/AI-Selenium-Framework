package com.ashutosh.ai.framework.common.exceptions;

/**
 * Exception thrown when there is an issue related to framework
 * configuration, such as:
 * <ul>
 * <li>Missing configuration file</li>
 * <li>Missing property</li>
 * <li>Invalid property value</li>
 * <li>Unable to load configuration</li>
 * </ul>
 *
 * @author Ashutosh Kumar Sahu
 * @version 1.0
 */
public final class ConfigurationException extends FrameworkException {

    private static final long serialVersionUID = 1L;
    public ConfigurationException() {
        super();
    }
    /**
     * Constructs a ConfigurationException with the specified message.
     *
     * @param message Exception message
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a ConfigurationException with the specified cause.
     *
     * @param cause Root cause
     */
    public ConfigurationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a ConfigurationException with the specified message and cause.
     *
     * @param message Exception message
     * @param cause Root cause
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}