package com.ashutosh.ai.framework.page.actions;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ashutosh.ai.framework.common.exceptions.ElementException;

/**
 * Provides reusable JavaScript-based actions for web elements.
 *
 * <p>
 * This class is intended as a fallback when standard Selenium interactions
 * are insufficient due to dynamic page behavior or browser limitations.
 * </p>
 *
 * @author Ashutosh Kumar Sahu
 * @version 1.0
 */
public class JavaScriptActions {

    private static final Logger LOGGER =
            LogManager.getLogger(JavaScriptActions.class);

    private final JavascriptExecutor javascriptExecutor;

    /**
     * Constructs a JavaScriptActions instance.
     *
     * @param driver Active WebDriver instance
     */
    public JavaScriptActions(WebDriver driver) {

        Objects.requireNonNull(driver,
                "WebDriver cannot be null.");

        this.javascriptExecutor = (JavascriptExecutor) driver;
    }

    /**
     * Clicks an element using JavaScript.
     *
     * @param element WebElement to click
     */
    public void click(WebElement element) {

        try {

            Objects.requireNonNull(element,
                    "WebElement cannot be null.");

            LOGGER.info("Clicking element using JavaScript.");

            javascriptExecutor.executeScript(
                    "arguments[0].click();",
                    element);

        } catch (Exception exception) {

            LOGGER.error("Failed to click element using JavaScript.",
                    exception);

            throw new ElementException(
                    "Unable to click element using JavaScript.",
                    exception);
        }
    }

    /**
     * Scrolls the element into view.
     *
     * @param element WebElement
     */
    public void scrollIntoView(WebElement element) {

        try {

            Objects.requireNonNull(element);

            LOGGER.info("Scrolling element into view.");

            javascriptExecutor.executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    element);

        } catch (Exception exception) {

            throw new ElementException(
                    "Unable to scroll element into view.",
                    exception);
        }
    }

    /**
     * Scrolls to the top of the page.
     */
    public void scrollToTop() {

        javascriptExecutor.executeScript(
                "window.scrollTo(0,0);");
    }

    /**
     * Scrolls to the bottom of the page.
     */
    public void scrollToBottom() {

        javascriptExecutor.executeScript(
                "window.scrollTo(0,document.body.scrollHeight);");
    }

    /**
     * Scrolls by the specified x and y offset.
     *
     * @param x horizontal pixels
     * @param y vertical pixels
     */
    public void scrollBy(int x,
                         int y) {

        javascriptExecutor.executeScript(
                "window.scrollBy(arguments[0],arguments[1]);",
                x,
                y);
    }

    /**
     * Highlights an element by drawing a red border.
     *
     * @param element WebElement
     */
    public void highlight(WebElement element) {

        try {

            javascriptExecutor.executeScript(
                    "arguments[0].style.border='3px solid red';",
                    element);

        } catch (Exception exception) {

            throw new ElementException(
                    "Unable to highlight element.",
                    exception);
        }
    }

    /**
     * Sets a value into an input field using JavaScript.
     *
     * @param element WebElement
     * @param value Value to set
     */
    public void setValue(WebElement element,
                         String value) {

        try {

            javascriptExecutor.executeScript(
                    "arguments[0].value=arguments[1];",
                    element,
                    value);

        } catch (Exception exception) {

            throw new ElementException(
                    "Unable to set value using JavaScript.",
                    exception);
        }
    }

    /**
     * Returns the page title using JavaScript.
     *
     * @return Page title
     */
    public String getTitle() {

        return javascriptExecutor.executeScript(
                "return document.title;")
                .toString();
    }

    /**
     * Returns the current page URL using JavaScript.
     *
     * @return Current URL
     */
    public String getCurrentUrl() {

        return javascriptExecutor.executeScript(
                "return document.URL;")
                .toString();
    }

    /**
     * Executes any JavaScript.
     *
     * @param script JavaScript
     * @param arguments Arguments
     * @return Result returned by JavaScript
     */
    public Object executeScript(String script,
                                Object... arguments) {

        try {

            return javascriptExecutor.executeScript(
                    script,
                    arguments);

        } catch (Exception exception) {

            throw new ElementException(
                    "Failed to execute JavaScript.",
                    exception);
        }
    }

    /**
     * Executes asynchronous JavaScript.
     *
     * @param script JavaScript
     * @param arguments Arguments
     * @return Result
     */
    public Object executeAsyncScript(String script,
                                     Object... arguments) {

        try {

            return javascriptExecutor.executeAsyncScript(
                    script,
                    arguments);

        } catch (Exception exception) {

            throw new ElementException(
                    "Failed to execute asynchronous JavaScript.",
                    exception);
        }
    }
}