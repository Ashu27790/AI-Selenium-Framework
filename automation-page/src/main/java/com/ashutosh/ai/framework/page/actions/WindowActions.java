package com.ashutosh.ai.framework.page.actions;

import java.util.Objects;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;

import com.ashutosh.ai.framework.common.exceptions.WindowException;
import com.ashutosh.ai.framework.page.waits.WaitUtils;

/**
 * Provides utility methods for browser window and tab operations.
 *
 * <p>
 * Supports switching between windows, closing windows,
 * retrieving window handles and locating windows by title or URL.
 * </p>
 */
public class WindowActions {

    private static final Logger LOGGER =LogManager.getLogger(WindowActions.class);
    private final WebDriver driver;
    private final WaitUtils waitUtils;

    /**
     * Constructs WindowActions.
     *
     * @param driver WebDriver instance
     * @param waitUtils Wait utility instance
     */
    public WindowActions(
            final WebDriver driver,
            final WaitUtils waitUtils) {
        this.driver = Objects.requireNonNull( driver,"WebDriver cannot be null.");
        this.waitUtils = Objects.requireNonNull(waitUtils,"WaitUtils cannot be null.");
    }

    /**
     * Returns the current window handle.
     *
     * @return current window handle
     */
    public String getCurrentWindowHandle() {

        try {
            final String handle = driver.getWindowHandle();
            LOGGER.info("Retrieved current window handle.");
            return handle;
        } catch (NoSuchWindowException exception) {
            LOGGER.error("Unable to retrieve current window handle.",exception);
            throw new WindowException("Unable to retrieve current window handle.",exception);
        }
    }
    /**
     * Returns all available window handles.
     *
     * @return set of window handles
     */
    public Set<String> getWindowHandles() {
        try {
            final Set<String> handles =driver.getWindowHandles();
            LOGGER.info("Retrieved all window handles.");
            return handles;
        } catch (NoSuchWindowException exception) {
            LOGGER.error( "Unable to retrieve window handles.",exception);
            throw new WindowException("Unable to retrieve window handles.",exception);
        }
    }

    /**
     * Switches to a window using its title.
     *
     * @param title window title
     */
    public void switchToWindow(final String title) {
        Objects.requireNonNull(title, "Window title cannot be null.");
        try {
        	waitUtils.waitForNewWindow();
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
                if (driver.getTitle().equals(title)) {
                    LOGGER.info( "Switched to window [{}].", title);
                    return;
                }
            }
            throw new WindowException("Window not found with title: " + title);
        } catch (WindowException exception) {
            throw exception;
        } catch (NoSuchWindowException exception) {
            LOGGER.error("Unable to switch window [{}].",title,exception);
            throw new WindowException(
                    "Unable to switch to window: " + title,
                    exception);
        }
    }
    /**
     * Switches to a window using URL.
     *
     * @param url window URL
     */
    public void switchToWindowByUrl(final String url) {
        Objects.requireNonNull(url,"Window URL cannot be null.");
        try {
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
                if (driver.getCurrentUrl().contains(url)) {
                    LOGGER.info( "Switched to window URL [{}].",url);
                    return;
                }
            }
            throw new WindowException("Window not found with URL: " + url);
        } catch (WindowException exception) {
            throw exception;
        } catch (NoSuchWindowException exception) {
            LOGGER.error("Unable to switch window URL [{}].",url,exception);
            throw new WindowException("Unable to switch to window URL: " + url,exception);
        }
    }
    /**
     * Switches to the newly opened window.
     *
     * @throws WindowException if a new window cannot be found
     */
    public void switchToNewWindow(final int expectedWindowCount) {
        try {
        	 waitUtils.waitForWindowCount(expectedWindowCount);
            final String currentWindow = driver.getWindowHandle();
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(currentWindow)) {
                    driver.switchTo().window(handle);
                    LOGGER.info("Switched to new window.");
                    return;
                }
            }
            throw new WindowException("New window was not found.");
        } catch (WindowException exception) {
            throw exception;
        } catch (NoSuchWindowException exception) {
            LOGGER.error("Unable to switch to new window.",exception);
            throw new WindowException("Unable to switch to new window.", exception);
        }
    }

    /**
     * Closes the current browser window.
     *
     * @throws WindowException if the window cannot be closed
     */
    public void closeCurrentWindow() {
        try {
            driver.close();
            LOGGER.info("Current window closed successfully.");
        } catch (NoSuchWindowException exception) {
            LOGGER.error( "Unable to close current window.",exception);
            throw new WindowException("Unable to close current window.",exception);
        }
    }

    /**
     * Closes all child windows while keeping the parent window open.
     *
     * @throws WindowException if closing child windows fails
     */
    public void closeAllChildWindows() {
        try {
            final String parentWindow =driver.getWindowHandle();
            for (String handle : driver.getWindowHandles()) {
                if (!parentWindow.equals(handle)) {
                    driver.switchTo().window(handle);
                    driver.close();
                    LOGGER.info("Closed child window [{}].",handle);
                }
            }
            driver.switchTo().window(parentWindow);
            LOGGER.info("Switched back to parent window.");
        } catch (NoSuchWindowException exception) {
            LOGGER.error("Unable to close child windows.",exception);
            throw new WindowException("Unable to close child windows.",exception);
        }
    }

    /**
     * Checks whether a browser window having the specified title exists.
     *
     * @param title window title
     *
     * @return true if found, otherwise false
     */
    public boolean isWindowPresent(final String title) {
        Objects.requireNonNull(title,"Window title cannot be null.");
        try {
            final String currentHandle =driver.getWindowHandle();
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
                if (driver.getTitle().equals(title)) {
                    driver.switchTo().window(currentHandle);
                    return true;
                }
            }
            driver.switchTo().window(currentHandle);
            return false;
        } catch (NoSuchWindowException exception) {
            LOGGER.error("Unable to verify window presence [{}].",title,exception);
            throw new WindowException( "Unable to verify window presence: " + title, exception);
        }
    }

}