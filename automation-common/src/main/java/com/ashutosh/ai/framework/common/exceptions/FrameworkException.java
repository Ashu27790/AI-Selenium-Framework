package com.ashutosh.ai.framework.common.exceptions;

/**
 * Base exception for the AI Selenium Automation Framework.
 *
 * All framework-specific exceptions should extend this class.
 *
 * @author Ashutosh Kumar Sahu
 * @version 1.0
 */
public abstract class FrameworkException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public FrameworkException() {
        super();
    }
    /**
     * Constructs a FrameworkException with the specified message.
     *
     * @param message Exception message
     */
    public FrameworkException(String message) {
        super(message);
    }

    /**
     * Constructs a FrameworkException with the specified cause.
     *
     * @param cause Root cause
     */
    public FrameworkException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a FrameworkException with the specified message and cause.
     *
     * @param message Exception message
     * @param cause Root cause
     */
    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }
}