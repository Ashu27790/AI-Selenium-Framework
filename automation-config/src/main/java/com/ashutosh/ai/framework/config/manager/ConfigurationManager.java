package com.ashutosh.ai.framework.config.manager;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * ConfigurationManager
 *
 * Centralized configuration class for the automation framework.
 *
 * Responsibilities:
 * - Read configuration properties
 * - Provide configuration values
 * - Ensure only one instance exists (Singleton)
 *
 * Author: Ashutosh Kumar Sahu
 */
public final class ConfigurationManager {
	private static ConfigurationManager instance;
	private final Properties properties;
	private ConfigurationManager() {
		properties = new Properties();
		loadProperties();
	}
	/**
	 * Returns the singleton instance of ConfigurationManager.
	 *
	 * If the instance has not yet been created, it creates one.
	 *
	 * @return ConfigurationManager singleton instance
	 */
	public static ConfigurationManager getInstance() {
		if (instance == null) {
			instance = new ConfigurationManager();
		}
		return instance;
	}
	private void loadProperties() {
	    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
	    	if (inputStream == null) {
	            throw new RuntimeException("Unable to locate config.properties");
	        }
	    	properties.load(inputStream);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}	

}