package com.kruzok.api.exposed.converter;

import com.kruzok.api.exposed.exception.ConversionException;
import com.kruzok.api.exposed.exception.InvalidParameterConversionException;


public class StringToIntConverter implements FieldConverter<String, Integer> {
    
    @Override
    public String convertOutput(Integer field) throws ConversionException {
        //feel free to implement
        throw new ConversionException("Not supported");
    }
    
    @Override
    public Integer convertInput(String value) throws ConversionException {
        
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException nfe) {
            throw new InvalidParameterConversionException("Cannot parse number value='"
                    + value + "'");
        }
        
    }
    
}
