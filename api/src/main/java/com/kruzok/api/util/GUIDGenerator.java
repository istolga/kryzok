package com.kruzok.api.util;

import java.util.UUID;

/**
 * Generates a version 4 UUID. Based on {@link java.util.UUID}
 */
public class GUIDGenerator {

	/**
	 * Generates UUID.
	 * 
	 * @return new ID;
	 */
	public static String generate() {
		return UUID.randomUUID().toString();
	}

}