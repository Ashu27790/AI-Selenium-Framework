package com.ashutosh.ai.framework.ai.service;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ashutosh.ai.framework.ai.client.AIClient;
import com.ashutosh.ai.framework.ai.client.AIClientFactory;
import com.ashutosh.ai.framework.ai.dto.AIRequest;
import com.ashutosh.ai.framework.ai.dto.AIResponse;
import com.ashutosh.ai.framework.ai.prompt.PromptBuilder;

/**
 * Enterprise service responsible for performing AI-driven
 * Selenium failure analysis.
 *
 * Responsibilities:
 * <ul>
 * <li>Validate AI request</li>
 * <li>Generate enterprise AI prompt</li>
 * <li>Invoke AI provider</li>
 * <li>Measure AI execution time</li>
 * <li>Enrich AI response</li>
 * <li>Gracefully handle AI failures</li>
 * </ul>
 *
 * This service does NOT interact with Selenium WebDriver,
 * Extent Reports or TestNG listeners.
 *
 * @author Ashutosh Kumar Sahu
 * @version 2.1
 */
public class FailureAnalysisService {

    private static final Logger LOGGER =
            LogManager.getLogger(FailureAnalysisService.class);

    /**
     * Executes enterprise AI failure analysis.
     *
     * @param request AI request
     * @return AI response
     */
    public AIResponse analyzeFailure(final AIRequest request) {

        validateRequest(request);

        LOGGER.info("==================================================");
        LOGGER.info("Starting Enterprise AI Failure Analysis");
        LOGGER.info("Test Name        : {}", request.getTestName());
        LOGGER.info("Browser          : {}", request.getBrowser());
        LOGGER.info("Environment      : {}", request.getEnvironment());
        LOGGER.info("Current URL      : {}", request.getCurrentUrl());
        LOGGER.info("==================================================");

        try {

            final long startTime = System.currentTimeMillis();

            // Build Enterprise Prompt
            final String prompt =
                    PromptBuilder.buildFailureAnalysisPrompt(request);

            request.setPrompt(prompt);

            LOGGER.debug("Prompt generated successfully.");
            LOGGER.debug("Prompt Size : {} characters", prompt.length());

            // Get configured AI provider
            final AIClient client = AIClientFactory.getClient();

            LOGGER.info("AI Provider : {}",
                    client.getClass().getSimpleName());

            // Execute AI request
            final AIResponse response = client.execute(request);

            if (response == null) {

                throw new IllegalStateException(
                        "AI Provider returned a null response.");

            }

            final long analysisTime =
                    System.currentTimeMillis() - startTime;

            enrichResponse(response, analysisTime);

            LOGGER.info("AI Analysis completed successfully.");
            LOGGER.info("AI Execution Time : {} ms", analysisTime);
            LOGGER.info("Confidence Score  : {}",
                    response.getConfidenceScore());

            return response;

        } catch (Exception exception) {

            LOGGER.error(
                    "Enterprise AI Failure Analysis failed.",
                    exception);

            return buildFallbackResponse(exception);

        }

    }

    /**
     * Validates AI request.
     *
     * @param request AI request
     */
    private void validateRequest(final AIRequest request) {

        if (request == null) {

            throw new IllegalArgumentException(
                    "AIRequest cannot be null.");

        }

        if (isBlank(request.getTestName())) {

            throw new IllegalArgumentException(
                    "Test name is mandatory.");

        }

        if (isBlank(request.getExceptionName())) {

            throw new IllegalArgumentException(
                    "Exception name is mandatory.");

        }

        if (isBlank(request.getErrorMessage())) {

            LOGGER.warn(
                    "Error message is empty. AI accuracy may decrease.");

        }

        if (isBlank(request.getStackTrace())) {

            LOGGER.warn(
                    "Stack trace is empty. AI accuracy may decrease.");

        }

    }

    /**
     * Enriches AI response with framework information.
     *
     * @param response AI response
     * @param analysisTime execution time
     */
    private void enrichResponse(
            final AIResponse response,
            final long analysisTime) {

        response.setAnalysisTime(analysisTime);
        response.setAnalysisTimestamp(LocalDateTime.now());

        if (response.getConfidenceScore() < 0
                || response.getConfidenceScore() > 100) {

            LOGGER.warn(
                    "Invalid confidence score received : {}",
                    response.getConfidenceScore());

            response.setConfidenceScore(50);

        }

    }

    /**
     * Builds fallback response when AI provider fails.
     *
     * @param exception Exception
     * @return fallback response
     */
    private AIResponse buildFallbackResponse(
            final Exception exception) {

        AIResponse response = new AIResponse();

        response.setSuccess(false);

        response.setExecutiveSummary(
                "AI Failure Analysis could not be completed.");

        response.setFailureCategory(
                "AI Service Failure");

        response.setSeverity(
                "LOW");

        response.setRootCause(
                exception.getMessage());

        response.setBusinessImpact(
                "AI analysis is unavailable. Standard Selenium reporting remains available.");

        response.setSuggestedSolution(
                "Verify AI provider availability and retry the analysis.");

        response.setRetryRecommendation(
                "Retry after validating API connectivity.");

        response.setLearningSummary(
                "Framework handled the AI service exception gracefully.");

        response.setConfidenceScore(0);

        response.setAnalysisTime(0);

        response.setAnalysisTimestamp(LocalDateTime.now());

        return response;

    }

    /**
     * Returns true if string is null or blank.
     *
     * @param value input value
     * @return true if blank
     */
    private boolean isBlank(final String value) {

        return value == null || value.isBlank();

    }

}