package com.ashutosh.ai.framework.page.actions;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import com.ashutosh.ai.framework.common.exceptions.AlertException;
import com.ashutosh.ai.framework.page.waits.WaitUtils;

/**
 * Provides utility methods for interacting with JavaScript alerts,
 * confirmation dialogs, and prompt dialogs.
 *
 * <p>
 * This class centralizes all browser alert operations and wraps Selenium
 * exceptions into framework-specific {@link AlertException}.
 * </p>
 *
 * @author Ashutosh
 */
public class AlertActions {

    private static final Logger LOGGER =
            LogManager.getLogger(AlertActions.class);

    private final WebDriver driver;
    private final WaitUtils waitUtils;

    /**
     * Constructs AlertActions.
     *
     * @param driver WebDriver instance
     * @param waitUtils Wait utility instance
     */
    public AlertActions(
            final WebDriver driver,
            final WaitUtils waitUtils) {
        this.driver = Objects.requireNonNull(driver,"WebDriver cannot be null.");
        this.waitUtils = Objects.requireNonNull(waitUtils,"WaitUtils cannot be null.");
    }
    /**
     * Waits until an alert is present.
     *
     * @return Alert instance
     * @throws AlertException if alert is not found
     */
    public Alert waitForAlert() {
        try {
            final Alert alert = waitUtils.waitForAlert();
            LOGGER.info("Alert is present.");
            return alert;
        } catch (AlertException  exception) {
            LOGGER.error("Unable to wait for alert.",exception);
            throw new AlertException("Unable to wait for alert.",exception);
        }
    }

    /**
     * Accepts the current alert.
     *
     * @throws AlertException if operation fails
     */
    public void acceptAlert() {
        try {
        	LOGGER.debug("Accepting browser alert.");
            waitForAlert().accept();
            LOGGER.info("Alert accepted successfully.");
        } catch (NoAlertPresentException | AlertException   exception) {
            LOGGER.error("Unable to accept alert.", exception);
            throw new AlertException("Unable to accept alert.",exception);
        }
    }

    /**
     * Dismisses the current alert.
     *
     * @throws AlertException if operation fails
     */
    public void dismissAlert() {

        try {
            waitForAlert().dismiss();
            LOGGER.info("Alert dismissed successfully.");
        } catch (Exception exception) {
            LOGGER.error("Unable to dismiss alert.",exception);
            throw new AlertException("Unable to dismiss alert.", exception);
        }
    }

    /**
     * Returns the alert text.
     *
     * @return Alert text
     * @throws AlertException if operation fails
     */
    public String getAlertText() {
        try {
            final String text = waitForAlert().getText();
            LOGGER.info("Alert text retrieved successfully.");
            return text;
        } catch (Exception exception) {
            LOGGER.error( "Unable to retrieve alert text.", exception);
            throw new AlertException("Unable to retrieve alert text.", exception);
        }
    }

    /**
     * Sends text to a prompt alert.
     *
     * @param text text to send
     * @throws AlertException if operation fails
     */
    public void sendText(final String text) {
        Objects.requireNonNull( text,"Text cannot be null.");
        if (text.isBlank()) {
            throw new IllegalArgumentException("Alert text cannot be blank.");
        }
        try {
            waitForAlert().sendKeys(text);
            LOGGER.info( "Text entered into alert successfully.");
        } catch (Exception exception) {
            LOGGER.error( "Unable to send text to alert.",exception);
            throw new AlertException( "Unable to send text to alert.",exception);
        }
    }

    /**
     * Checks whether an alert is currently present.
     *
     * @return true if alert is present; otherwise false
     */
    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            LOGGER.debug("Alert is present.");
            return true;
        } catch (NoAlertPresentException exception) {
            LOGGER.debug("Alert is not present.");
            return false;
        }
    }

}