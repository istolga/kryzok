package com.kruzok.api.exposed.exception;

public class InvalidParameterConversionException extends ConversionException {

	public InvalidParameterConversionException() {
		super();
	}

	public InvalidParameterConversionException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidParameterConversionException(String message) {
		super(message);
	}

	public InvalidParameterConversionException(Throwable cause) {
		super(cause);
	}

}
