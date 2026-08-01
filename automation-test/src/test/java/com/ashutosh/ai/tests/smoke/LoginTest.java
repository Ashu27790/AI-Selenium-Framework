package com.ashutosh.ai.tests.smoke;


import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.ashutosh.ai.framework.driver.manager.DriverManager;
import com.ashutosh.ai.framework.test.base.BaseTest;

public class LoginTest extends BaseTest {

    @Test
    public void verifyLoginButton() {

        DriverManager.getDriver().get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        // Intentionally incorrect locator to trigger NoSuchElementException
        DriverManager.getDriver().findElement(By.id("loginButton")).click();
    }
}