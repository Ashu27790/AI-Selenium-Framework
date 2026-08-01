package com.ashutosh.ai.framework.report.manager;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

/**
 * Manages ExtentTest instances for parallel execution.
 *
 * This class stores one ExtentTest instance per executing thread
 * using ThreadLocal to ensure thread safety.
 *
 * Responsibilities:
 * - Create ExtentTest
 * - Return current thread's ExtentTest
 * - Log messages
 * - Attach screenshots
 * - Remove ExtentTest
 *
 * @author Ashutosh Kumar Sahu
 * @since 1.0
 */
public final class ExtentTestManager {

    private static final Logger LOGGER =
            LogManager.getLogger(ExtentTestManager.class);

    private static final ThreadLocal<ExtentTest> EXTENT_TEST =
            new ThreadLocal<>();

    private ExtentTestManager() {

    }

    /**
     * Creates ExtentTest.
     *
     * @param testName Test Name
     */
    public static void createTest(final String testName) {

        LOGGER.info("Creating ExtentTest : {}", testName);

        ExtentTest test =
                ExtentReportManager
                        .getInstance()
                        .getExtentReports()
                        .createTest(testName);

        EXTENT_TEST.set(test);
    }

    /**
     * Creates ExtentTest with description.
     *
     * @param testName Test Name
     * @param description Test Description
     */
    public static void createTest(
            final String testName,
            final String description) {

        LOGGER.info("Creating ExtentTest : {}", testName);

        ExtentTest test =
                ExtentReportManager
                        .getInstance()
                        .getExtentReports()
                        .createTest(testName, description);

        EXTENT_TEST.set(test);
    }

    /**
     * Returns current thread ExtentTest.
     *
     * @return ExtentTest
     */
    public static ExtentTest getTest() {

        return Objects.requireNonNull(
                EXTENT_TEST.get(),
                "ExtentTest has not been initialized. "
                        + "Call createTest() first.");
    }

    /*-------------------------------------------------------
     * Generic Logging Methods
     *-------------------------------------------------------*/

    public static void info(final String message) {
        getTest().log(Status.INFO, message);
    }

    public static void pass(final String message) {
        getTest().log(Status.PASS, message);
    }

    public static void fail(final String message) {
        getTest().log(Status.FAIL, message);
    }

    public static void warning(final String message) {
        getTest().log(Status.WARNING, message);
    }

    public static void skip(final String message) {
        getTest().log(Status.SKIP, message);
    }

    /**
     * Logs exception.
     *
     * @param throwable Exception
     */
    public static void fail(final Throwable throwable) {
        getTest().fail(throwable);
    }

    /**
     * Logs HTML.
     *
     * @param html HTML Content
     */
    public static void logHtml(final String html) {
        getTest().log(Status.INFO, html);
    }

    /**
     * Logs code block.
     *
     * @param title Title
     * @param code Code
     */
    public static void logCodeBlock(
            final String title,
            final String code) {

        String html =
                "<b>" + title + "</b>"
                        + "<pre>"
                        + code
                        + "</pre>";

        getTest().log(Status.INFO, html);
    }

    /**
     * Logs AI section.
     *
     * @param heading Heading
     * @param value Value
     */
    public static void logAISection(
            final String heading,
            final String value) {

        String html =
                "<div style='border-left:4px solid #1976D2;"
                        + "padding:8px;"
                        + "margin:5px;'>"
                        + "<b>"
                        + heading
                        + "</b><br>"
                        + value
                        + "</div>";

        getTest().log(Status.INFO, html);
    }

    /**
     * Attaches screenshot.
     *
     * @param screenshotPath Screenshot path
     */
    public static void attachScreenshot(final String screenshotPath) {
        try {
        	LOGGER.info("Screenshot Path Received : {}", screenshotPath);
            if (screenshotPath == null|| screenshotPath.isBlank()) {
                return;
            }
            getTest().fail(
                    "Failure Screenshot",
                    MediaEntityBuilder
                            .createScreenCaptureFromPath(
                                    screenshotPath)
                            .build());
        } catch (Exception ex) {
            LOGGER.error("Unable to attach screenshot.",ex);
        }
    }
    /**
     * Logs AI Executive Summary.
     *
     * @param summary executive summary
     */
    public static void logExecutiveSummary(final String summary) {
        if (summary == null || summary.isBlank()) {
            return;
        }
        logHtml("""
            <div style='
                background:#F5F9FF;
                border-left:6px solid #1976D2;
                padding:12px;
                margin-top:10px;
                border-radius:5px;'>
            <h3>🤖 Executive Summary</h3>
            %s
            </div>
            """.formatted(summary));
    }
    /**
     * Logs severity badge.
     *
     * @param severity severity
     */
    public static void logSeverity(final String severity) {
        if (severity == null) {
            return;
        }
        String color;
        switch (severity.toUpperCase()) {
            case "CRITICAL":
                color = "#B71C1C";
                break;
            case "HIGH":
                color = "#E53935";
                break;
            case "MEDIUM":
                color = "#FB8C00";
                break;
            default:
                color = "#43A047";
        }
        logHtml("""
            <span style='
            background:%s;
            color:white;
            padding:5px 10px;
            border-radius:5px;
            font-weight:bold;'>
            Severity : %s
            </span>
            """.formatted(color, severity));
    }
    /**
     * Logs confidence score.
     *
     * @param confidence confidence
     */
    public static void logConfidenceScore(final int confidence) {
        logHtml("""
            <b>AI Confidence Score</b>
            <div style='
                width:300px;
                border:1px solid #DDD;
                margin-top:5px;'>
            <div style='
                width:%d%%;
                background:#4CAF50;
                color:white;
                padding:4px;'>
            %d%%
            </div>
            </div>
            """.formatted(confidence, confidence));
    }
    /**
     * Logs AI metrics.
     *
     * @param executionTime execution time
     * @param analysisTime AI analysis time
     */
    public static void logAiMetrics(
            final long executionTime,
            final long analysisTime) {
        logHtml("""
            <table border='1'
                   cellpadding='6'
                   cellspacing='0'
                   style='border-collapse:collapse;'>
            <tr>
                <th>Metric</th>
                <th>Value</th>
            </tr>
            <tr>
                <td>Test Execution</td>
                <td>%d ms</td>
            </tr>
            <tr>
                <td>AI Analysis</td>
                <td>%d ms</td>
            </tr>
            </table>
            """.formatted(
                    executionTime,
                    analysisTime));
    }
    /**
     * Logs AI information card.
     *
     * @param title title
     * @param value value
     */
    public static void logAiCard(
            final String title,
            final String value) {
        if (value == null || value.isBlank()) {
            return;
        }
        logHtml("""
            <div style='
            border-left:5px solid #2196F3;
            background:#FAFAFA;
            padding:10px;
            margin-top:8px;
            border-radius:5px;'>
            <b>%s</b>
            <br><br>
            %s
            </div>
            """.formatted(title, value));
    }
    /**
     * Logs raw AI response.
     *
     * @param json raw AI response
     */
    public static void logRawResponse(final String json) {
        if (json == null || json.isBlank()) {
            return;
        }
        logHtml("""
            <details>
            <summary>
            <b>Complete AI JSON Response</b>
            </summary>
            <pre>
            %s
            </pre>
            </details>
            """.formatted(json));
    }

    /**
     * Removes current thread ExtentTest.
     */
    public static void removeTest() {

        LOGGER.debug(
                "Removing ExtentTest from current thread.");

        EXTENT_TEST.remove();
    }

}