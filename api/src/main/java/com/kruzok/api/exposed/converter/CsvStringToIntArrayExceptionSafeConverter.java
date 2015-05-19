package com.kruzok.api.exposed.converter;

public class CsvStringToIntArrayExceptionSafeConverter extends
        AbstractSimpleCsvStringToIntArrayConverter {
    
    @Override
    protected Integer convertSingleValue(String value) {
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException nfe) {
            return null;
        }
    }
}
