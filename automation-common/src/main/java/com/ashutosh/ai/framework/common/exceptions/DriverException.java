package com.ashutosh.ai.framework.common.exceptions;

/**
 * Exception thrown when WebDriver initialization, browser creation,
 * or browser operations fail.
 *
 * This exception is specific to driver-related failures within
 * the AI Selenium Automation Framework.
 *
 * @author Ashutosh Kumar Sahu
 * @version 1.0
 */
public final class DriverException extends FrameworkException {

    private static final long serialVersionUID = 1L;
    public DriverException() {
        super();
    }
    /**
     * Constructs a DriverException with the specified message.
     * @param message Exception message
     */
    public DriverException(String message) {
        super(message);
    }

    /**
     * Constructs a DriverException with the specified cause.
     * @param cause Root cause
     */
    public DriverException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a DriverException with the specified message and cause.
     * @param message Exception message
     * @param cause Root cause
     */
    public DriverException(String message, Throwable cause) {
        super(message, cause);
    }
}