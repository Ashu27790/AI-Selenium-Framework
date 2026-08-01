package com.ashutosh.ai.framework.ai.config;

import com.ashutosh.ai.framework.ai.enums.AIProvider;
import com.ashutosh.ai.framework.config.manager.ConfigurationManager;

/**
 * Provides centralized access to AI configuration properties.
 *
 * @author Ashutosh Kumar Sahu
 * @since 1.0
 */
public final class AIConfiguration {

    private static final AIConfiguration INSTANCE = new AIConfiguration();

    private static final String AI_ENABLED = "ai.enabled";
    private static final String AI_PROVIDER = "ai.provider";
    private static final String AI_MODEL = "ai.model";
    private static final String AI_API_KEY = "ai.api.key";
    private static final String AI_BASE_URL = "ai.base.url";
    private static final String AI_TEMPERATURE = "ai.temperature";
    private static final String AI_MAX_TOKENS = "ai.max.tokens";
    private static final String AI_TIMEOUT = "ai.timeout";

    private final ConfigurationManager configurationManager;

    private AIConfiguration() {
        configurationManager = ConfigurationManager.getInstance();
    }
    /**
     * Returns configured AI provider.
     *
     * @return provider name
     */
    public String getProvider() {
        return configurationManager.getProperty(AI_PROVIDER, "OPENAI");
    }
    public static AIConfiguration getInstance() {
        return INSTANCE;
    }

    public boolean isAIEnabled() {
        return configurationManager.getBooleanProperty(AI_ENABLED, true);
    }

    public String getModel() {
        return configurationManager.getProperty(AI_MODEL, "gpt-4-nano");
    }

    public String getApiKey() {
        return configurationManager.getProperty(AI_API_KEY);
    }

    public double getTemperature() {
        return configurationManager.getDoubleProperty(AI_TEMPERATURE, 0.1);
    }

    public int getMaxTokens() {
        return configurationManager.getIntProperty(AI_MAX_TOKENS, 500);
    }

    
}