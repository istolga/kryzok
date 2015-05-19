package com.kruzok.api.exposed.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.kruzok.api.exposed.BeanVersionedConverter;
import com.kruzok.api.exposed.exception.ConversionException;


public abstract class AbstractListConverter<T, D> implements
        FieldConverter<List<T>, List<D>> {
    
    public abstract Class<D> getInternalBeanClass();
    
    public abstract Class<T> getVersionedBeanClass();
    
    @Override
    public List<D> convertInput(List<T> field) throws ConversionException {
        if (field == null) {
            return null;
        }
        
        if (CollectionUtils.isEmpty(field)) {
            return Collections.emptyList();
        }
        
        List<D> outputList = new ArrayList<D>();
        
        Class<D> resultClass = getInternalBeanClass();
        try {
            for (T input : field) {
                D output = resultClass.newInstance();
                BeanVersionedConverter.toInternalBean(input, output);
                outputList.add(output);
            }
        }
        catch (Exception e) {
            throw new ConversionException("List<" + getVersionedBeanClass() + "> to List<"
                    + getInternalBeanClass() + "> convertion got the exception:", e);
        }
        
        return outputList;
    }
    
    @Override
    public List<T> convertOutput(List<D> field) throws ConversionException {
        if (field == null) {
            return null;
        }
        
        if (CollectionUtils.isEmpty(field)) {
            return Collections.emptyList();
        }
        
        List<T> outputList = new ArrayList<T>();
        
        Class<T> resultClass = getVersionedBeanClass();
        try {
            for (D input : field) {
                T output = resultClass.newInstance();
                BeanVersionedConverter.fromInternalBean(input, output);
                outputList.add(output);
            }
        }
        catch (Exception e) {
            throw new ConversionException("List<" + getInternalBeanClass() + "> to List<"
                    + getVersionedBeanClass() + "> convertion got the exception:", e);
        }
        
        return outputList;
    }
    
    
}
