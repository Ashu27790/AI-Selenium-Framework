package com.ashutosh.ai.framework.ai.prompt;

import com.ashutosh.ai.framework.ai.dto.AIRequest;

/**
 * Utility class responsible for generating enterprise AI prompts
 * for Selenium failure analysis.
 *
 * Responsibilities:
 * <ul>
 *     <li>Build structured prompts.</li>
 *     <li>Provide execution context.</li>
 *     <li>Force JSON-only responses.</li>
 *     <li>Improve AI accuracy using evidence-based reasoning.</li>
 * </ul>
 *
 * @author Ashutosh Kumar Sahu
 * @version 2.0
 */
public final class PromptBuilder {

    /**
     * Private constructor.
     */
    private PromptBuilder() {

    }

    /**
     * Builds enterprise prompt for Selenium failure analysis.
     *
     * @param request AI request
     * @return formatted prompt
     */
    public static String buildFailureAnalysisPrompt(
            final AIRequest request) {

        StringBuilder prompt = new StringBuilder();

        prompt.append("""
You are an Enterprise Test Automation Architect and AI Failure Analysis Expert.

You specialize in:

- Selenium WebDriver
- Java
- TestNG
- Maven
- Enterprise Automation Frameworks
- Banking Applications
- CI/CD Pipelines
- Root Cause Analysis

OBJECTIVE

Analyze the supplied Selenium execution evidence.

Use ONLY the provided information.

Never assume application behaviour.

If sufficient evidence is unavailable, explicitly state that additional
information is required instead of inventing a reason.

==========================================================
EXECUTION CONTEXT
==========================================================
""");

        appendField(prompt, "Test Name", request.getTestName());
        appendField(prompt, "Browser", request.getBrowser());
        appendField(prompt, "Browser Version", request.getBrowserVersion());
        appendField(prompt, "Environment", request.getEnvironment());
        appendField(prompt, "Application URL", request.getApplicationUrl());
        appendField(prompt, "Current URL", request.getCurrentUrl());
        appendField(prompt, "Page Title", request.getPageTitle());
        appendField(prompt, "Execution Time", request.getExecutionTime() + " ms");
        appendField(prompt, "Retry Count", String.valueOf(request.getRetryCount()));

        prompt.append("""
==========================================================
FAILURE DETAILS
==========================================================
""");

        appendField(prompt, "Exception", request.getExceptionName());
        appendField(prompt, "Error Message", request.getErrorMessage());
        appendField(prompt, "Locator", request.getLocator());
        appendField(prompt, "Screenshot", request.getScreenshotPath());

        prompt.append("""

==========================================================
DOM SNIPPET
==========================================================
""");

        prompt.append(emptyIfNull(request.getDomSnippet()));

        prompt.append("""

==========================================================
STACK TRACE
==========================================================
""");

        prompt.append(emptyIfNull(request.getStackTrace()));

        prompt.append("""

==========================================================
ANALYSIS RULES
==========================================================

1. Use ONLY the supplied evidence.

2. Do NOT assume application behaviour.

3. Distinguish confirmed facts from assumptions.

4. If evidence is insufficient,
   clearly mention what additional information is required.

5. Do NOT repeat the stack trace.

6. Keep every section concise.

7. Do NOT generate generic Selenium explanations.

8. Confidence Score Guidelines

90-100
Root cause confirmed by evidence.

70-89
Highly probable.

40-69
Multiple possible causes.

Below 40
Insufficient evidence.

==========================================================
RETURN JSON ONLY
==========================================================

Return ONLY valid JSON.

No markdown.

No code block.

No explanation.

Schema:

{
  "executiveSummary": "",
  "failureCategory": "",
  "severity": "",
  "businessImpact": "",
  "rootCause": "",
  "possibleReason": "",
  "suggestedSolution": "",
  "bestPractices": "",
  "retryRecommendation": "",
  "learningSummary": "",
  "confidenceScore": 0
}

Formatting Rules

executiveSummary
Maximum 60 words.

failureCategory
One line.

severity
One of:
Critical
High
Medium
Low

businessImpact
Maximum 40 words.

rootCause
Maximum 80 words.

possibleReason
Maximum 5 bullet points.

suggestedSolution
Maximum 5 bullet points.

bestPractices
Maximum 5 bullet points.

retryRecommendation
One concise recommendation.

learningSummary
Maximum 50 words.

confidenceScore
Integer only.

Return ONLY the JSON object.
""");

        return prompt.toString();
    }

    /**
     * Appends a formatted field if present.
     *
     * @param builder prompt builder
     * @param label field label
     * @param value field value
     */
    private static void appendField(
            final StringBuilder builder,
            final String label,
            final String value) {

        builder.append(label)
               .append(" : ")
               .append(emptyIfNull(value))
               .append(System.lineSeparator());
    }

    /**
     * Returns empty string when value is null.
     *
     * @param value input value
     * @return safe value
     */
    private static String emptyIfNull(final String value) {

        return value == null ? "Not Available" : value;
    }

    /**
     * Returns custom prompt.
     *
     * @param prompt prompt
     * @return prompt
     */
    public static String buildPrompt(final String prompt) {
        return prompt;
    }

}