package com.ashutosh.ai.framework.config.loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.ashutosh.ai.framework.config.exceptions.ConfigurationException;

/**
 * Utility class responsible for loading configuration property files.
 *
 * Author: Ashutosh Kumar Sahu
 */
public final class PropertyLoader {

    private PropertyLoader() {

    }

    public static Properties load(String fileName) {
        Properties properties = new Properties();
        try (InputStream inputStream = PropertyLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new ConfigurationException("Unable to locate : " + fileName);
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new ConfigurationException("Failed to load : " + fileName + " : " + e.getMessage());
        }

        return properties;

    }

}