package com.ashutosh.ai.framework.ai.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a generic AI request sent from the automation framework.
 *
 * This DTO is provider-independent and can be used with
 * OpenAI, Gemini, Ollama, Azure OpenAI or any future AI provider.
 *
 * @author Ashutosh Kumar Sahu
 * @since 1.0
 */
public class AIRequest implements Serializable {

    private static final long serialVersionUID = 1L;
 // Prompt
    private String prompt;
 // Test Details
    private String testName;
 // Browser
    private String browser;
    private String environment;
    private String browserVersion;
 // Application
    private String applicationUrl;
    private String currentUrl;
    private String pageTitle;
 // Failure
    private String exceptionName;
    private String errorMessage;
    private String stackTrace;
    private String locator;
    
 // Evidence
    private String screenshotPath;
    private String domSnippet;
 // Execution
    private long executionTime;
    private int retryCount;

    /**
     * Additional provider-specific metadata.
     */
    private final Map<String, String> metadata =
            new HashMap<>();

    public AIRequest() {

    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(final String prompt) {
        this.prompt = prompt;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(final String testName) {
        this.testName = testName;
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

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(final String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(final String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * Adds metadata entry.
     *
     * @param key metadata key
     * @param value metadata value
     */
    public void addMetadata(final String key, final String value) {
        metadata.put(key, value);
    }

    /**
     * Returns metadata value.
     *
     * @param key metadata key
     * @return metadata value
     */
    public String getMetadataValue(final String key) {
        return metadata.get(key);
    }

    @Override
    public String toString() {

        return "AIRequest{" +
                "testName='" + testName + '\'' +
                ", browser='" + browser + '\'' +
                ", browserVersion='" + browserVersion + '\'' +
                ", environment='" + environment + '\'' +
                ", applicationUrl='" + applicationUrl + '\'' +
                ", currentUrl='" + currentUrl + '\'' +
                ", pageTitle='" + pageTitle + '\'' +
                ", exceptionName='" + exceptionName + '\'' +
                ", locator='" + locator + '\'' +
                ", screenshotPath='" + screenshotPath + '\'' +
                ", executionTime=" + executionTime +
                ", retryCount=" + retryCount +
                '}';
    }
    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(final String currentUrl) {
        this.currentUrl = currentUrl;
    }
    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(final String pageTitle) {
        this.pageTitle = pageTitle;
    }
    
    public String getScreenshotPath() {
        return screenshotPath;
    }

    public void setScreenshotPath(final String screenshotPath) {
        this.screenshotPath = screenshotPath;
    }
    /**
     * Returns DOM snippet captured at failure time.
     *
     * @return DOM snippet
     */
    public String getDomSnippet() {
        return domSnippet;
    }

    /**
     * Sets DOM snippet captured at failure time.
     *
     * @param domSnippet DOM snippet
     */
    public void setDomSnippet(final String domSnippet) {
        this.domSnippet = domSnippet;
    }

    /**
     * Returns locator used during test execution.
     *
     * @return locator
     */
    public String getLocator() {
        return locator;
    }

    /**
     * Sets locator used during test execution.
     *
     * @param locator locator
     */
    public void setLocator(final String locator) {
        this.locator = locator;
    }

    /**
     * Returns test execution time.
     *
     * @return execution time in milliseconds
     */
    public long getExecutionTime() {
        return executionTime;
    }

    /**
     * Sets test execution time.
     *
     * @param executionTime execution time in milliseconds
     */
    public void setExecutionTime(final long executionTime) {
        this.executionTime = executionTime;
    }

    /**
     * Returns browser version.
     *
     * @return browser version
     */
    public String getBrowserVersion() {
        return browserVersion;
    }

    /**
     * Sets browser version.
     *
     * @param browserVersion browser version
     */
    public void setBrowserVersion(final String browserVersion) {
        this.browserVersion = browserVersion;
    }

    /**
     * Returns execution environment.
     *
     * @return environment
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     * Sets execution environment.
     *
     * @param environment execution environment
     */
    public void setEnvironment(final String environment) {
        this.environment = environment;
    }

    /**
     * Returns retry count.
     *
     * @return retry count
     */
    public int getRetryCount() {
        return retryCount;
    }

    /**
     * Sets retry count.
     *
     * @param retryCount retry count
     */
    public void setRetryCount(final int retryCount) {
        this.retryCount = retryCount;
    }
}