package com.kruzok.api.exposed.exception;

import org.springframework.http.HttpStatus;

import com.kruzok.api.exposed.beans.Errors;


public class ApiException extends Exception {
    
    private static final long serialVersionUID = -2908899088246017030L;
    
    private Errors errors;
    private HttpStatus statusCode;
    private String errorCode;
    
    public ApiException () {
        super();
    }
    
    public ApiException (int statusCode, String message, Errors errors) {
        super(message);
        this.statusCode = HttpStatus.valueOf(statusCode);
        this.errors = errors;
    }
    
    public ApiException (int statusCode, String message) {
        super(message);
        this.statusCode = HttpStatus.valueOf(statusCode);
    }
    
    public ApiException (int statusCode, String errorCode, String message) {
        super(message);
        this.statusCode = HttpStatus.valueOf(statusCode);
        this.errorCode = errorCode;
    }
    
    public ApiException (String message, Errors errors) {
        super(message);
        this.errors = errors;
    }
    
    public ApiException (Errors errors) {
        this.errors = errors;
    }
    
    public ApiException (String message) {
        super(message);
    }
    
    public ApiException (Throwable cause) {
        super(cause);
    }
    
    public Errors getErrors() {
        return errors;
    }
    
    public void setErrors(Errors errors) {
        this.errors = errors;
    }
    
    public HttpStatus getStatusCode() {
        return statusCode;
    }
    
    public void setStatusCode(HttpStatus httpStatus) {
        statusCode = httpStatus;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
  
}
