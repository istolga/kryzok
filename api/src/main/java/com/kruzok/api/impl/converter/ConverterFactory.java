package com.kruzok.api.impl.converter;

import com.kruzok.api.exposed.converter.FieldConverter;

public interface ConverterFactory {

	FieldConverter getInstance(Class clazz) throws Exception;

}
