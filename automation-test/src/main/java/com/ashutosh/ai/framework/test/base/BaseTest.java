package com.ashutosh.ai.framework.test.base;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.ashutosh.ai.framework.common.enums.BrowserType;
import com.ashutosh.ai.framework.common.exceptions.*;
import com.ashutosh.ai.framework.config.manager.ConfigurationManager;
import com.ashutosh.ai.framework.driver.factory.DriverFactory;
import com.ashutosh.ai.framework.driver.manager.DriverManager;

/**
 * BaseTest serves as the foundation for all TestNG test classes.
 *
 * <p>
 * Responsibilities:
 * <ul>
 * <li>Initialize WebDriver before every test.</li>
 * <li>Configure browser settings.</li>
 * <li>Store WebDriver in ThreadLocal.</li>
 * <li>Quit browser after execution.</li>
 * <li>Remove ThreadLocal reference.</li>
 * </ul>
 * </p>
 *
 * This implementation is thread-safe and supports parallel execution.
 *
 * @author Ashutosh Kumar Sahu
 * @version 1.0
 */
public abstract class BaseTest {

    private static final Logger LOGGER = LogManager.getLogger(BaseTest.class);

    private static final ConfigurationManager configurationManager =
            ConfigurationManager.getInstance();

    /**
     * Initializes browser before every test execution.
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        LOGGER.info("========== Test Execution Started ==========");
        WebDriver driver = createDriver();
        DriverManager.setDriver(driver);
        initializeBrowser(driver);
        LOGGER.info("Browser initialized successfully.");
    }

    /**
     * Creates WebDriver based on configured browser.
     *
     * @return WebDriver instance
     */
    private WebDriver createDriver() {

        String browser = configurationManager.getProperty("browser");
        try {
            BrowserType browserType =
                    BrowserType.valueOf(browser.trim().toUpperCase());
            LOGGER.info("Launching browser : {}", browserType);
            WebDriver driver = DriverFactory.createDriver(browserType);
            if (driver == null) {
                throw new DriverException(
                        "DriverFactory returned null for browser : " + browserType);
            }
            return driver;
        } catch (IllegalArgumentException exception) {
            LOGGER.error("Invalid browser configured : {}", browser, exception);
            throw new DriverException(
                    "Unsupported browser configured in config.properties : " + browser,
                    exception);
        } catch (DriverException exception) {
            LOGGER.error("Driver creation failed.", exception);
            throw exception;
        } catch (Exception exception) {
            LOGGER.error("Unexpected error while creating WebDriver.", exception);
            throw new DriverException(
                    "Unable to create WebDriver instance.",
                    exception);
        }
    }

    /**
     * Applies browser configurations.
     *
     * @param driver WebDriver instance
     */
    private void initializeBrowser(WebDriver driver) {

        try {
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            int implicitWait =configurationManager.getIntProperty("implicit.wait");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
            LOGGER.info("Browser configuration completed.");
        } catch (Exception exception) {
            LOGGER.error("Failed to initialize browser configuration.",exception);
            throw new DriverException("Unable to configure browser.",exception);
        }
    }

    /**
     * Closes browser after every test execution.
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        WebDriver driver = DriverManager.getDriver();

        try {
            if (driver != null) {
                LOGGER.info("Closing browser...");
                driver.quit();
                LOGGER.info("Browser closed successfully.");
            } else {
                LOGGER.warn("No WebDriver instance found for cleanup.");
            }
        } catch (Exception exception) {
            LOGGER.error("Exception occurred while closing browser.",exception);
        } finally {
            DriverManager.removeDriver();
            LOGGER.info("Driver removed from ThreadLocal.");
            LOGGER.info("========== Test Execution Finished ==========");
        }
    }

}