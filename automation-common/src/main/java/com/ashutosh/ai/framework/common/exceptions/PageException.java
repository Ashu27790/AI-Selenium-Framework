package com.ashutosh.ai.framework.common.exceptions;

/**
 * Exception thrown when a Page Object encounters an error during
 * initialization, navigation, or page-specific operations.
 *
 * <p>
 * This exception is used to represent failures related to Page Objects
 * within the automation framework.
 * </p>
 *
 * @author Ashutosh Kumar Sahu
 * @version 1.0
 */
public final class PageException extends FrameworkException {

    private static final long serialVersionUID = 1L;
    public PageException() {
        super();
    }
    /**
     * Constructs a PageException with the specified message.
     *
     * @param message Exception message
     */
    public PageException(String message) {
        super(message);
    }

    /**
     * Constructs a PageException with the specified cause.
     *
     * @param cause Root cause
     */
    public PageException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a PageException with the specified message and cause.
     *
     * @param message Exception message
     * @param cause Root cause
     */
    public PageException(String message, Throwable cause) {
        super(message, cause);
    }
}