package com.kruzok.api.exposed.converter;

import com.kruzok.api.exposed.exception.ConversionException;

public interface FieldConverter<VERSIONED_DTO, INTERNAL_DTO> {

	INTERNAL_DTO convertInput(VERSIONED_DTO field) throws ConversionException;

	VERSIONED_DTO convertOutput(INTERNAL_DTO field) throws ConversionException;

}