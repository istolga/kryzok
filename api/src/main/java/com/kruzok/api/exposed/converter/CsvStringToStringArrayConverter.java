package com.kruzok.api.exposed.converter;

public class CsvStringToStringArrayConverter extends
		AbstractSimpleCsvStringToStringArrayConverter {

	@Override
	protected String convertSingleValue(String value) {
		return value.trim();
	}
}
