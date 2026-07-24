package com.ashutosh.ai.framework.page.base;

import java.time.Duration;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ashutosh.ai.framework.config.manager.ConfigurationManager;
import com.ashutosh.ai.framework.driver.manager.DriverManager;
import com.ashutosh.ai.framework.page.actions.ElementActions;
import com.ashutosh.ai.framework.page.waits.WaitUtils;

/**
 * BasePage serves as the parent class for all Page Objects.
 *
 * <p>
 * Responsibilities:
 * <ul>
 * <li>Initialize common Selenium components.</li>
 * <li>Provide browser navigation methods.</li>
 * <li>Provide reusable action classes.</li>
 * </ul>
 * </p>
 *
 * @author Ashutosh Kumar Sahu
 * @version 1.0
 */
public abstract class BasePage {

    private static final Logger LOGGER =
            LogManager.getLogger(BasePage.class);

    private static final ConfigurationManager CONFIG =
            ConfigurationManager.getInstance();

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final JavascriptExecutor jsExecutor;

    protected final WaitUtils waitUtils;
    protected final ElementActions elementActions;

    /**
     * Initializes common Selenium objects required by all Page Objects.
     */
    protected BasePage() {

        this.driver = Objects.requireNonNull(
                DriverManager.getDriver(),
                "WebDriver is not initialized. "
                        + "Ensure BaseTest initializes the driver before creating Page Objects.");

        int explicitWait = CONFIG.getIntProperty("explicit.wait");

        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));

        this.jsExecutor = (JavascriptExecutor) driver;

        this.waitUtils = new WaitUtils();
        this.elementActions = new ElementActions();

        LOGGER.info("Page Object initialized : {}",
                getClass().getSimpleName());
    }

    /**
     * Returns current page title.
     *
     * @return page title
     */
    public String getTitle() {

        LOGGER.debug("Getting page title.");

        return driver.getTitle();
    }

    /**
     * Returns current page URL.
     *
     * @return current URL
     */
    public String getCurrentUrl() {

        LOGGER.debug("Getting current page URL.");

        return driver.getCurrentUrl();
    }

    /**
     * Navigates to specified URL.
     *
     * @param url application URL
     */
    public void navigateTo(String url) {

        LOGGER.info("Navigating to : {}", url);

        driver.navigate().to(url);

        waitUtils.waitForPageLoad();
    }

    /**
     * Refreshes current page.
     */
    public void refreshPage() {

        LOGGER.info("Refreshing current page.");

        driver.navigate().refresh();

        waitUtils.waitForPageLoad();
    }

    /**
     * Navigates back.
     */
    public void back() {

        LOGGER.info("Navigating back.");

        driver.navigate().back();

        waitUtils.waitForPageLoad();
    }

    /**
     * Navigates forward.
     */
    public void forward() {

        LOGGER.info("Navigating forward.");

        driver.navigate().forward();

        waitUtils.waitForPageLoad();
    }

    /**
     * Returns WebDriver instance.
     *
     * @return WebDriver
     */
    protected WebDriver getDriver() {
        return driver;
    }

    /**
     * Returns WebDriverWait instance.
     *
     * @return WebDriverWait
     */
    protected WebDriverWait getWait() {
        return wait;
    }

    /**
     * Returns JavascriptExecutor instance.
     *
     * @return JavascriptExecutor
     */
    protected JavascriptExecutor getJsExecutor() {
        return jsExecutor;
    }

    /**
     * Returns WaitUtils instance.
     *
     * @return WaitUtils
     */
    protected WaitUtils getWaitUtils() {
        return waitUtils;
    }

    /**
     * Returns ElementActions instance.
     *
     * @return ElementActions
     */
    protected ElementActions getElementActions() {
        return elementActions;
    }

}