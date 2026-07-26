package com.ashutosh.ai.framework.report.manager;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.ExtentTest;

/**
 * Manages ExtentTest instances for parallel execution.
 *
 * <p>
 * This class stores one ExtentTest instance per executing thread
 * using ThreadLocal to ensure thread safety during parallel execution.
 * </p>
 *
 * Responsibilities:
 * <ul>
 * <li>Create ExtentTest</li>
 * <li>Return current thread's ExtentTest</li>
 * <li>Remove ExtentTest after execution</li>
 * </ul>
 *
 * Author: Ashutosh Kumar Sahu
 */
public final class ExtentTestManager {

    /**
     * Logger instance.
     */
    private static final Logger LOGGER =LogManager.getLogger(ExtentTestManager.class);

    /**
     * ThreadLocal storage for ExtentTest.
     */
    private static final ThreadLocal<ExtentTest> EXTENT_TEST =new ThreadLocal<>();

    /**
     * Prevent instantiation.
     */
    private ExtentTestManager() {
    }

    /**
     * Creates a new ExtentTest for the current thread.
     *
     * @param testName test name
     */
    public static void createTest(final String testName) {
        LOGGER.info("Creating ExtentTest : {}", testName);
        final ExtentTest extentTest = ExtentReportManager.getInstance().getExtentReports().createTest(testName);
        EXTENT_TEST.set(extentTest);
    }
    public static void createTest(final String testName,final String description) {
        LOGGER.info("Creating ExtentTest : {}", testName);
        final ExtentTest extentTest =ExtentReportManager.getInstance().getExtentReports().createTest(testName, description);
        EXTENT_TEST.set(extentTest);
    }
    /**
     * Returns the ExtentTest associated with the current thread.
     *
     * @return current thread ExtentTest
     */
    public static ExtentTest getTest() {
    	return Objects.requireNonNull(EXTENT_TEST.get(),"ExtentTest has not been initialized for the current thread. "+ "Ensure createTest() is called before accessing the test instance.");
    }

    /**
     * Removes the ExtentTest from the current thread.
     */
    public static void removeTest() {
        LOGGER.debug("Removing ExtentTest from current thread.");
        EXTENT_TEST.remove();
    }

}