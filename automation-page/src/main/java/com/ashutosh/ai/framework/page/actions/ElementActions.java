package com.ashutosh.ai.framework.page.actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ashutosh.ai.framework.driver.manager.DriverManager;
import com.ashutosh.ai.framework.page.waits.WaitUtils;

/**
 * Provides reusable element interaction methods for Selenium Page Objects.
 *
 * <p>
 * All interactions are synchronized using WaitUtils to improve test stability.
 * </p>
 *
 * @author Ashutosh Kumar Sahu
 * @version 1.0
 */
public class ElementActions {

    private static final Logger LOGGER = LogManager.getLogger(ElementActions.class);

    private final WebDriver driver;
    private final WaitUtils waitUtils;

    /**
     * Initializes ElementActions.
     */
    public ElementActions() {
        this.driver = DriverManager.getDriver();
        this.waitUtils = new WaitUtils();
    }

    /**
     * Clicks an element.
     *
     * @param locator element locator
     */
    public void click(By locator) {

        LOGGER.info("Clicking element : {}", locator);
        waitUtils.waitForClickable(locator).click();
    }

    /**
     * Types text into an input field.
     *
     * @param locator element locator
     * @param text text to enter
     */
    public void type(By locator, String text) {

        LOGGER.info("Entering text into element : {}", locator);
        WebElement element = waitUtils.waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Clears an input field.
     *
     * @param locator element locator
     */
    public void clear(By locator) {

        LOGGER.info("Clearing element : {}", locator);
        waitUtils.waitForVisibility(locator).clear();
    }

    /**
     * Returns text of an element.
     *
     * @param locator element locator
     * @return element text
     */
    public String getText(By locator) {

        LOGGER.info("Getting text from element : {}", locator);
        return waitUtils.waitForVisibility(locator).getText();
    }

    /**
     * Returns attribute value.
     *
     * @param locator element locator
     * @param attribute attribute name
     * @return attribute value
     */
    public String getAttribute(By locator, String attribute) {

        LOGGER.info("Getting attribute '{}' from {}", attribute, locator);
        return waitUtils.waitForVisibility(locator).getAttribute(attribute);
    }

    /**
     * Checks whether an element is displayed.
     *
     * @param locator element locator
     * @return true if displayed
     */
    public boolean isDisplayed(By locator) {

        LOGGER.info("Checking visibility of element : {}", locator);
        return waitUtils.waitForVisibility(locator).isDisplayed();
    }

    /**
     * Checks whether an element is enabled.
     *
     * @param locator element locator
     * @return true if enabled
     */
    public boolean isEnabled(By locator) {

        LOGGER.info("Checking enabled state of element : {}", locator);
        return waitUtils.waitForVisibility(locator).isEnabled();
    }

    /**
     * Checks whether a checkbox or radio button is selected.
     *
     * @param locator element locator
     * @return true if selected
     */
    public boolean isSelected(By locator) {

        LOGGER.info("Checking selected state of element : {}", locator);
        return waitUtils.waitForVisibility(locator).isSelected();
    }

    /**
     * Submits a form.
     *
     * @param locator form element locator
     */
    public void submit(By locator) {

        LOGGER.info("Submitting form : {}", locator);
        waitUtils.waitForVisibility(locator).submit();
    }

    /**
     * Returns the WebElement after waiting for visibility.
     *
     * @param locator element locator
     * @return WebElement
     */
    public WebElement getElement(By locator) {

        return waitUtils.waitForVisibility(locator);
    }
}