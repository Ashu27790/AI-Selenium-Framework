package com.ashutosh.ai.framework.common.exceptions;

/**
 * Exception thrown when alert operations fail.
 */
public class AlertException extends FrameworkException {

    private static final long serialVersionUID = 1L;

    public AlertException(final String message) {
        super(message);
    }

    public AlertException(final String message, final Throwable cause) {
        super(message, cause);
    }
}