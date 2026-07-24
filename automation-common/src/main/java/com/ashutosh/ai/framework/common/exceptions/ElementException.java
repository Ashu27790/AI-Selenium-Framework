package com.ashutosh.ai.framework.common.exceptions;

/**
 * Exception thrown when an element interaction fails.
 *
 * <p>
 * This exception wraps Selenium element-related exceptions such as:
 * <ul>
 * <li>NoSuchElementException</li>
 * <li>ElementClickInterceptedException</li>
 * <li>ElementNotInteractableException</li>
 * <li>StaleElementReferenceException</li>
 * </ul>
 * </p>
 *
 * @author Ashutosh Kumar Sahu
 * @version 1.0
 */
public final class ElementException extends FrameworkException {

    private static final long serialVersionUID = 1L;
    public ElementException() {
        super();
    }
    /**
     * Constructs an ElementException with the specified message.
     *
     * @param message Exception message
     */
    public ElementException(String message) {
        super(message);
    }

    /**
     * Constructs an ElementException with the specified cause.
     *
     * @param cause Root cause
     */
    public ElementException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs an ElementException with the specified message and cause.
     *
     * @param message Exception message
     * @param cause Root cause
     */
    public ElementException(String message, Throwable cause) {
        super(message, cause);
    }
}