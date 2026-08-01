package com.ashutosh.ai.framework.ai.enums;

/**
 * Supported AI models.
 *
 * @author Ashutosh Kumar Sahu
 * @since 1.0
 */
public enum AIModel {

    GPT_5_5("gpt-5.5"),
    GPT_4_1("gpt-4.1"),
    GPT_4O("gpt-4o"),
    GEMINI_2_5_PRO("gemini-2.5-pro"),
    LLAMA3("llama3");

    private final String modelName;

    AIModel(String modelName) {
        this.modelName = modelName;
    }

    public String getModelName() {
        return modelName;
    }
}