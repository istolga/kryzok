package com.kruzok.api.common.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;

public final class ServerConfig {

	protected static Log log = LogFactory.getLog(ServerConfig.class);

	private static final String DWM_API_VERSION_PROPERTIES = "api_version.properties";
	private static final Properties pomProperties = new Properties();
	private static String apiBuildVersion;

	static {
		InputStream in = null;
		try {
			ClassPathResource pathResource = new ClassPathResource(DWM_API_VERSION_PROPERTIES);
			in = pathResource.getInputStream();
			pomProperties.load(in);
			String version = pomProperties.getProperty("version");
			apiBuildVersion = (version == null) ? "Unknown" : version;
		} catch (Exception e) {
			log.error("Cannot load file " + DWM_API_VERSION_PROPERTIES + ". ApiConnect verion is unknown.", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception ex) {
				log.error(ex);
			}
		}
	}

	public void init() {
		// log build version number when JVM started
		log.info("API Build Version: " + apiBuildVersion);
	}

	public String getApiBuildVersion() {
		return apiBuildVersion;
	}
}
