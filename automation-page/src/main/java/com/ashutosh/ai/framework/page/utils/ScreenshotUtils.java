package com.ashutosh.ai.framework.page.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.ashutosh.ai.framework.common.exceptions.ScreenshotException;
import com.ashutosh.ai.framework.config.manager.ConfigurationManager;
import com.ashutosh.ai.framework.page.waits.WaitUtils;

/**
 * Utility class for capturing browser and element screenshots.
 *
 * <p>
 * Supports:
 * <ul>
 * <li>Viewport screenshot</li>
 * <li>Element screenshot</li>
 * <li>Base64 screenshot</li>
 * </ul>
 * </p>
 *
 * @author Ashutosh Kumar Sahu
 * @version 1.0
 */
public class ScreenshotUtils {
    private static final Logger LOGGER =LogManager.getLogger(ScreenshotUtils.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private final String screenshotDirectory;
    private final WebDriver driver;
    private final WaitUtils waitUtils;
    /**
     * Constructs ScreenshotUtils.
     *
     * @param driver WebDriver instance
     * @param waitUtils Wait utility
     */
    public ScreenshotUtils(final WebDriver driver,final WaitUtils waitUtils) {
        this.driver = Objects.requireNonNull(driver,"WebDriver cannot be null.");
        this.waitUtils = Objects.requireNonNull( waitUtils,"WaitUtils cannot be null.");
        this.screenshotDirectory = Objects.requireNonNull( ConfigurationManager.getInstance().getProperty("screenshot.directory"),"Screenshot directory is not configured.");
        createScreenshotDirectory();
    }
    /**
     * Captures browser viewport screenshot.
     *
     * @param screenshotName screenshot name
     *
     * @return screenshot path
     */
    public String captureViewport(final String screenshotName) {
        validateScreenshotName(screenshotName);
        try {
            final Path destination =buildScreenshotPath(screenshotName);
            Files.write(destination,getScreenshotDriver().getScreenshotAs(OutputType.BYTES));
            LOGGER.info( "Viewport screenshot captured [{}].",destination);
            return destination.toString();
        } catch (IOException | WebDriverException exception) {
            LOGGER.error( "Unable to capture viewport screenshot.", exception);
            throw new ScreenshotException( "Unable to capture viewport screenshot.", exception);
        }
    }

    /**
     * Captures screenshot of a WebElement.
     *
     * @param element target element
     * @param screenshotName screenshot name
     *
     * @return screenshot path
     */
    public String captureElement(final WebElement element,final String screenshotName) {
        Objects.requireNonNull(element, "Element cannot be null.");
        validateScreenshotName(screenshotName);
        try {
            final Path destination = buildScreenshotPath(screenshotName);
            Files.write(destination, element.getScreenshotAs(OutputType.BYTES));
            LOGGER.info( "Element screenshot captured [{}].", destination);
            return destination.toString();

        } catch (IOException | WebDriverException exception) {
            LOGGER.error("Unable to capture element screenshot.",exception);
            throw new ScreenshotException("Unable to capture element screenshot.",exception);
        }
    }
    /**
     * Captures screenshot using locator.
     *
     * @param locator element locator
     * @param screenshotName screenshot name
     *
     * @return screenshot path
     */
    public String captureElement(final By locator,final String screenshotName) {
        Objects.requireNonNull(locator,"Locator cannot be null.");
        return captureElement( waitUtils.waitForElementVisible(locator),screenshotName);
    }

    /**
     * Captures Base64 screenshot.
     *
     * @return Base64 screenshot
     */
    public String captureBase64() {
        LOGGER.debug("Capturing Base64 screenshot.");
        return getScreenshotDriver().getScreenshotAs(OutputType.BASE64);
    }
    /**
     * Creates screenshot directory.
     */
    private void createScreenshotDirectory() {
        try {
            Files.createDirectories(Paths.get(screenshotDirectory));
            LOGGER.debug( "Screenshot directory initialized [{}].",screenshotDirectory);
        } catch (IOException exception) {
            LOGGER.error( "Unable to create screenshot directory.", exception);
            throw new ScreenshotException( "Unable to create screenshot directory.",exception);
        }
    }
    /**
     * Builds screenshot path.
     *
     * @param screenshotName screenshot name
     *
     * @return screenshot path
     */
    private Path buildScreenshotPath(final String screenshotName) {
        final String timestamp = LocalDateTime.now().format(FORMATTER);
        return Paths.get( screenshotDirectory, screenshotName + "_" + timestamp + ".png");
    }
    /**
     * Returns screenshot driver.
     *
     * @return TakesScreenshot implementation
     */
    private TakesScreenshot getScreenshotDriver() {
        return (TakesScreenshot) driver;
    }
    /**
     * Validates screenshot name.
     *
     * @param screenshotName screenshot name
     */
    private void validateScreenshotName(final String screenshotName) {
        Objects.requireNonNull(screenshotName,"Screenshot name cannot be null.");
        if (screenshotName.isBlank()) {
            throw new IllegalArgumentException("Screenshot name cannot be blank.");
        }
    }

}