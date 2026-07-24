package com.ashutosh.ai.framework.page.actions;

import java.util.Objects;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.ashutosh.ai.framework.common.exceptions.ElementException;
import com.ashutosh.ai.framework.page.waits.WaitUtils;

/**
 * Provides reusable mouse interactions using Selenium Actions API.
 *
 * <p>
 * This class encapsulates all mouse-related operations such as hovering,
 * clicking, double-clicking, right-clicking, drag-and-drop, and composite
 * mouse interactions.
 * </p>
 *
 * <p>
 * All Selenium exceptions are wrapped into {@link ElementException} to provide
 * framework-specific exception handling.
 * </p>
 *
 * @author Ashutosh Kumar Sahu
 * @version 1.0
 */
public final class MouseActions {

    private static final Logger LOGGER = LogManager.getLogger(MouseActions.class);

    private final WebDriver driver;
    private final WaitUtils waitUtils;

    /**
     * Constructs a MouseActions instance.
     *
     * @param driver Active WebDriver instance
     * @param waitUtils Framework WaitUtils instance
     */
    public MouseActions(final WebDriver driver, final WaitUtils waitUtils) {
        this.driver = Objects.requireNonNull(driver, "WebDriver cannot be null.");
        this.waitUtils = Objects.requireNonNull(waitUtils, "WaitUtils cannot be null.");
    }

    /**
     * Returns a visible WebElement using the framework wait utility.
     *
     * @param locator Element locator
     * @return Visible WebElement
     */
    private WebElement getVisibleElement(final By locator) {
        Objects.requireNonNull(locator, "Locator cannot be null.");
        return waitUtils.waitForVisibility(locator);
    }

    /**
     * Validates that the supplied WebElement is not null.
     *
     * @param element WebElement
     * @return Valid WebElement
     */
    private WebElement validateElement(final WebElement element) {
        return Objects.requireNonNull(element, "WebElement cannot be null.");
    }

    /**
     * Centralized execution logic for mouse interactions.
     * Creates a fresh, stateless Actions instance for each interaction,
     * builds and performs the action chain, and encapsulates error logging
     * and custom exception wrapping.
     *
     * @param actionSetup  Lambda expression setting up the actions chain
     * @param errorMessage Custom error message for exceptions
     * @throws ElementException if execution fails
     */
    private void executeAction(final Consumer<Actions> actionSetup, final String errorMessage) {
        try {
            Actions freshActions = new Actions(this.driver);
            actionSetup.accept(freshActions);
            freshActions.build().perform();
        } catch (Exception exception) {
            LOGGER.error(errorMessage, exception);
            throw new ElementException(errorMessage, exception);
        }
    }

    /**
     * Moves the mouse pointer to the specified WebElement.
     *
     * @param element WebElement to hover over
     */
    public void moveToElement(final WebElement element) {
        LOGGER.info("Moving mouse to WebElement.");
        executeAction(
                actions -> actions.moveToElement(validateElement(element)),
                "Unable to move mouse to WebElement."
        );
    }

    /**
     * Moves the mouse pointer to the element identified by the locator.
     *
     * @param locator Element locator
     */
    public void moveToElement(final By locator) {
        LOGGER.info("Moving mouse to element located by: {}", locator);
        executeAction(
                actions -> actions.moveToElement(getVisibleElement(locator)),
                "Unable to move mouse to element: " + locator
        );
    }

    /**
     * Performs a mouse click on the specified WebElement.
     *
     * @param element WebElement to click
     */
    public void click(final WebElement element) {
        LOGGER.info("Clicking WebElement using Actions.");
        executeAction(
                actions -> actions.click(validateElement(element)),
                "Unable to click WebElement."
        );
    }

    /**
     * Performs a mouse click on the element identified by the locator.
     *
     * @param locator Element locator
     */
    public void click(final By locator) {
        LOGGER.info("Clicking element located by: {}", locator);
        executeAction(
                actions -> actions.click(getVisibleElement(locator)),
                "Unable to click element: " + locator
        );
    }

    /**
     * Performs a double-click on the specified WebElement.
     *
     * @param element WebElement to double-click
     */
    public void doubleClick(final WebElement element) {
        LOGGER.info("Double-clicking WebElement.");
        executeAction(
                actions -> actions.doubleClick(validateElement(element)),
                "Unable to double-click WebElement."
        );
    }

    /**
     * Performs a double-click on the element identified by the locator.
     *
     * @param locator Element locator
     */
    public void doubleClick(final By locator) {
        LOGGER.info("Double-clicking element located by: {}", locator);
        executeAction(
                actions -> actions.doubleClick(getVisibleElement(locator)),
                "Unable to double-click element: " + locator
        );
    }

    /**
     * Performs a right-click on the specified WebElement.
     *
     * @param element WebElement to right-click
     */
    public void rightClick(final WebElement element) {
        LOGGER.info("Right-clicking WebElement.");
        executeAction(
                actions -> actions.contextClick(validateElement(element)),
                "Unable to right-click WebElement."
        );
    }

    /**
     * Performs a right-click on the element identified by the locator.
     *
     * @param locator Element locator
     */
    public void rightClick(final By locator) {
        LOGGER.info("Right-clicking element located by: {}", locator);
        executeAction(
                actions -> actions.contextClick(getVisibleElement(locator)),
                "Unable to right-click element: " + locator
        );
    }

    /**
     * Clicks and holds the specified WebElement.
     *
     * @param element WebElement to click and hold
     */
    public void clickAndHold(final WebElement element) {
        LOGGER.info("Clicking and holding WebElement.");
        executeAction(
                actions -> actions.clickAndHold(validateElement(element)),
                "Unable to click and hold WebElement."
        );
    }

