package com.kruzok.api.exposed.converter;

public class CsvStringToUpperCaseStringArrayConverter extends
		AbstractSimpleCsvStringToStringArrayConverter {

	@Override
	protected String convertSingleValue(String value) {
		return value.trim().toUpperCase();
	}
}