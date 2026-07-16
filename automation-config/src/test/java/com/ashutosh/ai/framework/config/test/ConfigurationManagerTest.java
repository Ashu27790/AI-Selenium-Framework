package com.ashutosh.ai.framework.config.test;
import com.ashutosh.ai.framework.config.manager.ConfigurationManager;

public class ConfigurationManagerTest {
    public static void main(String[] args) {
        ConfigurationManager config = ConfigurationManager.getInstance();
        System.out.println(config.getProperty("browser"));
        System.out.println(config.getProperty("base.url"));
        System.out.println(config.getBooleanProperty("headless"));
        System.out.println(config.getIntProperty("explicit.wait"));

    }
}