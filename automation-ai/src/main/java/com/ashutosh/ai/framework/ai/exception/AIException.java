package com.ashutosh.ai.framework.ai.exception;
import com.ashutosh.ai.framework.common.exceptions.FrameworkException;

public class AIException extends FrameworkException {
	private static final long serialVersionUID = 1L;
    /**
     * Constructs a AIException with a message.
     *
     * @param message exception message
     */
    public AIException(final String message) {
        super(message);
    }
    /**
     * Constructs a AIException with a cause.
     *
     * @param cause root cause
     */
    public AIException(final Throwable cause) {
        super(cause);
    }
    /**
     * Constructs a AIException with a message and cause.
     *
     * @param message exception message
     * @param cause root cause
     */
    public AIException(final String message,final Throwable cause) {
        super(message, cause);

    }
}
