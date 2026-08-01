package com.ashutosh.ai.framework.ai.client;

import com.ashutosh.ai.framework.ai.config.AIConfiguration;
import com.ashutosh.ai.framework.ai.enums.AIProvider;
import com.ashutosh.ai.framework.ai.exception.AIException;

/**
 * Factory class for creating AI client implementations.
 *
 * Supports:
 * - OpenAI
 * - Gemini
 * - Ollama
 * - Azure OpenAI
 *
 * @author Ashutosh Kumar Sahu
 * @since 1.0
 */
public final class AIClientFactory {

    private static final AIConfiguration CONFIGURATION =
            AIConfiguration.getInstance();

    private AIClientFactory() {
        // Prevent instantiation
    }

    /**
     * Returns the configured AI provider implementation.
     *
     * @return AIClient implementation
     */
    public static AIClient getClient() {

        AIProvider provider = AIProvider.valueOf(
                CONFIGURATION.getProvider().toUpperCase());

        switch (provider) {

            case OPENAI:
                return new OpenAIClientImpl();

            case GEMINI:
                // return new GeminiClientImpl();
                throw new AIException("Gemini client not implemented.");

            case OLLAMA:
                // return new OllamaClientImpl();
                throw new AIException("Ollama client not implemented.");

            case AZURE_OPENAI:
                // return new AzureOpenAIClientImpl();
                throw new AIException("Azure OpenAI client not implemented.");

            default:
                throw new AIException(
                        "Unsupported AI Provider : " + provider);
        }
    }

}