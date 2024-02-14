package com.example.log_puller.utility;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class PropertyReader {
    private static final String propertyFilePath = "/s3.properties";
    private Properties properties = null;
    private PropertyReader() throws IOException {
        loadProperties();
    }
    private void loadProperties() throws IOException {
        properties = new Properties();
        try (InputStream input = getClass().getResourceAsStream(propertyFilePath)) {
            properties.load(input);
        }
    }
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