    /**
     * Clicks and holds the element identified by the locator.
     *
     * @param locator Element locator
     */
    public void clickAndHold(final By locator) {
        LOGGER.info("Clicking and holding element located by: {}", locator);
        executeAction(
                actions -> actions.clickAndHold(getVisibleElement(locator)),
                "Unable to click and hold element: " + locator
        );
    }

    /**
     * Releases the mouse button from the specified WebElement.
     *
     * @param element WebElement
     */
    public void release(final WebElement element) {
        LOGGER.info("Releasing mouse on WebElement.");
        executeAction(
                actions -> actions.release(validateElement(element)),
                "Unable to release mouse on WebElement."
        );
    }

    /**
     * Releases the mouse button from the element identified by the locator.
     *
     * @param locator Element locator
     */
    public void release(final By locator) {
        LOGGER.info("Releasing mouse on element located by: {}", locator);
        executeAction(
                actions -> actions.release(getVisibleElement(locator)),
                "Unable to release mouse on element: " + locator
        );
    }

    /**
     * Performs drag and drop from source element to target element.
     *
     * @param source Source WebElement
     * @param target Target WebElement
     */
    public void dragAndDrop(final WebElement source, final WebElement target) {
        LOGGER.info("Performing drag and drop from source to target WebElements.");
        executeAction(
                actions -> actions.dragAndDrop(validateElement(source), validateElement(target)),
                "Unable to perform drag and drop."
        );
    }

    /**
     * Performs drag and drop using locators.
     *
     * @param source Source locator
     * @param target Target locator
     */
    public void dragAndDrop(final By source, final By target) {
        LOGGER.info("Performing drag and drop from {} to {}.", source, target);
        executeAction(
                actions -> actions.dragAndDrop(getVisibleElement(source), getVisibleElement(target)),
                "Unable to perform drag and drop."
        );
    }

    /**
     * Drags the specified WebElement by the given offsets.
     *
     * @param source  Source WebElement
     * @param xOffset Horizontal offset
     * @param yOffset Vertical offset
     */
    public void dragAndDropBy(final WebElement source, final int xOffset, final int yOffset) {
        LOGGER.info("Dragging WebElement by x={}, y={}.", xOffset, yOffset);
        executeAction(
                actions -> actions.dragAndDropBy(validateElement(source), xOffset, yOffset),
                "Unable to drag WebElement."
        );
    }

    /**
     * Drags the located element by the given offsets.
     *
     * @param source  Source locator
     * @param xOffset Horizontal offset
     * @param yOffset Vertical offset
     */
    public void dragAndDropBy(final By source, final int xOffset, final int yOffset) {
        LOGGER.info("Dragging element {} by x={}, y={}.", source, xOffset, yOffset);
        executeAction(
                actions -> actions.dragAndDropBy(getVisibleElement(source), xOffset, yOffset),
                "Unable to drag element."
        );
    }

    /**
     * Moves the mouse pointer by the specified offsets.
     *
     * @param xOffset Horizontal offset
     * @param yOffset Vertical offset
     */
    public void moveByOffset(final int xOffset, final int yOffset) {
        LOGGER.info("Moving mouse by x={}, y={}.", xOffset, yOffset);
        executeAction(
                actions -> actions.moveByOffset(xOffset, yOffset),
                "Unable to move mouse by offset."
        );
    }

    /**
     * Hovers over one WebElement and clicks another.
     *
     * @param hoverElement element to hover
     * @param clickElement element to click
     */
    public void hoverAndClick(final WebElement hoverElement, final WebElement clickElement) {
        LOGGER.info("Hovering over WebElement and clicking another WebElement.");
        executeAction(
                actions -> actions.moveToElement(validateElement(hoverElement)).click(validateElement(clickElement)),
                "Unable to hover and click WebElement."
        );
    }

    /**
     * Hovers over one located element and clicks another.
     *
     * @param hoverLocator locator to hover
     * @param clickLocator locator to click
     */
    public void hoverAndClick(final By hoverLocator, final By clickLocator) {
        LOGGER.info("Hovering over {} and clicking {}.", hoverLocator, clickLocator);
        executeAction(
                actions -> actions.moveToElement(getVisibleElement(hoverLocator)).click(getVisibleElement(clickLocator)),
                "Unable to hover and click located element."
        );
    }

    /**
     * Moves the mouse over the specified WebElement.
     *
     * @param element WebElement
     */
    public void hover(final WebElement element) {
        LOGGER.info("Hovering over WebElement.");
        executeAction(
                actions -> actions.moveToElement(validateElement(element)),
                "Unable to hover over WebElement."
        );
    }

    /**
     * Moves the mouse over the located element.
     *
     * @param locator element locator
     */
    public void hover(final By locator) {
        LOGGER.info("Hovering over element located by {}.", locator);
        executeAction(
                actions -> actions.moveToElement(getVisibleElement(locator)),
                "Unable to hover over element."
        );
    }

    /**
     * Moves the mouse to the specified WebElement and clicks it.
     *
     * @param element WebElement
     */
    public void moveToElementAndClick(final WebElement element) {
        LOGGER.info("Moving to WebElement and clicking.");
        executeAction(
                actions -> actions.moveToElement(validateElement(element)).click(),
                "Unable to move to WebElement and click."
        );
    }

    /**
     * Moves the mouse to the located element and clicks it.
     *
     * @param locator element locator
     */
    public void moveToElementAndClick(final By locator) {
        LOGGER.info("Moving to element {} and clicking.", locator);
        executeAction(
                actions -> actions.moveToElement(getVisibleElement(locator)).click(),
                "Unable to move to element and click."
        );
    }
}
