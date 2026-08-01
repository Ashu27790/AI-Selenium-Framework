package com.ashutosh.ai.framework.ai.test;

import com.ashutosh.ai.framework.ai.client.AIClient;
import com.ashutosh.ai.framework.ai.client.AIClientFactory;
import com.ashutosh.ai.framework.ai.dto.AIRequest;
import com.ashutosh.ai.framework.ai.dto.AIResponse;
import com.ashutosh.ai.framework.ai.prompt.PromptBuilder;

public class AITest {

    public static void main(String[] args) {

        AIRequest request = new AIRequest();

        request.setTestName("Login Test");
        request.setBrowser("Chrome");
        request.setApplicationUrl("https://opensource-demo.orangehrmlive.com");

        request.setExceptionName("NoSuchElementException");

        request.setErrorMessage(
                "Unable to locate element: id=loginButton");

        request.setStackTrace(
                "org.openqa.selenium.NoSuchElementException...");

        String prompt =
                PromptBuilder.buildFailureAnalysisPrompt(request);

        request.setPrompt(prompt);

        AIClient client = AIClientFactory.getClient();

        AIResponse response = client.execute(request);

        System.out.println("========================================");
        System.out.println("AI RESPONSE");
        System.out.println("========================================");
        System.out.println(response.getResponse());
    }
}