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
 * @author Ashutosh Kumar Sahu
 * @version 1.0
 */
public final class ReportConfiguration {
	/**
	 * Timestamp formatter used for report file names.
	 */
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
	/**
	 * Configuration Manager instance.
	 */
	private static final ConfigurationManager CONFIGURATION_MANAGER =ConfigurationManager.getInstance();
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
	 * Returns report file name.
	 *
	 * @return report file name
	 */
	public static String getReportFileName() {
		return "AutomationReport_"+ LocalDateTime.now().format(FORMATTER)+ ".html";
	}

	/**
	 * Returns report title.
	 *
	 * @return report title
	 */
	public static String getDocumentTitle() {
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
	 * Returns framework/system name.
	 *
	 * @return system name
	 */
	public static String getSystemName() {
		return CONFIGURATION_MANAGER.getProperty("report.system.name");
	}
	/**
	 * Returns execution environment.
	 *
	 * @return execution environment
	 */
	public static String getEnvironment() {
		return CONFIGURATION_MANAGER.getProperty("report.environment");
	}
	/**
	 * Returns tester name.
	 *
	 * @return tester name
	 */
	public static String getTester() {
		return CONFIGURATION_MANAGER.getProperty("report.tester");
	}

}