package com.ashutosh.ai.tests.smoke;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ashutosh.ai.framework.driver.manager.DriverManager;
import com.ashutosh.ai.framework.test.base.BaseTest;

public class GoogleTest extends BaseTest {

    @Test
    public void verifyGoogleTitle() {

        DriverManager.getDriver().get("https://www.google.com");
        System.out.println(DriverManager.getDriver().getTitle());
        Assert.assertEquals(
            DriverManager.getDriver().getTitle(),
            "Google");
    }
}