package com.kruzok.api.exposed.converter;

import com.kruzok.api.exposed.BeanVersionedConverter;
import com.kruzok.api.exposed.exception.ConversionException;


public abstract class AbstractSimpleBeanConverter<VERSIONED_BEAN_CLASS, INTERNAL_BEAN_CLASS>
        implements FieldConverter<VERSIONED_BEAN_CLASS, INTERNAL_BEAN_CLASS> {
    
    
    public abstract Class<INTERNAL_BEAN_CLASS> getInternalBeanClass();
    
    public abstract Class<VERSIONED_BEAN_CLASS> getVerionedBeanClass();
    
    @Override
    public INTERNAL_BEAN_CLASS convertInput(VERSIONED_BEAN_CLASS field)
            throws ConversionException {
        if (field == null) {
            return null;
        }
        
        try {
            INTERNAL_BEAN_CLASS abnew = getInternalBeanClass().newInstance();
            BeanVersionedConverter.toInternalBean(field, abnew);
            return abnew;
        }
        catch (Exception e) {
            throw new ConversionException(getVerionedBeanClass() + " to "
                    + getInternalBeanClass() + " convertion got the exception:", e);
        }
        
    }
    
    @Override
    public VERSIONED_BEAN_CLASS convertOutput(INTERNAL_BEAN_CLASS field)
            throws ConversionException {
        if (field == null) {
            return null;
        }
        
        try {
            VERSIONED_BEAN_CLASS abnew = getVerionedBeanClass().newInstance();
            BeanVersionedConverter.fromInternalBean(field, abnew);
            return abnew;
        }
        catch (Exception e) {
            throw new ConversionException(getInternalBeanClass() + " to "
                    + getInternalBeanClass() + " convertion got the exception:", e);
        }
        
        
    }
    
    
}
