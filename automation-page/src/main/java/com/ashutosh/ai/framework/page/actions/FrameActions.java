package com.ashutosh.ai.framework.page.actions;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ashutosh.ai.framework.common.exceptions.FrameException;
import com.ashutosh.ai.framework.page.waits.WaitUtils;

/**
 * Provides reusable utility methods for interacting with frames and iframes.
 *
 * <p>
 * Supports switching between frames using index, name/id, locator,
 * WebElement and returning to parent or default content.
 * </p>
 */
public class FrameActions {
    private static final Logger LOGGER =LogManager.getLogger(FrameActions.class);
    private final WebDriver driver;
    private final WaitUtils waitUtils;
    /**
     * Constructs FrameActions.
     *
     * @param driver WebDriver instance
     * @param waitUtils Wait utility
     */
    public FrameActions(
            final WebDriver driver,
            final WaitUtils waitUtils) {
        this.driver = Objects.requireNonNull(driver,"WebDriver cannot be null.");
        this.waitUtils = Objects.requireNonNull(waitUtils,"WaitUtils cannot be null.");
    }

    /**
     * Switches to frame using index.
     *
     * @param index frame index
     */
    public void switchToFrame(final int index) {
        try {
            driver.switchTo().frame(index);
            LOGGER.info("Switched to frame using index [{}].",index);
        } catch (Exception exception) {
            LOGGER.error("Unable to switch to frame using index [{}].",index,exception);
            throw new FrameException("Unable to switch to frame using index: " + index,exception);
        }
    }

    /**
     * Switches to frame using name or id.
     *
     * @param nameOrId frame name or id
     */
    public void switchToFrame(final String nameOrId) {
        Objects.requireNonNull( nameOrId,"Frame name/id cannot be null.");
        try {
            driver.switchTo().frame(nameOrId);
            LOGGER.info("Switched to frame [{}].",nameOrId);
        } catch (Exception exception) {
            LOGGER.error("Unable to switch to frame [{}].",nameOrId,exception);
            throw new FrameException("Unable to switch to frame: " + nameOrId,exception);
        }
    }

    /**
     * Switches to frame using WebElement.
     *
     * @param frameElement frame element
     */
    public void switchToFrame(final WebElement frameElement) {
        Objects.requireNonNull(frameElement,"Frame element cannot be null.");
        try {
            driver.switchTo().frame(frameElement);
            LOGGER.info("Switched to frame using WebElement.");
        } catch (Exception exception) {
            LOGGER.error("Unable to switch to frame using WebElement.", exception);
            throw new FrameException("Unable to switch to frame using WebElement.",exception);
        }
    }
    /**
     * Switches to frame using locator.
     *
     * @param locator frame locator
     */
    public void switchToFrame(final By locator) {
        Objects.requireNonNull(locator,"Frame locator cannot be null.");
        try {
            final WebElement frame = waitUtils.waitForElementVisible(locator);
            driver.switchTo().frame(frame);
            LOGGER.info("Switched to frame located by [{}].", locator);
        } catch (Exception exception) {
            LOGGER.error( "Unable to switch to frame located by [{}].",locator, exception);
            throw new FrameException( "Unable to switch to frame located by: " + locator, exception);
        }
    }
    /**
     * Switches to parent frame.
     */
    public void switchToParentFrame() {
        try {
            driver.switchTo().parentFrame();LOGGER.info("Switched to parent frame.");
        } catch (Exception exception) {
            LOGGER.error("Unable to switch to parent frame.",exception);
            throw new FrameException("Unable to switch to parent frame.",exception);
        }
    }
    /**
     * Switches to default content.
     */
    public void switchToDefaultContent() {
        try {
            driver.switchTo().defaultContent();
            LOGGER.info("Switched to default content.");
        } catch (Exception exception) {
            LOGGER.error("Unable to switch to default content.", exception);
            throw new FrameException( "Unable to switch to default content.", exception);
        }
    }
    /**
     * Checks whether a frame is available.
     *
     * @param locator frame locator
     * @return true if frame is available; otherwise false
     */
    public boolean isFrameAvailable(final By locator) {
        Objects.requireNonNull(locator,"Frame locator cannot be null.");
        try {
            waitUtils.waitForElementVisible(locator);
            LOGGER.debug("Frame is available: {}",locator);
            return true;
        } catch (Exception exception) {
            LOGGER.debug( "Frame is not available: {}", locator);
            return false;
        }
    }
    /**
     * Checks whether a frame element is available.
     *
     * @param frameElement frame element
     * @return true if available
     */
    public boolean isFrameAvailable(
            final WebElement frameElement) {
        return frameElement != null
                && frameElement.isDisplayed();
    }

    

}
