package com.kruzok .api.exposed.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class Errors {
    
    private List<ErrorBean> errorList = null;
    
    public void setErrorList(List<ErrorBean> errorList) {
        this.errorList = errorList;
    }
    
    public List<ErrorBean> getErrorList() {
        return errorList;
    }
    
    public void addError(Integer errorCode, String errorMsg) {
        if (errorList == null) {
            errorList = new ArrayList<ErrorBean>();
        }
        ErrorBean errorBean = new ErrorBean(errorCode, errorMsg);
        errorList.add(errorBean);
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
        Errors castOther = (Errors) other;
        return new EqualsBuilder() //
        .append(errorList, castOther.errorList) //
        .isEquals();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder() //
        .append(errorList) //
        .toHashCode();
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this) //
        .append("errorList", errorList) //
        .toString();
    }
    
}
