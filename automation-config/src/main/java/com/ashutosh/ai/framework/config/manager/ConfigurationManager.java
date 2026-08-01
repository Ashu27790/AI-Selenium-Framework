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
	private static final ConfigurationManager INSTANCE = new ConfigurationManager();
	private final Properties properties;
	private ConfigurationManager() {
		 properties = PropertyLoader.load("framework.properties");
	}
	/**
	 * Returns the singleton instance of ConfigurationManager.
	 *
	 * @return ConfigurationManager singleton instance
	 */
	public static ConfigurationManager getInstance() {
	    return INSTANCE;
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
	 * Returns the property value if present; otherwise returns the default value.
	 *
	 * @param key property key
	 * @param defaultValue default value
	 * @return property value or default value
	 */
	public String getProperty(final String key,final String defaultValue) {
	    return properties.getProperty(key, defaultValue);

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
	/**
	 * Returns boolean property.
	 *
	 * @param key Property key
	 * @param defaultValue Default value
	 * @return Boolean value
	 */
	public boolean getBooleanProperty(final String key, final boolean defaultValue) {
	    return Boolean.parseBoolean(
	            properties.getProperty(key, String.valueOf(defaultValue)));
	}
	/**
	 * Returns integer property.
	 *
	 * @param key Property key
	 * @param defaultValue Default value
	 * @return Integer value
	 */
	public int getIntProperty(final String key, final int defaultValue) {
	    return Integer.parseInt(
	            properties.getProperty(key, String.valueOf(defaultValue)));
	}
	/**
	 * Returns double property.
	 *
	 * @param key Property key
	 * @return Double value
	 */
	public double getDoubleProperty(final String key) {
	    return Double.parseDouble(properties.getProperty(key));
	}
	/**
	 * Returns double property.
	 *
	 * @param key Property key
	 * @param defaultValue Default value
	 * @return Double value
	 */
	public double getDoubleProperty(final String key,
	                                final double defaultValue) {

	    return Double.parseDouble(
	            properties.getProperty(key, String.valueOf(defaultValue)));
	}
}