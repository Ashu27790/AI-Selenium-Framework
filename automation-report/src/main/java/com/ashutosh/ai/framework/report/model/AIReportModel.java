package com.ashutosh.ai.framework.report.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import com.ashutosh.ai.framework.ai.dto.AIRequest;
import com.ashutosh.ai.framework.ai.dto.AIResponse;

/**
 * Model representing AI Failure Analysis Report.
 *
 * <p>
 * This model acts as a bridge between the AI Engine and the Reporting module.
 * It contains all information required to generate Extent Report,
 * HTML Report, PDF Report, Email Report, Dashboard, etc.
 * </p>
 *
 * @author Ashutosh Kumar Sahu
 * @since 1.0
 */
public class AIReportModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Test Name.
     */
    private String testName;

    /**
     * Test Status.
     */
    private String testStatus;

    /**
     * Browser Name.
     */
    private String browser;

    /**
     * Application URL.
     */
    private String applicationUrl;

    /**
     * Test execution time.
     */
    private String executionTime;

    /**
     * Exception Name.
     */
    private String exceptionName;

    /**
     * Root Cause identified by AI.
     */
    private String rootCause;

    /**
     * Possible reason for failure.
     */
    private String possibleReason;

    /**
     * Suggested solution.
     */
    private String suggestedSolution;

    /**
     * Selenium / Automation best practices.
     */
    private String bestPractices;

    /**
     * AI confidence score.
     */
    private int confidenceScore;

    /**
     * Screenshot location.
     */
    private String screenshotPath;

    /**
     * Complete AI response.
     */
    private String rawAIResponse;

    /**
     * Default Constructor.
     */
    public AIReportModel() {

    }

    /**
     * Full Constructor.
     */
    public AIReportModel(
            final String testName,
            final String testStatus,
            final String browser,
            final String applicationUrl,
            final String executionTime,
            final String exceptionName,
            final String rootCause,
            final String possibleReason,
            final String suggestedSolution,
            final String bestPractices,
            final int confidenceScore,
            final String screenshotPath,
            final String rawAIResponse) {

        this.testName = testName;
        this.testStatus = testStatus;
        this.browser = browser;
        this.applicationUrl = applicationUrl;
        this.executionTime = executionTime;
        this.exceptionName = exceptionName;
        this.rootCause = rootCause;
        this.possibleReason = possibleReason;
        this.suggestedSolution = suggestedSolution;
        this.bestPractices = bestPractices;
        this.confidenceScore = confidenceScore;
        this.screenshotPath = screenshotPath;
        this.rawAIResponse = rawAIResponse;
    }

    /**
     * Creates AIReportModel from AIRequest and AIResponse.
     *
     * @param request AI Request
     * @param response AI Response
     * @return AIReportModel
     */
    public static AIReportModel from(
            final AIRequest request,
            final AIResponse response) {

        AIReportModel model = new AIReportModel();

        if (request != null) {

            model.setTestName(request.getTestName());
            model.setBrowser(request.getBrowser());
            model.setApplicationUrl(request.getApplicationUrl());
            model.setExceptionName(request.getExceptionName());

            // Uncomment when available
            // model.setExecutionTime(request.getExecutionTime());
        }

        if (response != null) {

            model.setRootCause(response.getRootCause());
            model.setPossibleReason(response.getPossibleReason());
            model.setSuggestedSolution(response.getSuggestedSolution());
            model.setBestPractices(response.getBestPractices());
            model.setConfidenceScore(response.getConfidenceScore());
            model.setRawAIResponse(response.getResponse());
        }

        return model;
    }

    /**
     * Builder method.
     *
     * @param executionTime execution time
     * @return current object
     */
    public AIReportModel withExecutionTime(
            final String executionTime) {

        this.executionTime = executionTime;
        return this;
    }

    /**
     * Builder method.
     *
     * @param screenshotPath screenshot path
     * @return current object
     */
    public AIReportModel withScreenshotPath(
            final String screenshotPath) {

        this.screenshotPath = screenshotPath;
        return this;
    }

    /**
     * Builder method.
     *
     * @param testStatus status
     * @return current object
     */
    public AIReportModel withTestStatus(
            final String testStatus) {

        this.testStatus = testStatus;
        return this;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(final String testName) {
        this.testName = testName;
    }

    public String getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(final String testStatus) {
        this.testStatus = testStatus;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(final String browser) {
        this.browser = browser;
    }

    public String getApplicationUrl() {
        return applicationUrl;
    }

    public void setApplicationUrl(final String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(final String executionTime) {
        this.executionTime = executionTime;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(final String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public String getRootCause() {
        return rootCause;
    }

    public void setRootCause(final String rootCause) {
        this.rootCause = rootCause;
    }

    public String getPossibleReason() {
        return possibleReason;
    }

    public void setPossibleReason(final String possibleReason) {
        this.possibleReason = possibleReason;
    }

    public String getSuggestedSolution() {
        return suggestedSolution;
    }

    public void setSuggestedSolution(final String suggestedSolution) {
        this.suggestedSolution = suggestedSolution;
    }

    public String getBestPractices() {
        return bestPractices;
    }

    public void setBestPractices(final String bestPractices) {
        this.bestPractices = bestPractices;
    }

    public int getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(final int confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public String getScreenshotPath() {
        return screenshotPath;
    }

    public void setScreenshotPath(final String screenshotPath) {
        this.screenshotPath = screenshotPath;
    }

    public String getRawAIResponse() {
        return rawAIResponse;
    }

    public void setRawAIResponse(final String rawAIResponse) {
        this.rawAIResponse = rawAIResponse;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                testName,
                testStatus,
                browser,
                applicationUrl,
                executionTime,
                exceptionName,
                rootCause,
                possibleReason,
                suggestedSolution,
                bestPractices,
                confidenceScore,
                screenshotPath,
                rawAIResponse);
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof AIReportModel other)) {
            return false;
        }

        return confidenceScore == other.confidenceScore
                && Objects.equals(testName, other.testName)
                && Objects.equals(testStatus, other.testStatus)
                && Objects.equals(browser, other.browser)
                && Objects.equals(applicationUrl, other.applicationUrl)
                && Objects.equals(executionTime, other.executionTime)
                && Objects.equals(exceptionName, other.exceptionName)
                && Objects.equals(rootCause, other.rootCause)
                && Objects.equals(possibleReason, other.possibleReason)
                && Objects.equals(suggestedSolution, other.suggestedSolution)
                && Objects.equals(bestPractices, other.bestPractices)
                && Objects.equals(screenshotPath, other.screenshotPath)
                && Objects.equals(rawAIResponse, other.rawAIResponse);
    }

    @Override
    public String toString() {

        return "AIReportModel{" +
                "testName='" + testName + '\'' +
                ", testStatus='" + testStatus + '\'' +
                ", browser='" + browser + '\'' +
                ", applicationUrl='" + applicationUrl + '\'' +
                ", executionTime='" + executionTime + '\'' +
                ", exceptionName='" + exceptionName + '\'' +
                ", rootCause='" + rootCause + '\'' +
                ", possibleReason='" + possibleReason + '\'' +
                ", suggestedSolution='" + suggestedSolution + '\'' +
                ", bestPractices='" + bestPractices + '\'' +
                ", confidenceScore=" + confidenceScore +
                ", screenshotPath='" + screenshotPath + '\'' +
                ", rawAIResponse='" + rawAIResponse + '\'' +
                '}';
    }
}