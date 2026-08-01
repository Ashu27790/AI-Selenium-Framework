package com.ashutosh.ai.framework.report.manager;

import com.ashutosh.ai.framework.report.model.AIReportModel;

/**
 * Responsible for writing AI analysis into Extent Report.
 *
 * <p>
 * This class receives a fully populated {@link AIReportModel} and renders
 * the AI analysis in the Extent Report.
 * </p>
 *
 * @author Ashutosh Kumar Sahu
 * @since 1.0
 */
public final class AIReportManager {

    /**
     * Private constructor.
     */
    private AIReportManager() {

    }

    /**
     * Writes AI Failure Analysis into Extent Report.
     *
     * @param reportModel AI Report Model
     */
    public static void addAIAnalysis(final AIReportModel reportModel) {

        if (reportModel == null) {
            return;
        }

        ExtentTestManager.logHtml(
                "<hr><h2 style='color:#1565C0'>🤖 AI Failure Analysis</h2>");

        //==========================================================
        // Test Information
        //==========================================================

        ExtentTestManager.info(
                "<b>Test Name :</b> "
                        + safe(reportModel.getTestName()));

        ExtentTestManager.info(
                "<b>Test Status :</b> "
                        + safe(reportModel.getTestStatus()));

        ExtentTestManager.info(
                "<b>Browser :</b> "
                        + safe(reportModel.getBrowser()));

        ExtentTestManager.info(
                "<b>Application URL :</b> "
                        + safe(reportModel.getApplicationUrl()));

        ExtentTestManager.info(
                "<b>Execution Time :</b> "
                        + safe(reportModel.getExecutionTime()));

        ExtentTestManager.info(
                "<b>Exception :</b> "
                        + safe(reportModel.getExceptionName()));

        //==========================================================
        // AI Analysis
        //==========================================================

        ExtentTestManager.logAISection(
                "Root Cause",
                safe(reportModel.getRootCause()));

        ExtentTestManager.logAISection(
                "Possible Reason",
                safe(reportModel.getPossibleReason()));

        ExtentTestManager.logAISection(
                "Suggested Solution",
                safe(reportModel.getSuggestedSolution()));

        ExtentTestManager.logAISection(
                "Best Practices",
                safe(reportModel.getBestPractices()));

        ExtentTestManager.info(
                "<b>Confidence Score :</b> "
                        + reportModel.getConfidenceScore()
                        + "%");

        //==========================================================
        // Screenshot
        //==========================================================

        if (reportModel.getScreenshotPath() != null
                && !reportModel.getScreenshotPath().isBlank()) {

            ExtentTestManager.attachScreenshot(
                    reportModel.getScreenshotPath());
        }

        //==========================================================
        // Complete AI Response
        //==========================================================

        ExtentTestManager.logCodeBlock(
                "Complete AI Response",
                safe(reportModel.getRawAIResponse()));

        ExtentTestManager.logHtml("<hr>");
    }

    /**
     * Returns safe String.
     *
     * @param value String
     * @return Safe String
     */
    private static String safe(final String value) {

        if (value == null || value.isBlank()) {
            return "Not Available";
        }

        return value;
    }

}