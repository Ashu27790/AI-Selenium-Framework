package com.ashutosh.ai.framework.ai.client;

import com.ashutosh.ai.framework.ai.dto.AIRequest;
import com.ashutosh.ai.framework.ai.dto.AIResponse;
import com.ashutosh.ai.framework.ai.exception.AIException;

/**
 * Generic contract for all AI providers.
 *
 * Every AI implementation must implement this interface.
 *
 * Examples:
 * - OpenAI
 * - Ollama
 * - Gemini
 * - Azure OpenAI
 * - Custom Python AI Model
 *
 * @author Ashutosh Kumar Sahu
 * @since 1.0
 */
public interface AIClient {

    /**
     * Sends a request to an AI provider and returns the response.
     *
     * @param request AI request
     * @return AI response
     * @throws AIException if AI processing fails
     */
    AIResponse execute(AIRequest request) throws AIException;

}