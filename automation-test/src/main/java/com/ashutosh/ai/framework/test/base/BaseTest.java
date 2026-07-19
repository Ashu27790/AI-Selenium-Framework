package com.ashutosh.ai.framework.test.base;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.ashutosh.ai.framework.common.enums.BrowserType;
import com.ashutosh.ai.framework.config.manager.ConfigurationManager;
import com.ashutosh.ai.framework.driver.exceptions.DriverException;
import com.ashutosh.ai.framework.driver.factory.DriverFactory;
import com.ashutosh.ai.framework.driver.manager.DriverManager;

/**
 * Base class for all TestNG test classes.
 *
 * <p>
 * Responsible for:
 * <ul>
 *     <li>Initializing WebDriver before every test.</li>
 *     <li>Managing browser configuration.</li>
 *     <li>Cleaning up WebDriver after every test.</li>
 *     <li>Supporting thread-safe parallel execution.</li>
 * </ul>
 * </p>
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

        try {

            WebDriver driver = createDriver();

            DriverManager.setDriver(driver);

            initializeBrowser(driver);

            LOGGER.info("Browser initialized successfully.");

        } catch (Exception exception) {

            LOGGER.error("Failed to initialize browser.", exception);

            throw new DriverException(
                    "Unable to initialize browser for test execution.",
                    exception);

        }
    }

    /**
     * Creates WebDriver based on configured browser.
     *
     * @return WebDriver instance
     */
    private WebDriver createDriver() {

        String browser = configurationManager.getProperty("browser");

        BrowserType browserType =
                BrowserType.valueOf(browser.trim().toUpperCase());

        LOGGER.info("Launching Browser : {}", browserType);

        return DriverFactory.createDriver(browserType);
    }

    /**
     * Applies common browser configurations.
     *
     * @param driver WebDriver instance
     */
    private void initializeBrowser(WebDriver driver) {

        driver.manage().window().maximize();

        driver.manage().deleteAllCookies();

        int implicitWait =
                configurationManager.getIntProperty("implicit.wait");

        driver.manage()
                .timeouts()
                .implicitlyWait(Duration.ofSeconds(implicitWait));

        LOGGER.info("Browser configuration completed.");
    }

    /**
     * Cleans up browser after every test.
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        WebDriver driver = DriverManager.getDriver();

        try {

            if (driver != null) {

                LOGGER.info("Closing browser...");

                driver.quit();

                LOGGER.info("Browser closed successfully.");
            }

        } catch (Exception exception) {

            LOGGER.error("Error while closing browser.", exception);

        } finally {

            DriverManager.removeDriver();

            LOGGER.info("Driver removed from ThreadLocal.");

            LOGGER.info("========== Test Execution Finished ==========");
        }
    }

}