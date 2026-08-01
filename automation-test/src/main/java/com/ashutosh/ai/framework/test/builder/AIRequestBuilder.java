package com.ashutosh.ai.framework.test.builder;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import com.ashutosh.ai.framework.ai.dto.AIRequest;
import com.ashutosh.ai.framework.config.manager.ConfigurationManager;
import com.ashutosh.ai.framework.driver.manager.DriverManager;

/**
 * Builds AIRequest by collecting execution context from
 * Selenium, TestNG and the Framework.
 *
 * Responsibilities:
 * <ul>
 * <li>Collect test information</li>
 * <li>Collect Selenium execution context</li>
 * <li>Collect browser information</li>
 * <li>Collect framework metadata</li>
 * </ul>
 *
 * @author Ashutosh Kumar Sahu
 * @version 2.0
 */
public final class AIRequestBuilder {

    private static final Logger LOGGER =
            LogManager.getLogger(AIRequestBuilder.class);

    private static final ConfigurationManager CONFIG =
            ConfigurationManager.getInstance();

    private AIRequestBuilder() {

    }

    /**
     * Builds enterprise AI request.
     *
     * @param result TestNG result
     * @return populated AIRequest
     */
    public static AIRequest build(final ITestResult result) {

        LOGGER.info("Building Enterprise AI Request...");

        AIRequest request = new AIRequest();

        collectTestInformation(result, request);

        collectFailureInformation(result, request);

        collectBrowserInformation(request);

        collectFrameworkInformation(request);

        LOGGER.info("AI Request created successfully.");

        return request;
    }

    /**
     * Collects TestNG information.
     */
    private static void collectTestInformation(
            final ITestResult result,
            final AIRequest request) {

        request.setTestName(result.getMethod().getMethodName());

        request.setExecutionTime(result.getEndMillis()
                - result.getStartMillis());
    }

    /**
     * Collects failure details.
     */
    private static void collectFailureInformation(
            final ITestResult result,
            final AIRequest request) {

        Throwable throwable = result.getThrowable();

        if (throwable == null) {
            return;
        }

        request.setExceptionName(
                throwable.getClass().getSimpleName());

        request.setErrorMessage(
                throwable.getMessage());

        request.setStackTrace(
                getStackTrace(throwable));
    }

    /**
     * Collects browser information.
     */
    private static void collectBrowserInformation(
            final AIRequest request) {

        WebDriver driver = DriverManager.getDriver();

        if (driver == null) {

            LOGGER.warn("WebDriver is null.");

            return;
        }

        try {

            request.setCurrentUrl(driver.getCurrentUrl());

            request.setApplicationUrl(driver.getCurrentUrl());

            request.setPageTitle(driver.getTitle());

            request.setBrowser(driver.getClass().getSimpleName());

            if (driver instanceof HasCapabilities hasCapabilities) {

                Capabilities capabilities =
                        hasCapabilities.getCapabilities();

                request.setBrowserVersion(
                        capabilities.getBrowserVersion());

            }

            String pageSource = driver.getPageSource();

            if (pageSource != null) {

                int maxLength = Math.min(5000, pageSource.length());

                request.setDomSnippet(
                        pageSource.substring(0, maxLength));

            }

        } catch (Exception exception) {

            LOGGER.warn(
                    "Unable to collect browser information.",
                    exception);

        }

    }

    /**
     * Collects framework configuration.
     */
    private static void collectFrameworkInformation(
            final AIRequest request) {

        request.setEnvironment(
                CONFIG.getProperty("environment", "QA"));

        request.setRetryCount(0);

        request.addMetadata(
                "Framework",
                "AI Selenium Framework");

        request.addMetadata(
                "ExecutionEngine",
                "TestNG");

        request.addMetadata(
                "JavaVersion",
                System.getProperty("java.version"));

        request.addMetadata(
                "OperatingSystem",
                System.getProperty("os.name"));

    }

    /**
     * Converts stack trace to String.
     */
    private static String getStackTrace(
            final Throwable throwable) {

        StringWriter writer = new StringWriter();

        PrintWriter printWriter = new PrintWriter(writer);

        throwable.printStackTrace(printWriter);

        return writer.toString();

    }

}