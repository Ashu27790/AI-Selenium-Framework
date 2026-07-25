package com.ashutosh.ai.framework.common.exceptions;

/**
 * Exception thrown when a frame-related operation fails.
 *
 * <p>
 * This exception is used for failures occurring while switching
 * between frames or interacting with iframes.
 * </p>
 *
 * @author Ashutosh
 */
public final class FrameException extends FrameworkException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new FrameException with the specified message.
     *
     * @param message exception message
     */
    public FrameException(final String message) {
        super(message);
    }

    /**
     * Constructs a new FrameException with the specified message and cause.
     *
     * @param message exception message
     * @param cause root cause
     */
    public FrameException(
            final String message,
            final Throwable cause) {

        super(message, cause);
    }

}