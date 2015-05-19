package com.kruzok.api.exposed.exception;

public class InvalidParameterNumericConversionException extends
		ConversionException {

	public InvalidParameterNumericConversionException() {
		super();
	}

	public InvalidParameterNumericConversionException(String message,
			Throwable cause) {
		super(message, cause);
	}

	public InvalidParameterNumericConversionException(String message) {
		super(message);
	}

	public InvalidParameterNumericConversionException(Throwable cause) {
		super(cause);
	}

}
