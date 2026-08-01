package com.ashutosh.ai.framework.ai.utils;

public class PromptOptimizer {

    private static final int MAX_STACKTRACE_LINES = 10;
    private static final int MAX_MESSAGE_LENGTH = 300;
    private static final int MAX_PROMPT_LENGTH = 1000;

    public String optimizeStackTrace(String stackTrace) {

        if (stackTrace == null || stackTrace.isBlank()) {
            return "";
        }

        String[] lines = stackTrace.split("\\R");
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < Math.min(lines.length, MAX_STACKTRACE_LINES); i++) {
            builder.append(lines[i]).append(System.lineSeparator());
        }

        return builder.toString();
    }

    public String optimizeMessage(String message) {

        if (message == null || message.isBlank()) {
            return "";
        }

        if (message.length() <= MAX_MESSAGE_LENGTH) {
            return message;
        }

        return message.substring(0, MAX_MESSAGE_LENGTH) + "...";
    }

    public String optimizePrompt(String prompt) {

        if (prompt == null || prompt.isBlank()) {
            return "";
        }

        if (prompt.length() <= MAX_PROMPT_LENGTH) {
            return prompt;
        }

        return prompt.substring(0, MAX_PROMPT_LENGTH) + "...";
    }
}