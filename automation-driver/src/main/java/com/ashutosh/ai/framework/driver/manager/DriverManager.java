package com.ashutosh.ai.framework.driver.manager;

import org.openqa.selenium.WebDriver;

/**
 * DriverManager
 *
 * Stores WebDriver instances using ThreadLocal.
 *
 * Responsibilities:
 * - Store Driver
 * - Return Driver
 * - Remove Driver
 *
 * Supports Parallel Execution.
 *
 * @author Ashutosh Kumar Sahu
 * @since 1.0
 */
public final class DriverManager {

    private DriverManager() {

    }
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    
    public static void setDriver(WebDriver driver) {
    	DRIVER.set(driver);
    }
    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void removeDriver() {
        DRIVER.remove();
    }
}