package com.kruzok.api.exposed.beans;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class ErrorBean {
    
    private Integer httpStatusCode;
    private Integer errorCode;
    private String errorMessage;
    
    
    public ErrorBean () {
    }
    
    public ErrorBean (Integer errorCode, String errorMsg) {
        setErrorCode(errorCode);
        setErrorMsg(errorMsg);
    }
    
    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }
    
    public Integer getErrorCode() {
        return errorCode;
    }
    
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
    
    public String getErrorMsg() {
        return errorMessage;
    }
    
    public void setErrorMsg(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    
    public void setHttpStatusCode(Integer httpStatusCode) {
        if (httpStatusCode == null) {
            this.httpStatusCode = HttpServletResponse.SC_BAD_REQUEST;
        }
        this.httpStatusCode = httpStatusCode;
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }
        ErrorBean castOther = (ErrorBean) other;
        return new EqualsBuilder() //
        .append(httpStatusCode, castOther.httpStatusCode) //
        .append(errorCode, castOther.errorCode) //
        .append(errorMessage, castOther.errorMessage) //
        .isEquals();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder() //
        .append(httpStatusCode) //
        .append(errorCode) //
        .append(errorMessage) //
        .toHashCode();
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this) //
        .append("httpStatusCode", httpStatusCode) //
        .append("errorCode", errorCode) //
        .append("errorMessage", errorMessage) //
        .toString();
    }
    
    
}
