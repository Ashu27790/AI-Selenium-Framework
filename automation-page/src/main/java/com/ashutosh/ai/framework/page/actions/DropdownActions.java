package com.ashutosh.ai.framework.page.actions;

import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

import com.ashutosh.ai.framework.common.exceptions.ElementException;
import com.ashutosh.ai.framework.page.waits.WaitUtils;

/**
 * Provides reusable utility methods for interacting with HTML
 * dropdown elements using Selenium Select.
 *
 * <p>
 * Supports selection, deselection and retrieval of dropdown
 * options with centralized exception handling.
 * </p>
 */
public class DropdownActions {

    private static final Logger LOGGER =LogManager.getLogger(DropdownActions.class);
    private final WebDriver driver;
    private final WaitUtils waitUtils;
    /**
     * Constructs DropdownActions.
     *
     * @param driver WebDriver instance
     * @param waitUtils Wait utility
     */
    public DropdownActions(final WebDriver driver,final WaitUtils waitUtils) {
        this.driver = Objects.requireNonNull(driver,"WebDriver cannot be null.");
        this.waitUtils = Objects.requireNonNull( waitUtils,"WaitUtils cannot be null.");
    }

    /**
     * Returns Selenium Select object.
     *
     * @param element dropdown element
     *
     * @return Select object
     */
    private Select getSelect(final WebElement element) {
        Objects.requireNonNull(element,"Dropdown element cannot be null.");
        try {
            return new Select(element);
        } catch (UnexpectedTagNameException exception) {
            throw new ElementException("The provided element is not a <select> element.",exception);
        }
    }

    /**
     * Returns Selenium Select object.
     *
     * @param locator dropdown locator
     *
     * @return Select object
     */
    private Select getSelect(final By locator) {
        Objects.requireNonNull(locator,"Locator cannot be null.");
        return getSelect( waitUtils.waitForElementVisible(locator));
    }

    /**
     * Selects dropdown option using visible text.
     *
     * @param element dropdown element
     * @param visibleText visible text
     */
    public void selectByVisibleText(final WebElement element,final String visibleText) {
    	if (visibleText.isBlank()) {
    	    throw new IllegalArgumentException(
    	            "Visible text cannot be blank.");
    	}
        try {
            getSelect(element).selectByVisibleText(visibleText);
            LOGGER.info("Selected dropdown option [{}].",visibleText);
        } catch (Exception exception) {
            LOGGER.error( "Unable to select dropdown option [{}].",visibleText,exception);
            throw new ElementException("Unable to select dropdown option: "+ visibleText, exception);
        }
    }

    /**
     * Selects dropdown option using visible text.
     *
     * @param locator dropdown locator
     * @param visibleText visible text
     */
    public void selectByVisibleText(final By locator,final String visibleText) {
        selectByVisibleText( waitUtils.waitForElementVisible(locator), visibleText);
    }

    /**
     * Selects dropdown option using value.
     *
     * @param element dropdown element
     * @param value option value
     */
    public void selectByValue(final WebElement element,final String value) {
        Objects.requireNonNull(value,"Value cannot be null.");
        try {
            getSelect(element).selectByValue(value);
            LOGGER.info("Selected dropdown value [{}].",value);
        } catch (Exception exception) {
            LOGGER.error("Unable to select dropdown value [{}].",value,exception);
            throw new ElementException("Unable to select dropdown value: "+ value,exception);
        }
    }

    /**
     * Selects dropdown option using value.
     *
     * @param locator dropdown locator
     * @param value option value
     */
    public void selectByValue(final By locator,final String value) {
        selectByValue(waitUtils.waitForElementVisible(locator), value);
    }

    /**
     * Selects dropdown option using index.
     *
     * @param element dropdown element
     * @param index dropdown index
     */
    public void selectByIndex(final WebElement element,final int index) {
        try {
        	if (index < 0) {
        	    throw new IllegalArgumentException("Index cannot be negative.");
        	}
            getSelect(element).selectByIndex(index);
            LOGGER.info("Selected dropdown index [{}].",index);
        } catch (Exception exception) {
            LOGGER.error("Unable to select dropdown index [{}].",index, exception);
            throw new ElementException( "Unable to select dropdown index: " + index,exception);
        }
    }

    /**
     * Selects dropdown option using index.
     *
     * @param locator dropdown locator
     * @param index dropdown index
     */
    public void selectByIndex(final By locator,final int index) {
        selectByIndex(waitUtils.waitForElementVisible(locator),index);
    }
}