package com.ashutosh.ai.framework.driver.exceptions;

/**
 * Exception thrown when driver creation,
 * initialization or termination fails.
 *
 * Responsibilities:
 * - Driver initialization errors
 * - Driver shutdown errors
 * - Unsupported browser errors
 *
 * @author Ashutosh Kumar Sahu
 * @since 1.0
 */
public class DriverException extends RuntimeException {

    public DriverException(String message) {
        super(message);
    }

    public DriverException(String message, Throwable cause) {
        super(message, cause);
    }

}