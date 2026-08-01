package com.ashutosh.ai.framework.report.listener;
import java.util.Arrays;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.ashutosh.ai.framework.common.exceptions.ConfigurationException;
import com.ashutosh.ai.framework.common.exceptions.ScreenshotException;
import com.ashutosh.ai.framework.config.manager.ConfigurationManager;
import com.ashutosh.ai.framework.driver.manager.DriverManager;
import com.ashutosh.ai.framework.page.utils.ScreenshotUtils;
import com.ashutosh.ai.framework.report.manager.ExtentReportManager;
import com.ashutosh.ai.framework.report.manager.ExtentTestManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

/**
 * TestNG listener responsible for reporting, logging and execution lifecycle
 * management.
 *
 * <p>
 * This implementation is thread-safe and relies on framework provided
 * ThreadLocal implementations for WebDriver and ExtentTest management.
 * </p>
 */
public class TestListener implements ITestListener {
    private static final Logger LOGGER = LogManager.getLogger(TestListener.class);
    private static final String UNKNOWN = "UNKNOWN";
    /**
     * Invoked before suite execution starts.
     *
     * @param context test context
     */
    @Override
    public void onStart(final ITestContext context) {
        try {
        	 LOGGER.info("========== SUITE STARTED ==========");
             LOGGER.info("Suite Name : {}", context.getSuite().getName());
             LOGGER.info("Java Version : {}", System.getProperty("java.version"));
             LOGGER.info("Operating System : {}", System.getProperty("os.name"));
             LOGGER.info("Browser : {}", getBrowser());
             LOGGER.info("Environment : {}", getEnvironment());
             LOGGER.info("Thread Count : {}",
                     context.getSuite().getXmlSuite().getThreadCount());
             LOGGER.info("Execution Started");
             LOGGER.info("===================================");

         } catch (RuntimeException exception) {

             LOGGER.error("Failed during suite startup.", exception);

         }
    }
    /**
     * Invoked after suite execution completes.
     *
     * @param context test context
     */
    @Override
    public void onFinish(final ITestContext context) {
        try {
            ExtentReportManager.getInstance().flush();
            LOGGER.info("Extent report flushed successfully.");
        } catch (RuntimeException exception) {
            LOGGER.error("Failed to flush extent report.", exception);
        } finally {
            cleanupThread();
            LOGGER.info("========== SUITE FINISHED ==========");
            LOGGER.info("Suite Name : {}", context.getSuite().getName());
            LOGGER.info("Execution Completed");
            LOGGER.info("====================================");
        }
    }
    /**
     * Invoked before test execution starts.
     *
     * @param result test result
     */
    @Override
    public void onTestStart(final ITestResult result) {
        try {
            final String testName = getTestName(result);
            final String browser = getBrowser();
            final String environment = getEnvironment();
            ExtentTestManager.createTest(testName);
            final ExtentTest test = ExtentTestManager.getTest();
            assignCategories(test,browser,environment, result.getTestClass().getName());logTestMetadata(test,testName,browser,environment, result);
        } catch (RuntimeException exception) {
            LOGGER.error("Error during test start.", exception);
        }
    }
    /**
     * Invoked when test execution passes.
     *
     * @param result test result
     */
    @Override
    public void onTestSuccess(final ITestResult result) {
        try {
            final ExtentTest test = ExtentTestManager.getTest();
            final long duration = getExecutionDuration(result);
            test.log(Status.PASS, "Test Passed Successfully");
            logExecutionTime(test, duration);
            LOGGER.info("Test Passed : {}", getTestName(result));
        } catch (RuntimeException exception) {
            LOGGER.error("Error while processing passed test.", exception);
        } finally {
            cleanupThread();
        }
    }

