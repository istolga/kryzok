package com.kruzok.web.util;

import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class CustomPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	Properties mergedProperties;

	private void loadMergedProperties() {
		if (mergedProperties == null) {
			try {
				mergedProperties = mergeProperties();
			} catch (Exception e) {
				mergedProperties = new Properties();
			}
		}
	}

	public String getProperty(String key) {
		loadMergedProperties();
		return mergedProperties.getProperty(key);
	}
}
