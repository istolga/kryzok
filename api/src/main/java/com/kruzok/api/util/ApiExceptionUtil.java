package com.kruzok.api.util;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.kruzok.api.exposed.exception.ApiException;


public class ApiExceptionUtil {
    
    public static ApiException buildInternalServerErrorApiException(
            MessageSource messageSource, String key, Object[] obj) {
        
        if (obj == null)
            obj = new Object[] {};
        
        if (messageSource == null || key == null)
            return null;
        
        String message = messageSource.getMessage(key, obj, key,
                LocaleContextHolder.getLocale());
        
        new DelegatingFilterProxy();
        return new ApiException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
    }
}
