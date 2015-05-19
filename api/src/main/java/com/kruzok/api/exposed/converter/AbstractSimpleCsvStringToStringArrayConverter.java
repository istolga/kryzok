package com.kruzok.api.exposed.converter;

import org.apache.commons.lang3.StringUtils;

import com.kruzok.api.exposed.exception.ConversionException;
import com.kruzok.api.exposed.exception.InvalidParameterConversionException;

public abstract class AbstractSimpleCsvStringToStringArrayConverter implements
		FieldConverter<String, String[]> {

	@Override
	public String convertOutput(String[] field) throws ConversionException {
		throw new ConversionException("Not supported");
	}

	@Override
	public String[] convertInput(String field) throws ConversionException {
		if (StringUtils.isEmpty(field)) {
			return null;
		}
		String[] values = StringUtils.split(field, ',');
		String[] newValues = new String[values.length];
		for (int k = 0; k < values.length; k++) {
			String newValue = convertSingleValue(values[k]);
			if (newValue != null) {
				newValues[k] = newValue;
			} else {
				throw new InvalidParameterConversionException(
						"Cannot convert value='" + values[k] + "'");

			}
		}
		return newValues;
	}

	/**
	 * 
	 * @param value
	 * @return null if impossible to convert or exception
	 */
	protected abstract String convertSingleValue(String value);

}
