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
 * Enterprise Screenshot Utility.
 *
 * Supports:
 * <ul>
 *     <li>Viewport Screenshot</li>
 *     <li>Element Screenshot</li>
 *     <li>Base64 Screenshot</li>
 *     <li>Extent Report Compatible Relative Paths</li>
 * </ul>
 *
 * @author Ashutosh Kumar Sahu
 * @version 2.0
 */
public class ScreenshotUtils {
    private static final Logger LOGGER =LogManager.getLogger(ScreenshotUtils.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final String REPORT_RELATIVE_SCREENSHOT_PATH = "../screenshots/";
    private final WebDriver driver;
    private final String screenshotDirectory;
    /**
     * Constructor.
     *
     * @param driver WebDriver
     */
    public ScreenshotUtils(final WebDriver driver) {
        this.driver = Objects.requireNonNull( driver, "WebDriver cannot be null.");
        this.screenshotDirectory =Objects.requireNonNull( ConfigurationManager.getInstance() .getProperty("screenshot.directory"), "Screenshot directory is not configured.");
        createScreenshotDirectory();
    }
    /**
     * Captures browser screenshot.
     *
     * Returns relative path for Extent Report.
     *
     * @param screenshotName screenshot name
     * @return relative screenshot path
     */
    public String captureScreenshot(final String screenshotName) {
        validateScreenshotName(screenshotName);
        try {
            Path destination = buildScreenshotPath(screenshotName);
            Files.write(
                    destination,
                    getScreenshotExecutor()
                            .getScreenshotAs(OutputType.BYTES));

            LOGGER.info(
                    "Screenshot saved successfully : {}",
                    destination.toAbsolutePath());

            String relativePath =
                    REPORT_RELATIVE_SCREENSHOT_PATH
                            + destination.getFileName();

            LOGGER.info(
                    "Returning Extent compatible path : {}",
                    relativePath);

            return relativePath;

        } catch (IOException | WebDriverException exception) {

            LOGGER.error(
                    "Unable to capture screenshot.",
                    exception);

            throw new ScreenshotException(
                    "Unable to capture screenshot.",
                    exception);
        }
    }

    /**
     * Captures WebElement screenshot.
     *
     * @param element WebElement
     * @param screenshotName screenshot name
     * @return relative screenshot path
     */
    public String captureElement(
            final WebElement element,
            final String screenshotName) {

        Objects.requireNonNull(
                element,
                "Element cannot be null.");

        validateScreenshotName(screenshotName);

        try {

            Path destination =
                    buildScreenshotPath(screenshotName);

            Files.write(
                    destination,
                    element.getScreenshotAs(OutputType.BYTES));

            LOGGER.info(
                    "Element screenshot saved : {}",
                    destination.toAbsolutePath());

            return REPORT_RELATIVE_SCREENSHOT_PATH
                    + destination.getFileName();

        } catch (IOException | WebDriverException exception) {

            LOGGER.error(
                    "Unable to capture element screenshot.",
                    exception);

            throw new ScreenshotException(
                    "Unable to capture element screenshot.",
                    exception);
        }
    }

    /**
     * Captures element screenshot using locator.
     *
     * @param locator locator
     * @param screenshotName screenshot name
     * @return screenshot path
     */
    public String captureElement(
            final By locator,
            final String screenshotName) {

        Objects.requireNonNull(
                locator,
                "Locator cannot be null.");

        WaitUtils wait =
                new WaitUtils();

        return captureElement(
                wait.waitForElementVisible(locator),
                screenshotName);
    }

    /**
     * Returns Base64 screenshot.
     *
     * Ideal for Jenkins / GitHub Actions / CI Reports.
     *
     * @return Base64 image
     */
    public String captureBase64() {

        LOGGER.debug("Capturing Base64 screenshot.");

        return getScreenshotExecutor()
                .getScreenshotAs(OutputType.BASE64);
    }

    /**
     * Creates screenshot directory.
     */
    private void createScreenshotDirectory() {

        try {

            Files.createDirectories(
                    Paths.get(screenshotDirectory));

            LOGGER.info(
                    "Screenshot directory ready : {}",
                    screenshotDirectory);

        } catch (IOException exception) {

            LOGGER.error(
                    "Unable to create screenshot directory.",
                    exception);

            throw new ScreenshotException(
                    "Unable to create screenshot directory.",
                    exception);
        }
    }

    /**
     * Builds screenshot destination.
     *
     * @param screenshotName screenshot name
     * @return destination path
     */
    private Path buildScreenshotPath(
            final String screenshotName) {

        String timestamp =
                LocalDateTime.now()
                        .format(FORMATTER);

        return Paths.get(
                screenshotDirectory,
                screenshotName
                        + "_"
                        + timestamp
                        + ".png");
    }

    /**
     * Returns screenshot executor.
     *
     * @return TakesScreenshot
     */
    private TakesScreenshot getScreenshotExecutor() {

        return (TakesScreenshot) driver;
    }
    /**
     * Validates screenshot name.
     *
     * @param screenshotName screenshot name
     */
    private void validateScreenshotName(
            final String screenshotName) {

        Objects.requireNonNull(
                screenshotName,
                "Screenshot name cannot be null.");

        if (screenshotName.isBlank()) {

            throw new IllegalArgumentException(
                    "Screenshot name cannot be blank.");
        }
    }
}