    /**
     * Invoked when test execution fails.
     *
     * @param result test result
     */
    @Override
    public void onTestFailure(final ITestResult result) {
        try {
            final ExtentTest test = ExtentTestManager.getTest();
            final Throwable throwable = result.getThrowable();
            final long duration = getExecutionDuration(result);
            test.log(Status.FAIL, throwable);
            final String screenshotPath = attachScreenshot(test, result);
            logExecutionTime(test, duration);
            LOGGER.error(
                    "Test Failed : {} | Thread Id : {}",
                    getTestName(result),
                    Thread.currentThread().getId(),
                    throwable);
            /*
             * ==========================================================
             * Future AI Extension Point
             * ==========================================================
             *
             * AI Failure Analysis
             * DOM Snapshot Collection
             * Root Cause Analysis
             * Console Log Collection
             * Network Traffic Collection
             * Self-Healing Recommendations
             *
             * Keep isolated from reporting workflow.
             */
            performFutureAiAnalysis(result, screenshotPath, duration);
        } catch (RuntimeException exception) {
            LOGGER.error("Error while handling failed test.", exception);
        } finally {
            cleanupThread();
        }
    }
    /**
     * Invoked when test execution is skipped.
     *
     * @param result test result
     */
    @Override
    public void onTestSkipped(final ITestResult result) {
        try {
            final ExtentTest test = ExtentTestManager.getTest();
            final Throwable throwable = result.getThrowable();
            final long duration = getExecutionDuration(result);
            if (Objects.nonNull(throwable)) {
                test.log(Status.SKIP, throwable);
                LOGGER.warn(
                        "Test Skipped : {} | Reason : {}",
                        getTestName(result),
                        throwable.getMessage());
            } else {
                test.log(Status.SKIP, "Test Skipped");
                LOGGER.warn("Test Skipped : {}", getTestName(result));
            }
            logExecutionTime(test, duration);
        } catch (RuntimeException exception) {
            LOGGER.error("Error while handling skipped test.", exception);
        } finally {
            cleanupThread();
        }
    }
    /**
     * Assigns categories for report filtering.
     *
     * @param test extent test
     * @param browser browser
     * @param environment environment
     * @param testClass test class
     */
    private void assignCategories(final ExtentTest test,
                                  final String browser,
                                  final String environment,
                                  final String testClass) {
        test.assignCategory(browser);
        test.assignCategory(environment);
        test.assignCategory(testClass);
    }
    /**
     * Logs test metadata.
     *
     * @param test extent test
     * @param testName test name
     * @param browser browser
     * @param environment environment
     * @param result test result
     */
    private void logTestMetadata(final ExtentTest test,
                                 final String testName,
                                 final String browser,
                                 final String environment,
                                 final ITestResult result) {
        final long threadId = Thread.currentThread().getId();
        LOGGER.info(
                "Test Started | Name={} | Class={} | Browser={} | Environment={} | Thread={}",
                testName,
                result.getTestClass().getName(),
                browser,
                environment,
                threadId);
        test.info("Browser : " + browser);
        test.info("Environment : " + environment);
        test.info("Thread Id : " + threadId);
    }
    /**
     * Attaches screenshot to report if available.
     *
     * @param test extent test
     * @param result test result
     */
    private String attachScreenshot(final ExtentTest test,final ITestResult result) {
        final WebDriver driver = DriverManager.getDriver();
        String screenshotPath = null;
        try {
            if (driver == null) {
                LOGGER.warn("Driver is null. Screenshot capture skipped for {}.",getTestName(result));
                return null;
            }
            final ScreenshotUtils screenshotUtils = new ScreenshotUtils(driver);
            screenshotPath = screenshotUtils.captureScreenshot(getTestName(result));
            test.fail( "Failure Screenshot",MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath) .build());
            LOGGER.info("Screenshot attached : {}", screenshotPath);
        } catch (ScreenshotException exception) {
            LOGGER.error( "Screenshot capture failed. Report generation will continue.",exception);
            test.warning("Screenshot capture failed : " + exception.getMessage());
            return null;
        }
        return screenshotPath;
    }
    /**
     * Logs execution duration.
     *
     * @param test extent test
     * @param duration execution duration
     */
    private void logExecutionTime(final ExtentTest test,final long duration) {
        test.info("Execution Duration : " + duration + " ms");
        LOGGER.info("Execution Duration : {} ms", duration);
    }
    /**
     * Returns execution duration.
     *
     * @param result test result
     *
     * @return duration in milliseconds
     */
    private long getExecutionDuration(final ITestResult result) {
        return result.getEndMillis() - result.getStartMillis();
    }

    /**
     * Returns test name including DataProvider parameters.
     *
     * @param result test result
     *
     * @return formatted test name
     */
    private String getTestName(final ITestResult result) {
        final Object[] parameters = result.getParameters();
        if (parameters == null || parameters.length == 0) {
            return result.getMethod().getMethodName();
        }
        return result.getMethod().getMethodName()+ Arrays.toString(parameters);
    }
    /**
     * Returns configured browser.
     *
     * @return browser
     */
    private String getBrowser() {
        try {
            return ConfigurationManager.getInstance().getProperty("browser");
        } catch (Exception exception) {
            LOGGER.warn("Unable to retrieve browser configuration.");
            return UNKNOWN;
        }
    }

    /**
     * Returns configured environment.
     *
     * @return environment
     */
    private String getEnvironment() {
        try {
            return ConfigurationManager.getInstance().getProperty("environment");
        } catch (ConfigurationException exception) {

            LOGGER.warn("Unable to retrieve environment configuration.", exception);

            return UNKNOWN;
        }
    }
    /**
     * Removes ThreadLocal resources.
     */
    private void cleanupThread() {
        try {
            ExtentTestManager.removeTest();
        } catch (RuntimeException exception) {
            LOGGER.error("Failed to cleanup ThreadLocal resources.", exception);
        }
    }
    /**
     * Future AI extension point.
     *
     * @param result test result
     */
    private void performFutureAiAnalysis(final ITestResult result,
            final String screenshotPath,
            final long executionDuration) {

// Reserved for future AI integrations.
}
}