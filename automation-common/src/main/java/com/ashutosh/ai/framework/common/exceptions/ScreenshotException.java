package com.ashutosh.ai.framework.common.exceptions;

/**
 * Exception thrown when screenshot operations fail.
 */
public final class ScreenshotException extends FrameworkException {

    private static final long serialVersionUID = 1L;

    public ScreenshotException(final String message) {
        super(message);
    }

    public ScreenshotException(final Throwable cause) {
        super(cause);
    }

    public ScreenshotException(final String message, final Throwable cause) {
        super(message, cause);
    }
}