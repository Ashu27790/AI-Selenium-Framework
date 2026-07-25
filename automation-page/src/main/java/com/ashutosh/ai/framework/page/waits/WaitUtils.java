package com.ashutosh.ai.framework.page.waits;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ashutosh.ai.framework.common.exceptions.WaitException;
import com.ashutosh.ai.framework.config.manager.ConfigurationManager;
import com.ashutosh.ai.framework.driver.manager.DriverManager;
import java.util.concurrent.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;
/**
 * Utility class responsible for explicit waits used throughout the Page Object
 * Model.
 *
 * <p>
 * Provides reusable synchronization methods for Selenium WebDriver using
 * ExpectedConditions.
 * </p>
 *
 * @author Ashutosh Kumar Sahu
 * @version 1.0
 */
public class WaitUtils {

    private static final Logger LOGGER = LogManager.getLogger(WaitUtils.class);

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor jsExecutor;

    /**
     * Initializes WaitUtils.
     */
    public WaitUtils() {

        this.driver = DriverManager.getDriver();
        int explicitWait = ConfigurationManager.getInstance().getIntProperty("explicit.wait");
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    /**
     * Waits until an element located by the locator is visible.
     *
     * @param locator element locator
     * @return visible WebElement
     */
    public WebElement waitForVisibility(By locator) {

        LOGGER.debug("Waiting for visibility of element: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits until a WebElement becomes visible.
     *
     * @param element WebElement
     * @return visible WebElement
     */
    public WebElement waitForVisibility(WebElement element) {

        LOGGER.debug("Waiting for element visibility.");
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits until an element becomes clickable.
     *
     * @param locator element locator
     * @return clickable WebElement
     */
    public WebElement waitForClickable(By locator) {

        LOGGER.debug("Waiting for element to become clickable: {}", locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits until a WebElement becomes clickable.
     *
     * @param element WebElement
     * @return clickable WebElement
     */
    public WebElement waitForClickable(WebElement element) {

        LOGGER.debug("Waiting for WebElement to become clickable.");
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Waits until an element is present in the DOM.
     *
     * @param locator element locator
     * @return located WebElement
     */
    public WebElement waitForPresence(By locator) {

        LOGGER.debug("Waiting for element presence: {}", locator);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Waits until an element becomes invisible.
     *
     * @param locator element locator
     * @return true if invisible
     */
    public boolean waitForInvisibility(By locator) {

        LOGGER.debug("Waiting for element invisibility: {}", locator);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Waits until the page title contains the specified text.
     *
     * @param title expected title fragment
     * @return true if condition is satisfied
     */
    public boolean waitForTitleContains(String title) {

        LOGGER.debug("Waiting for title to contain: {}", title);
        return wait.until(ExpectedConditions.titleContains(title));
    }

    /**
     * Waits until the URL contains the specified text.
     *
     * @param url expected URL fragment
     * @return true if condition is satisfied
     */
    public boolean waitForUrlContains(String url) {

        LOGGER.debug("Waiting for URL to contain: {}", url);
        return wait.until(ExpectedConditions.urlContains(url));
    }

    /**
     * Waits until the page is completely loaded.
     */
    public void waitForPageLoad() {

        LOGGER.debug("Waiting for page load completion.");
        wait.until(driver -> "complete".equals(
                jsExecutor.executeScript("return document.readyState")));
    }

    /**
     * Waits until the expected number of elements are present.
     *
     * @param locator element locator
     * @param count expected element count
     * @return list of WebElements
     */
    public List<WebElement> waitForElementCount(By locator, int count) {

        LOGGER.debug("Waiting for {} elements for locator: {}", count, locator);
        return wait.until(ExpectedConditions.numberOfElementsToBe(locator, count));
    }

    /**
     * Waits until a browser alert is present.
     *
     * @return the available {@link Alert} instance
     * @throws WaitException if the alert is not present within the configured timeout
     */
    public Alert waitForAlert() {
        try {
            LOGGER.debug("Waiting for browser alert.");
            final Alert alert = wait.until( ExpectedConditions.alertIsPresent());
            LOGGER.info("Browser alert is present.");
            return alert;
        } catch (Exception exception) {
            LOGGER.error( "Failed to wait for browser alert.",exception);
            throw new WaitException("Failed to wait for browser alert.",exception);
        }
    }

    /**
     * Waits until the specified element becomes visible.
     *
     * @param locator element locator
     * @return visible WebElement
     * @throws WaitException if the element does not become visible within the configured timeout
     */
    public WebElement waitForElementVisible(final By locator) {

        Objects.requireNonNull(locator,"Locator cannot be null.");
        try {
            LOGGER.debug("Waiting for element visibility: {}",locator);
            final WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            LOGGER.info("Element is visible: {}",locator);
            return element;
        } catch (Exception exception) {
            LOGGER.error("Failed to wait for element visibility: {}",locator, exception);
            throw new WaitException("Failed to wait for element visibility: " + locator,exception);
        }
    }

	public void waitForWindowCount(final int expectedWindowCount) {
		if (expectedWindowCount <= 0) {
	        throw new IllegalArgumentException("Expected window count must be greater than zero.");
	    }
	    try {
	        LOGGER.debug("Waiting for {} browser window(s).",expectedWindowCount);
	        wait.until(ExpectedConditions.numberOfWindowsToBe(expectedWindowCount));
	        LOGGER.info("{} browser window(s) are available.",expectedWindowCount);
	    } catch (TimeoutException exception) {
	        LOGGER.error("Timed out waiting for {} browser window(s).",expectedWindowCount, exception);
	        throw new WaitException(String.format("Timed out waiting for %d browser window(s).",expectedWindowCount),exception);
	    }
		
	}

	public void waitForNewWindow() {
		final int expectedWindowCount =driver.getWindowHandles().size() + 1;
	    LOGGER.debug("Waiting for a new browser window. Expected window count: {}",expectedWindowCount);
	    waitForWindowCount(expectedWindowCount);
		
	}


}