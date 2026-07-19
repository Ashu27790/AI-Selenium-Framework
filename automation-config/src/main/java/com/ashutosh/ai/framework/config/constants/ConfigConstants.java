package com.ashutosh.ai.framework.config.constants;

/**
 * Configuration property keys used throughout the framework.
 *
 * Keeping all property names in one place avoids magic strings
 * and makes maintenance easier.
 *
 * @author Ashutosh
 */
public final class ConfigConstants {

    private ConfigConstants() {
        // Prevent instantiation
    }

    public static final String BROWSER = "browser";
    public static final String BASE_URL = "base.url";
    public static final String HEADLESS = "headless";
    public static final String EXPLICIT_WAIT = "explicit.wait";
}