package com.ashutosh.ai.framework.page.base;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ashutosh.ai.framework.config.manager.ConfigurationManager;
import com.ashutosh.ai.framework.driver.manager.DriverManager;

/**
 * BasePage is the parent class for all Page Objects.
 *
 * <p>
 * It initializes the common Selenium components and provides reusable browser
 * operations for all page classes.
 * </p>
 *
 * Responsibilities:
 * <ul>
 * <li>Initialize WebDriver</li>
 * <li>Initialize WebDriverWait</li>
 * <li>Initialize JavascriptExecutor</li>
 * <li>Provide common browser navigation methods</li>
 * </ul>
 *
 * @author Ashutosh Kumar Sahu
 * @version 1.0
 */
public abstract class BasePage {

    private static final Logger LOGGER = LogManager.getLogger(BasePage.class);

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final JavascriptExecutor jsExecutor;

    protected final ConfigurationManager configurationManager =
            ConfigurationManager.getInstance();

    /**
     * Initializes common Selenium objects for every Page Object.
     */
    protected BasePage() {

        this.driver = DriverManager.getDriver();

        int explicitWait =
                configurationManager.getIntProperty("explicit.wait");

        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));

        this.jsExecutor = (JavascriptExecutor) driver;

        LOGGER.info("Initialized Page : {}", getClass().getSimpleName());
    }

    /**
     * Returns the current page title.
     *
     * @return Page title
     */
    public String getTitle() {

        LOGGER.debug("Getting page title.");

        return driver.getTitle();
    }

    /**
     * Returns the current page URL.
     *
     * @return Current URL
     */
    public String getCurrentUrl() {

        LOGGER.debug("Getting current URL.");

        return driver.getCurrentUrl();
    }

    /**
     * Navigates to the specified URL.
     *
     * @param url URL to navigate to
     */
    public void navigateTo(String url) {

        LOGGER.info("Navigating to URL : {}", url);

        driver.navigate().to(url);
    }

    /**
     * Refreshes the current page.
     */
    public void refreshPage() {

        LOGGER.info("Refreshing current page.");

        driver.navigate().refresh();
    }

    /**
     * Navigates back to the previous page.
     */
    public void back() {

        LOGGER.info("Navigating back.");

        driver.navigate().back();
    }

    /**
     * Navigates forward to the next page.
     */
    public void forward() {

        LOGGER.info("Navigating forward.");

        driver.navigate().forward();
    }

  

}