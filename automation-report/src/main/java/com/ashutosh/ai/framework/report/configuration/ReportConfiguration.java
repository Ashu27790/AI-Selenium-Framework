package com.ashutosh.ai.framework.report.configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.ashutosh.ai.framework.config.manager.ConfigurationManager;

/**
 * Centralized configuration for Extent Reports.
 *
 * <p>
 * Reads all report configuration values from framework.properties.
 * This class acts as a wrapper around ConfigurationManager and
 * exposes report-related configuration in a type-safe manner.
 * </p>
 *
 * Responsibilities:
 * <ul>
 * <li>Provides report configuration values.</li>
 * <li>Generates timestamped report file names.</li>
 * <li>Acts as a single source of report configuration.</li>
 * </ul>
 *
 * @author Ashutosh Kumar Sahu
 * @version 1.0
 */
public final class ReportConfiguration {

    /**
     * Configuration Manager instance.
     */
    private static final ConfigurationManager CONFIGURATION_MANAGER =
            ConfigurationManager.getInstance();

    /**
     * Prevent instantiation.
     */
    private ReportConfiguration() {

    }
    /**
     * Returns report directory.
     *
     * @return report directory
     */
    public static String getReportDirectory() {

        return CONFIGURATION_MANAGER.getProperty("report.directory");
    }

    /**
     * Returns report file name with timestamp.
     *
     * @return report file name
     */
    public static String getReportFileName() {

        return getReportName().replaceAll("\\s+", "_")+ "_" + getTimeStamp()+ ".html";
    }

    /**
     * Returns report title.
     *
     * @return report title
     */
    public static String getReportTitle() {
        return CONFIGURATION_MANAGER.getProperty("report.title");
    }

    /**
     * Returns report name.
     *
     * @return report name
     */
    public static String getReportName() {
        return CONFIGURATION_MANAGER.getProperty("report.name");
    }

    /**
     * Returns report theme.
     *
     * @return report theme
     */
    public static String getTheme() {
        return CONFIGURATION_MANAGER.getProperty("report.theme");
    }

    /**
     * Returns report encoding.
     *
     * @return report encoding
     */
    public static String getEncoding() {
        return CONFIGURATION_MANAGER.getProperty("report.encoding");
    }

    /**
     * Returns framework/system name.
     *
     * @return system name
     */
    public static String getSystemName() {
        return CONFIGURATION_MANAGER.getProperty("application.name");
    }

    /**
     * Returns application version.
     *
     * @return application version
     */
    public static String getApplicationVersion() {
        return CONFIGURATION_MANAGER.getProperty("application.version");
    }

    /**
     * Returns execution environment.
     *
     * @return environment
     */
    public static String getEnvironment() {
        return CONFIGURATION_MANAGER.getProperty("environment");
    }

    /**
     * Returns configured browser.
     *
     * @return browser
     */
    public static String getBrowser() {
        return CONFIGURATION_MANAGER.getProperty("browser");
    }

    /**
     * Returns base application URL.
     *
     * @return application URL
     */
    public static String getBaseUrl() {
        return CONFIGURATION_MANAGER.getProperty("base.url");
    }

    /**
     * Returns tester name.
     *
     * @return tester name
     */
    public static String getTester() {
        return CONFIGURATION_MANAGER.getProperty("report.tester");

    }
    /**
     * Returns current timestamp formatted according to
     * the configured report timestamp pattern.
     *
     * @return formatted timestamp
     */
    public static String getTimeStamp() {

        final String pattern = CONFIGURATION_MANAGER.getProperty("report.timestamp.format", "yyyyMMdd_HHmmss");
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }
    /**
     * Returns application name.
     *
     * @return application name
     */
    public static String getApplicationName() {
        return CONFIGURATION_MANAGER.getProperty("application.name");

    }
    

}