package com.ashutosh.ai.framework.common.exceptions;

/**
 * Exception thrown when an explicit wait operation fails or
 * a timeout occurs while waiting for an expected condition.
 *
 * <p>
 * This exception is typically used within WaitUtils to wrap Selenium
 * TimeoutException and provide meaningful framework-level error messages.
 * </p>
 *
 * @author Ashutosh Kumar Sahu
 * @version 1.0
 */
public final class WaitException extends FrameworkException {

    private static final long serialVersionUID = 1L;
    public WaitException() {
        super();
    }
    /**
     * Constructs a WaitException with the specified message.
     *
     * @param message Exception message
     */
    public WaitException(String message) {
        super(message);
    }

    /**
     * Constructs a WaitException with the specified cause.
     *
     * @param cause Root cause
     */
    public WaitException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a WaitException with the specified message and cause.
     *
     * @param message Exception message
     * @param cause Root cause
     */
    public WaitException(String message, Throwable cause) {
        super(message, cause);
    }
}