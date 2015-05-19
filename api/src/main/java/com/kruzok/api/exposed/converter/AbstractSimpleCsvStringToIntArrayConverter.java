package com.kruzok.api.exposed.converter;

import org.apache.commons.lang3.StringUtils;

import com.kruzok.api.exposed.exception.ConversionException;
import com.kruzok.api.exposed.exception.InvalidParameterConversionException;

public abstract class AbstractSimpleCsvStringToIntArrayConverter implements
		FieldConverter<String, int[]> {

	@Override
	public String convertOutput(int[] field) throws ConversionException {
		// feel free to implement
		throw new ConversionException("Not supported");
	}

	@Override
	public int[] convertInput(String field) throws ConversionException {
		int[] intValues = null;
		if (!StringUtils.isEmpty(field)) {
			String[] values = StringUtils.split(field, ',');
			intValues = new int[values.length];
			for (int k = 0; k < values.length; k++) {
				Integer intValue = convertSingleValue(values[k]);
				if (intValue != null) {
					intValues[k] = intValue;
				} else {
					throw new InvalidParameterConversionException(
							"Cannot convert value='" + values[k] + "'");

				}
			}
		}
		return intValues;
	}

	/**
	 * @param value
	 * @return null if impossible to convert or exception
	 */
	protected abstract Integer convertSingleValue(String value);

}
