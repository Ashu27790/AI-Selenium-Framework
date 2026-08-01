package com.ashutosh.ai.framework.ai.client;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ashutosh.ai.framework.ai.config.AIConfiguration;
import com.ashutosh.ai.framework.ai.dto.AIRequest;
import com.ashutosh.ai.framework.ai.dto.AIResponse;
import com.ashutosh.ai.framework.ai.exception.AIException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

/**
 * Enterprise OpenAI client implementation.
 *
 * Responsibilities:
 * <ul>
 * <li>Invoke OpenAI</li>
 * <li>Parse JSON response</li>
 * <li>Validate response</li>
 * <li>Enrich metadata</li>
 * <li>Handle fallback scenarios</li>
 * </ul>
 *
 * @author Ashutosh Kumar Sahu
 * @version 2.0
 */
public class OpenAIClientImpl implements AIClient {

    private static final Logger LOGGER =
            LogManager.getLogger(OpenAIClientImpl.class);

    private final OpenAIClient client;
    private final AIConfiguration configuration;
    private final ObjectMapper objectMapper;

    public OpenAIClientImpl() {

        configuration = AIConfiguration.getInstance();

        client = OpenAIOkHttpClient.builder()
                .apiKey(configuration.getApiKey())
                .build();

        objectMapper = new ObjectMapper();
    }

    @Override
    public AIResponse execute(final AIRequest request)
            throws AIException {

        LOGGER.info("==========================================");
        LOGGER.info("Sending request to OpenAI");
        LOGGER.info("Model : {}", configuration.getModel());
        LOGGER.info("Test  : {}", request.getTestName());
        LOGGER.info("==========================================");

        long startTime = System.currentTimeMillis();

        try {

            ChatCompletionCreateParams params =
                    ChatCompletionCreateParams.builder()
                            .model(ChatModel.of(configuration.getModel()))
                            .addUserMessage(request.getPrompt())
                            .build();

            ChatCompletion completion =
                    client.chat()
                            .completions()
                            .create(params);

            long elapsedTime =
                    System.currentTimeMillis() - startTime;

            String rawResponse =
                    completion.choices()
                            .get(0)
                            .message()
                            .content()
                            .orElse("");

            LOGGER.info("==========================================");
            LOGGER.info("RAW AI RESPONSE");
            LOGGER.info("==========================================");
            LOGGER.info("\n{}", rawResponse);

            String cleanedResponse = cleanJson(rawResponse);

            LOGGER.info("==========================================");
            LOGGER.info("CLEANED AI RESPONSE");
            LOGGER.info("==========================================");
            LOGGER.info("\n{}", cleanedResponse);

            AIResponse response;

            try {

                response = objectMapper.readValue(
                        cleanedResponse,
                        AIResponse.class);

                LOGGER.info("AI JSON parsed successfully.");

            } catch (com.fasterxml.jackson.core.JsonProcessingException ex) {

                LOGGER.error("==========================================");
                LOGGER.error("AI JSON PARSING FAILED");
                LOGGER.error("==========================================");
                LOGGER.error("Exception Type : {}", ex.getClass().getSimpleName());
                LOGGER.error("Reason         : {}", ex.getOriginalMessage());
                LOGGER.error("Location       : {}", ex.getLocation());
                LOGGER.error("==========================================");
                LOGGER.error("RAW JSON");
                LOGGER.error("{}", cleanedResponse);
                LOGGER.error("==========================================", ex);

                response = buildFallbackResponse(cleanedResponse);

                response.addMetadata(
                        "ParsingError",
                        ex.getOriginalMessage());

                response.addMetadata(
                        "ExceptionType",
                        ex.getClass().getSimpleName());
            }

            enrichResponse(response, elapsedTime);

            validateResponse(response);

            LOGGER.info("==========================================");
            LOGGER.info("OpenAI analysis completed successfully.");
            LOGGER.info("==========================================");

            return response;

        } catch (Exception exception) {

            LOGGER.error("==========================================");
            LOGGER.error("OPENAI INVOCATION FAILED");
            LOGGER.error("==========================================", exception);

            throw new AIException(
                    "Unable to communicate with OpenAI.",
                    exception);
        }
    }

    /**
     * Removes markdown wrappers.
     */
    private String cleanJson(final String response) {

        String value = response.trim();

        value = value.replace("```json", "");
        value = value.replace("```", "");
        value = value.replace("JSON", "");
        value = value.replace("Response:", "");
        value = value.replace("Answer:", "");

        int firstBrace = value.indexOf('{');
        int lastBrace = value.lastIndexOf('}');

        if (firstBrace >= 0 && lastBrace > firstBrace) {
            value = value.substring(firstBrace, lastBrace + 1);
        }

        return value.trim();
    }

    /**
     * Creates fallback response.
     */
    private AIResponse buildFallbackResponse(
            final String rawResponse) {

        AIResponse response =
                new AIResponse();

        response.setSuccess(true);

        response.setExecutiveSummary(
                "AI returned a valid response, but the framework could not deserialize it into AIResponse.");

        response.setFailureCategory(
                "Response Parsing");

        response.setSeverity("LOW");

        response.setRootCause(
                "Jackson JSON deserialization failed. Check parser logs for the exact field causing the failure.");

        response.setSuggestedSolution(
                "Verify PromptBuilder returns valid JSON only.");

        response.setResponse(rawResponse);

        response.setConfidenceScore(0);

        return response;

    }

    /**
     * Enriches AI response.
     */
    private void enrichResponse(
            final AIResponse response,
            final long executionTime) {

        response.setAnalysisTime(
                executionTime);

        response.setAnalysisTimestamp(
                LocalDateTime.now());

        response.addMetadata(
                "Provider",
                "OpenAI");

        response.addMetadata(
                "Model",
                configuration.getModel());

    }

    /**
     * Validates response.
     */
    private void validateResponse(
            final AIResponse response) {

        if (response.getConfidenceScore() < 0
                || response.getConfidenceScore() > 100) {

            LOGGER.warn(
                    "Invalid confidence score : {}",
                    response.getConfidenceScore());

            response.setConfidenceScore(50);

        }

        if (response.getExecutiveSummary() == null) {

            response.setExecutiveSummary(
                    "No executive summary generated.");

        }

    }

}