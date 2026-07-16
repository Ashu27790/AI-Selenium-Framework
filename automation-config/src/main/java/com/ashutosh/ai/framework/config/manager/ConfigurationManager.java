package com.ashutosh.ai.framework.config.manager;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.ashutosh.ai.framework.config.exceptions.ConfigurationException;
import com.ashutosh.ai.framework.config.loader.PropertyLoader;
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
		 properties = PropertyLoader.load("config.properties");
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
	
	/**
	 * Returns the value associated with the given property key.
	 *
	 * @param key Property key
	 * @return Property value
	 */
	public String getProperty(String key) {
	    return properties.getProperty(key);
	}
	/**
	 * Returns integer property.
	 *
	 * @param key Property key
	 * @return Integer value
	 */
	public int getIntProperty(String key) {
	    return Integer.parseInt(properties.getProperty(key));
	}
	/**
	 * Returns boolean property.
	 *
	 * @param key Property key
	 * @return Boolean value
	 */
	public boolean getBooleanProperty(String key) {
	    return Boolean.parseBoolean(properties.getProperty(key));
	}
}