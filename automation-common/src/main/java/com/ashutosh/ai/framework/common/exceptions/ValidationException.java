package com.ashutosh.ai.framework.common.exceptions;

/**
 * Exception thrown when validation of application data,
 * business rules, or test assertions fails.
 *
 * <p>
 * This exception is intended for custom validation logic within
 * the automation framework.
 * </p>
 *
 * @author Ashutosh Kumar Sahu
 * @version 1.0
 */
public final class ValidationException extends FrameworkException {

    private static final long serialVersionUID = 1L;
    public ValidationException() {
        super();
    }
    /**
     * Constructs a ValidationException with the specified message.
     *
     * @param message Exception message
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Constructs a ValidationException with the specified cause.
     *
     * @param cause Root cause
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a ValidationException with the specified message and cause.
     *
     * @param message Exception message
     * @param cause Root cause
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}