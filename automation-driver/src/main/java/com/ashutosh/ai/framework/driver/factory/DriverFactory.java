package com.ashutosh.ai.framework.driver.factory;

import com.ashutosh.ai.framework.common.enums.BrowserType;
import com.ashutosh.ai.framework.config.manager.ConfigurationManager;
import com.ashutosh.ai.framework.driver.exceptions.DriverException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * DriverFactory
 *
 * Centralized factory to instantiate and configure WebDriver instances.
 * Implements the Factory Design Pattern to abstract browser creation logic,
 * supporting parallel local execution and remote execution (Docker/Selenium Grid).
 *
 * @author Ashutosh Kumar Sahu
 * @since 1.0
 */
public final class DriverFactory {

    private static final Logger LOGGER = LogManager.getLogger(DriverFactory.class);
    private static final ConfigurationManager CONFIG = ConfigurationManager.getInstance();

    private DriverFactory() {
        // Prevent instantiation of utility class
    }

    /**
     * Instantiates and configures a WebDriver instance based on the provided browser type.
     *
     * @param browserType The browser to instantiate (CHROME, FIREFOX, EDGE)
     * @return A fully configured WebDriver instance
     * @throws DriverException if driver initialization fails
     */
    public static WebDriver createDriver(BrowserType browserType) {
        Objects.requireNonNull(browserType, "Browser type must not be null");
        LOGGER.info("Initiating WebDriver creation for browser type: {}", browserType);

        try {
            boolean isRemote = CONFIG.getBooleanProperty("remote.execution");
            
            if (isRemote) {
                LOGGER.info("Remote execution is enabled. Routing request to Selenium Grid.");
                return createRemoteDriver(browserType);
            }
            
            LOGGER.info("Local execution is enabled. Routing request to local driver managers.");
            return createLocalDriver(browserType);
        } catch (Exception e) {
            LOGGER.fatal("Critical failure occurred while initializing {} driver", browserType, e);
            throw new DriverException("Failed to initialize driver for: " + browserType, e);
        }
    }

    private static WebDriver createLocalDriver(BrowserType browserType) {
        return switch (browserType) {
            case CHROME -> {
                LOGGER.debug("Setting up ChromeDriver using WebDriverManager...");
                WebDriverManager.chromedriver().setup();
                yield new ChromeDriver(getChromeOptions());
            }
            case FIREFOX -> {
                LOGGER.debug("Setting up FirefoxDriver using WebDriverManager...");
                WebDriverManager.firefoxdriver().setup();
                yield new FirefoxDriver(getFirefoxOptions());
            }
            case EDGE -> {
                LOGGER.debug("Setting up EdgeDriver using WebDriverManager...");
                WebDriverManager.edgedriver().setup();
                yield new EdgeDriver(getEdgeOptions());
            }
            default -> {
                LOGGER.error("The browser type '{}' is currently unsupported for local runs.", browserType);
                throw new DriverException("Unsupported local browser type: " + browserType);
            }
        };
    }

    private static WebDriver createRemoteDriver(BrowserType browserType) {
        String remoteUrl = CONFIG.getProperty("remote.url");
        if (remoteUrl == null || remoteUrl.isBlank()) {
            throw new DriverException("Remote execution enabled, but 'remote.url' is missing from configurations.");
        }
        
        try {
            LOGGER.info("Connecting to Remote Grid Hub at: {}", remoteUrl);
            URL url = new URL(remoteUrl);
            
            return switch (browserType) {
                case CHROME -> new RemoteWebDriver(url, getChromeOptions());
                case FIREFOX -> new RemoteWebDriver(url, getFirefoxOptions());
                case EDGE -> new RemoteWebDriver(url, getEdgeOptions());
                default -> throw new DriverException("Unsupported remote browser type: " + browserType);
            };
        } catch (MalformedURLException e) {
            throw new DriverException("Invalid Remote Grid hub URL specified: " + remoteUrl, e);
        }
    }

    private static ChromeOptions getChromeOptions() {
        LOGGER.debug("Building ChromeOptions...");
        ChromeOptions options = new ChromeOptions();
        
        if (CONFIG.getBooleanProperty("headless")) {
            LOGGER.info("Enabling Chrome Headless mode.");
            options.addArguments("--headless=new");
        }
        
        if (CONFIG.getBooleanProperty("incognito")) {
            LOGGER.info("Enabling Chrome Incognito mode.");
            options.addArguments("--incognito");
        }

        // Production-ready defaults for stability in containers
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");
        
        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        LOGGER.debug("Building FirefoxOptions...");
        FirefoxOptions options = new FirefoxOptions();
        
        if (CONFIG.getBooleanProperty("headless")) {
            LOGGER.info("Enabling Firefox Headless mode.");
            options.addArguments("-headless");
        }
        
        if (CONFIG.getBooleanProperty("incognito")) {
            LOGGER.info("Enabling Firefox Private Browsing mode.");
            options.addArguments("-private");
        }

        return options;
    }

    private static EdgeOptions getEdgeOptions() {
        LOGGER.debug("Building EdgeOptions...");
        EdgeOptions options = new EdgeOptions();
        
        if (CONFIG.getBooleanProperty("headless")) {
            LOGGER.info("Enabling Edge Headless mode.");
            options.addArguments("--headless=new");
        }
        
        if (CONFIG.getBooleanProperty("incognito")) {
            LOGGER.info("Enabling Edge InPrivate mode.");
            options.addArguments("-inprivate");
        }

        // Production-ready defaults for stability in containers
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--start-maximized");
        
        return options;
    }
}
