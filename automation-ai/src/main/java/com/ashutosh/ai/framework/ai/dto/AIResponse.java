package com.ashutosh.ai.framework.ai.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
/**
 * Represents the response returned by the AI provider.
 *
 * @author Ashutosh Kumar Sahu
 * @since 1.0
 */
public class AIResponse implements Serializable {
	private String executiveSummary;
	private String failureCategory;
	private String severity;
	private String businessImpact;
	private String retryRecommendation;
	private String learningSummary;
	private long analysisTime;
	private LocalDateTime analysisTimestamp;
    private static final long serialVersionUID = 1L;

    /**
     * Indicates whether the AI request was successful.
     */
    private boolean success;

    /**
     * Complete AI response returned by the model.
     */
    private String response;

    /**
     * Root cause of the failure.
     */
    private String rootCause;

    /**
     * Most probable reasons for the failure.
     */
    private String possibleReason;

    /**
     * AI suggested solution.
     */
    private String suggestedSolution;

    /**
     * Selenium automation best practices.
     */
    private String bestPractices;

    /**
     * Confidence score (0-100).
     */
    private int confidenceScore;

    /**
     * Optional metadata.
     */
    private Map<String, String> metadata = new HashMap<>();

    public AIResponse() {

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(final String response) {
        this.response = response;
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

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(final Map<String, String> metadata) {
        this.metadata = metadata;
    }
    public void addMetadata(
            final String key,
            final String value) {

        metadata.put(key, value);

    }
    public String getMetadataValue(
            final String key) {

        return metadata.get(key);

    }


    @Override
    public String toString() {

        return "AIResponse{" +
                "success=" + success +
                ", rootCause='" + rootCause + '\'' +
                ", possibleReason='" + possibleReason + '\'' +
                ", suggestedSolution='" + suggestedSolution + '\'' +
                ", bestPractices='" + bestPractices + '\'' +
                ", confidenceScore=" + confidenceScore +
                ", metadata=" + metadata +
                '}';
    }
    
    public String getExecutiveSummary() {
        return executiveSummary;
    }

    public void setExecutiveSummary(final String executiveSummary) {
        this.executiveSummary = executiveSummary;
    }
    public String getFailureCategory() {
        return failureCategory;
    }

    public void setFailureCategory(final String failureCategory) {
        this.failureCategory = failureCategory;
    }
    
    public String getSeverity() {
        return severity;
    }

    public void setSeverity(final String severity) {
        this.severity = severity;
    }
    
    public String getBusinessImpact() {
        return businessImpact;
    }

    public void setBusinessImpact(final String businessImpact) {
        this.businessImpact = businessImpact;
    }
    
    public String getRetryRecommendation() {
        return retryRecommendation;
    }

    public void setRetryRecommendation(final String retryRecommendation) {
        this.retryRecommendation = retryRecommendation;
    }
    
    public String getLearningSummary() {
        return learningSummary;
    }

    public void setLearningSummary(final String learningSummary) {
        this.learningSummary = learningSummary;
    }
    
    public long getAnalysisTime() {
        return analysisTime;
    }

    public void setAnalysisTime(final long analysisTime) {
        this.analysisTime = analysisTime;
    }
    
    public LocalDateTime getAnalysisTimestamp() {
        return analysisTimestamp;
    }

    public void setAnalysisTimestamp(
            final LocalDateTime analysisTimestamp) {
        this.analysisTimestamp = analysisTimestamp;
    }
}