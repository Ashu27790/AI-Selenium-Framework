package com.ashutosh.ai.framework.common.constants;

/**
 * Centralized file and directory locations used by the framework.
 */
public final class FileConstants {

    private FileConstants() {
        // Prevent instantiation
    }

    public static final String PROJECT_ROOT = System.getProperty("user.dir");

    public static final String CONFIG_DIRECTORY =
            PROJECT_ROOT + "/config/";

    public static final String TEST_DATA_DIRECTORY =
            PROJECT_ROOT + "/test-data/";

    public static final String REPORT_DIRECTORY =
            PROJECT_ROOT + "/reports/";

    public static final String SCREENSHOT_DIRECTORY =
            REPORT_DIRECTORY + "screenshots/";

}