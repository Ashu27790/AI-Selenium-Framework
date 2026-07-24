package com.ashutosh.ai.framework.page.waits;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ashutosh.ai.framework.config.manager.ConfigurationManager;
import com.ashutosh.ai.framework.driver.manager.DriverManager;

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
}