package com.kruzok.api.exposed.converter;

import com.kruzok.api.exposed.exception.ConversionException;

public class PlainFieldConverter implements FieldConverter<Object, Object> {

	@Override
	public Object convertInput(Object field) throws ConversionException {
		if (field == null) {
			return null;
		}

		if (field instanceof String) {
			return field;
		} else if (field instanceof Integer) {
			return new Integer(((Integer) field).intValue());
		} else if (field instanceof Long) {
			return new Long(((Long) field).longValue());
		} else if (field instanceof Byte) {
			return new Byte(((Byte) field).byteValue());
		} else if (field instanceof Double) {
			return new Double(((Double) field).doubleValue());
		} else if (field instanceof Float) {
			return new Float(((Float) field).floatValue());
		} else if (field instanceof Boolean) {
			return field;
		}

		throw new ConversionException("No convertor for " + field.getClass()
				+ " has been found.");

	}

	@Override
	public Object convertOutput(Object field) throws ConversionException {
		return convertInput(field);
	}

}
