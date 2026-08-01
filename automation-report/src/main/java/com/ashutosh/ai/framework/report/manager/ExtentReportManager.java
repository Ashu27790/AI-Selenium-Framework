package com.ashutosh.ai.framework.report.manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ashutosh.ai.framework.config.exceptions.ConfigurationException;
import com.ashutosh.ai.framework.report.configuration.ReportConfiguration;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * Manages Extent Report initialization.
 *
 * Responsibilities:
 * <ul>
 * <li>Create report directory</li>
 * <li>Configure Spark Reporter</li>
 * <li>Initialize ExtentReports</li>
 * <li>Provide singleton ExtentReports instance</li>
 * </ul>
 *
 * Author: Ashutosh Kumar Sahu
 */
public final class ExtentReportManager {
    private static final Logger LOGGER =LogManager.getLogger(ExtentReportManager.class);
    private static volatile ExtentReportManager instance;
    private final ExtentReports extentReports;
    private ExtentReportManager() {
    	System.out.println("******** ExtentReportManager Constructor ********");
        extentReports = new ExtentReports();
        final String reportPath = createReportPath();
        System.out.println("Extent Report Path = " + reportPath);
        final ExtentSparkReporter sparkReporter =new ExtentSparkReporter(reportPath);
        configureSparkReporter(sparkReporter);
        extentReports.attachReporter(sparkReporter);
        setSystemInformation();
        LOGGER.info("Extent Report initialized successfully.");
        System.out.println("******** Extent Report Initialized ********");
    }

    /**
     * Returns singleton instance.
     *
     * @return ExtentReportManager instance
     */
    public static ExtentReportManager getInstance() {
        if (instance == null) {
            synchronized (ExtentReportManager.class) {
                if (instance == null) {
                    instance = new ExtentReportManager();
                }

            }

        }
        return instance;

    }

    /**
     * Returns ExtentReports object.
     *
     * @return ExtentReports
     */
    public ExtentReports getExtentReports() {
        return extentReports;
    }
    /**
     * Flushes report.
     */
    public void flush() {
    	LOGGER.info("Flushing Extent Report to disk.");
        extentReports.flush();
    }

    /**
     * Creates report path.
     *
     * @return report file path
     */
    private String createReportPath() {
        final String directory = ReportConfiguration.getReportDirectory();
        createDirectory(directory);
        final String reportPath = Paths.get(directory,ReportConfiguration.getReportFileName()).toString();
        LOGGER.info("Extent Report Path : {}", reportPath);
        return reportPath; 
    }

    /**
     * Creates report directory.
     *
     * @param directory report directory
     */
    private void createDirectory(final String directory) {
        final Path path = Paths.get(directory);
        try {
            Files.createDirectories(path);
            LOGGER.debug("Creating report directory : {}", directory);
        }
        catch (IOException exception) {
            throw new ConfigurationException("Unable to create report directory : "+ directory, exception);
        }
    }
    /**
     * Configures Spark Reporter.
     *
     * @param sparkReporter Spark Reporter
     */
    private void configureSparkReporter(
            final ExtentSparkReporter sparkReporter) {
        sparkReporter.config().setDocumentTitle(ReportConfiguration.getReportTitle());
	sparkReporter.config().setReportName(ReportConfiguration.getReportName());
        sparkReporter.config().setEncoding(ReportConfiguration.getEncoding());
        sparkReporter.config().setTheme(getTheme());
    }

    /**
     * Returns configured theme.
     *
     * @return Theme
     */
    private Theme getTheme() {
        final String theme =ReportConfiguration.getTheme();
        return Theme.valueOf(theme.toUpperCase());
    }

    /**
     * Sets report system information.
     */
    private void setSystemInformation() {
        extentReports.setSystemInfo("Application",ReportConfiguration.getApplicationName());
        extentReports.setSystemInfo( "Version",ReportConfiguration.getApplicationVersion());
        extentReports.setSystemInfo("Environment",ReportConfiguration.getEnvironment());
        extentReports.setSystemInfo("Browser",ReportConfiguration.getBrowser());
        extentReports.setSystemInfo("Tester", ReportConfiguration.getTester());
    }

}