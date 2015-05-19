package com.kruzok.api.impl.converter;

import com.kruzok.api.exposed.converter.FieldConverter;

public class ConverterFactoryImpl implements ConverterFactory {

	@Override
	public FieldConverter getInstance(Class clazz) throws Exception {
		// TODO: do caching or spring to get instances, make them
		// singletone.
		Object converter = clazz.newInstance();
		if (converter instanceof FieldConverter) {
			return (FieldConverter) converter;
		} else {
			throw new IllegalArgumentException("Input class " + clazz
					+ " is not instance of FieldConverter");
		}
	}
}
