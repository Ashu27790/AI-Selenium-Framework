package com.ashutosh.ai.framework.common.exceptions;

/**
 * Exception thrown when a browser window or tab operation fails.
 *
 * <p>
 * This exception is used for failures occurring while switching,
 * closing, or interacting with browser windows or tabs.
 * </p>
 *
 * @author Ashutosh
 */
public final class WindowException extends FrameworkException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new WindowException with the specified message.
     *
     * @param message exception message
     */
    public WindowException(final String message) {
        super(message);
    }

    /**
     * Constructs a new WindowException with the specified message and cause.
     *
     * @param message exception message
     * @param cause root cause
     */
    public WindowException(
            final String message,
            final Throwable cause) {

        super(message, cause);
    }

}