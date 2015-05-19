package com.kruzok.api.exposed.exception;

public class UnsupportedParameterException extends RuntimeException {

	public UnsupportedParameterException() {
		super();
	}

	public UnsupportedParameterException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause);
	}

	public UnsupportedParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsupportedParameterException(String message) {
		super(message);
	}

	public UnsupportedParameterException(Throwable cause) {
		super(cause);
	}

}
