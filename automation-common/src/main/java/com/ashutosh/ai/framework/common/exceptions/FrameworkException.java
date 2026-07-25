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

    /**
     * Constructs a FrameworkException with the specified detail message.
     *
     * @param message the detail message
     */
    protected FrameworkException(final String message) {
        super(message);
    }

    /**
     * Constructs a FrameworkException with the specified cause.
     *
     * @param cause the root cause
     */
    protected FrameworkException(final Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a FrameworkException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the root cause
     */
    protected FrameworkException(
            final String message,
            final Throwable cause) {

        super(message, cause);
    }
}