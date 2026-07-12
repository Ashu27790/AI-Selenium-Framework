package com.ashutosh.ai.framework.common.exceptions;

/**
 * Base exception for the AI Automation Framework.
 * All framework-specific exceptions should extend this class.
 */
public class FrameworkException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FrameworkException(String message) {
        super(message);
    }

    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }
}