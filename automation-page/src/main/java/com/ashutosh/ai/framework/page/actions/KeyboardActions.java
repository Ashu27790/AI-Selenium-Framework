package com.ashutosh.ai.framework.page.actions;
import java.util.Objects;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.ashutosh.ai.framework.common.exceptions.ElementException;
import com.ashutosh.ai.framework.page.waits.WaitUtils;
public class KeyboardActions {

	private static final Logger LOGGER =LogManager.getLogger(KeyboardActions.class);

	private final WebDriver driver;
	private final WaitUtils waitUtils;

	public KeyboardActions(final WebDriver driver,final WaitUtils waitUtils) {

		this.driver = Objects.requireNonNull(driver,"WebDriver cannot be null.");

		this.waitUtils = Objects.requireNonNull(waitUtils,"WaitUtils cannot be null.");
	}
	private WebElement validateElement(final WebElement element) {

	    return Objects.requireNonNull(element,"WebElement cannot be null.");
	}
	private WebElement getVisibleElement(final By locator) {

	    Objects.requireNonNull(locator,"Locator cannot be null.");
	    return waitUtils.waitForVisibility(locator);
	}
	
	 /**
     * Presses a key.
     *
     * @param key keyboard key
     */
    public void keyDown(final Keys key) {

        Objects.requireNonNull(key, "Key cannot be null.");

        executeAction(actions -> actions.keyDown(key),
                String.format("Key down performed for key [%s]", key));
    }

    /**
     * Releases a key.
     *
     * @param key keyboard key
     */
    public void keyUp(final Keys key) {

        Objects.requireNonNull(key, "Key cannot be null.");

        executeAction(actions -> actions.keyUp(key),
                String.format("Key up performed for key [%s]", key));
    }

    /**
     * Sends keys to the active element.
     *
     * @param keys keys to send
     */
    public void sendKeys(final CharSequence... keys) {

        Objects.requireNonNull(keys, "Keys cannot be null.");

        executeAction(actions -> actions.sendKeys(keys),
                "Keys sent to active element.");
    }

    /**
     * Sends keys to a web element located by locator.
     *
     * @param locator element locator
     * @param keys keys to send
     */
    public void sendKeys(final By locator, final CharSequence... keys) {

        Objects.requireNonNull(locator, "Locator cannot be null.");
        Objects.requireNonNull(keys, "Keys cannot be null.");

        final WebElement element = waitUtils.waitForElementVisible(locator);

        executeAction(actions -> actions.sendKeys(element, keys),
                String.format("Keys sent to element located by [%s]", locator));
    }

    /**
     * Sends keys to the supplied element.
     *
     * @param element web element
     * @param keys keys to send
     */
    public void sendKeys(final WebElement element, final CharSequence... keys) {

        Objects.requireNonNull(element, "WebElement cannot be null.");
        Objects.requireNonNull(keys, "Keys cannot be null.");

        executeAction(actions -> actions.sendKeys(element, keys),
                "Keys sent to WebElement.");
    }

    /**
     * Performs ENTER key press.
     */
    public void pressEnter() {

        executeAction(actions -> actions.sendKeys(Keys.ENTER),
                "ENTER key pressed.");
    }

    /**
     * Performs TAB key press.
     */
    public void pressTab() {

        executeAction(actions -> actions.sendKeys(Keys.TAB),
                "TAB key pressed.");
    }

    /**
     * Performs ESCAPE key press.
     */
    public void pressEscape() {

        executeAction(actions -> actions.sendKeys(Keys.ESCAPE),
                "ESCAPE key pressed.");
    }

    /**
     * Performs CTRL + A.
     */
    public void selectAll() {

        executeAction(actions -> actions.keyDown(Keys.CONTROL)
                .sendKeys("a")
                .keyUp(Keys.CONTROL),
                "CTRL+A performed.");
    }

    /**
     * Performs CTRL + C.
     */
    public void copy() {

        executeAction(actions -> actions.keyDown(Keys.CONTROL)
                .sendKeys("c")
                .keyUp(Keys.CONTROL),
                "CTRL+C performed.");
    }

    /**
     * Performs CTRL + V.
     */
    public void paste() {

        executeAction(actions -> actions.keyDown(Keys.CONTROL)
                .sendKeys("v")
                .keyUp(Keys.CONTROL),
                "CTRL+V performed.");
    }

    /**
     * Performs CTRL + X.
     */
    public void cut() {

        executeAction(actions -> actions.keyDown(Keys.CONTROL)
                .sendKeys("x")
                .keyUp(Keys.CONTROL),
                "CTRL+X performed.");
    }

    /**
     * Executes keyboard action with centralized logging and exception handling.
     *
     * @param action action to execute
     * @param successMessage success log message
     */
    private void executeAction(final Consumer<Actions> action, final String successMessage) {

        try {
            final Actions actions = new Actions(driver);

            action.accept(actions);

            actions.build().perform();

            LOGGER.info(successMessage);

        } catch (Exception exception) {

            LOGGER.error("Failed to execute keyboard action.", exception);

            throw new ElementException("Failed to execute keyboard action.", exception);
        }
    }

}
