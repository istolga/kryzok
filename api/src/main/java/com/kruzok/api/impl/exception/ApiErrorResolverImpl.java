package com.kruzok.api.impl.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import com.kruzok.api.exposed.beans.ErrorBean;
import com.kruzok.api.exposed.exception.ApiError;
import com.kruzok.api.exposed.exception.ApiException;


public class ApiErrorResolverImpl implements ApiErrorResolver, MessageSourceAware,
        InitializingBean {
    
    public static final String DEFAULT_EXCEPTION_MESSAGE_VALUE = "_exmsg";
    public static final String DEFAULT_MESSAGE_VALUE = "_msg";
    
    private static final Log log = LogFactory.getLog(ApiErrorResolverImpl.class);
    
    private Map<String, ApiError> exceptionMappings = Collections.emptyMap();
    
    private Map<String, String> exceptionMappingDefinitions = Collections.emptyMap();
    
    private MessageSource messageSource;
    private LocaleResolver localeResolver;
    
    private boolean defaultEmptyCodeToStatus;
    
    
    public ApiErrorResolverImpl () {
        this.defaultEmptyCodeToStatus = true;
    }
    
    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    public void setLocaleResolver(LocaleResolver resolver) {
        this.localeResolver = resolver;
    }
    
    public void setExceptionMappingDefinitions(
            Map<String, String> exceptionMappingDefinitions) {
        this.exceptionMappingDefinitions = exceptionMappingDefinitions;
    }
    
    public void setDefaultEmptyCodeToStatus(boolean defaultEmptyCodeToStatus) {
        this.defaultEmptyCodeToStatus = defaultEmptyCodeToStatus;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        
        // populate with some defaults:
        Map<String, String> definitions = createDefaultExceptionMappingDefinitions();
        
        // add in user-specified mappings (will override defaults as necessary):
        if (this.exceptionMappingDefinitions != null
                && !this.exceptionMappingDefinitions.isEmpty()) {
            definitions.putAll(this.exceptionMappingDefinitions);
        }
        
        this.exceptionMappings = toApiErrors(definitions);
    }
    
    protected final Map<String, String> createDefaultExceptionMappingDefinitions() {
        
        Map<String, String> m = new LinkedHashMap<String, String>();
        
        // 400
        applyDef(m, HttpMessageNotReadableException.class, HttpStatus.BAD_REQUEST);
        applyDef(m, MissingServletRequestParameterException.class, HttpStatus.BAD_REQUEST);
        applyDef(m, InvalidMediaTypeException.class, HttpStatus.BAD_REQUEST);
        applyDef(m, TypeMismatchException.class, HttpStatus.BAD_REQUEST);
        applyDef(m, "javax.validation.ValidationException", HttpStatus.BAD_REQUEST);
        // 400
        applyDef(m, "com.kruzok.api.exposed.exception.ApiException",
                HttpStatus.BAD_REQUEST);
        applyDef(m, "javax.xml.bind.UnmarshalException", HttpStatus.BAD_REQUEST);
        
        // 404
        applyDef(m, NoSuchRequestHandlingMethodException.class, HttpStatus.NOT_FOUND);
        
        // 405
        applyDef(m, HttpRequestMethodNotSupportedException.class,
                HttpStatus.METHOD_NOT_ALLOWED);
        
        // 406
        applyDef(m, HttpMediaTypeNotAcceptableException.class, HttpStatus.NOT_ACCEPTABLE);
        
        // 415
        applyDef(m, HttpMediaTypeNotSupportedException.class,
                HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        
        
        return m;
    }
    
    private void applyDef(Map<String, String> m, Class<?> clazz, HttpStatus status) {
        applyDef(m, clazz.getName(), status);
    }
    
    private void applyDef(Map<String, String> m, String key, HttpStatus status) {
        m.put(key, definitionFor(status));
    }
    
    private String definitionFor(HttpStatus status) {
        return status.value() + ", " + DEFAULT_EXCEPTION_MESSAGE_VALUE;
    }
    
    @Override
    public ApiError resolveError(ServletWebRequest request, Exception ex) {
        
        ApiError template = getApiErrorTemplate(ex);
        if (template == null) {
            return null;
        }
        
        ApiError.Builder builder = new ApiError.Builder();
        builder.status(getStatusValue(template, request, ex));
        builder.errors(getErrors(template, request, ex));
        builder.throwable(ex);
        
        return builder.build();
    }
    
    protected List<ApiError.Error> getErrors(ApiError template,
            ServletWebRequest request, Exception ex) {
        
        List<ApiError.Error> errors = null;
        
        if (ex instanceof ApiException && ((ApiException) ex).getErrors() != null) {
            // get Dynamic messages
            errors = getErrors(ex);
            return errors;
            
        }
        else {
            if (request.getNativeRequest() != null && log.isDebugEnabled()) {
                log.debug(
                        "Request "
                                + ((HttpServletRequest) request.getNativeRequest()).getRequestURL()
                                + " failed with exception ", ex);
            }
            errors = new ArrayList<ApiError.Error>();
            int code = getCode(template, request, ex);
            String msg = getMessage(template, request, ex);
            
            ApiError.Error apiError = new ApiError.Error(code, msg);
            errors.add(apiError);
            
        }
        return errors;
    }
    
    protected List<ApiError.Error> getErrors(Exception ex) {
        
        if (((ApiException) ex).getErrors() == null
                || ((ApiException) ex).getErrors().getErrorList() == null) {
            return null;
        }
        
        List<ApiError.Error> errors = new ArrayList<ApiError.Error>();
        for (ErrorBean error : ((ApiException) ex).getErrors().getErrorList()) {
            int code = error.getErrorCode();
            String msg = error.getErrorMsg();
            HttpStatus status = null;
            if (error.getHttpStatusCode() != null) {
                status = HttpStatus.valueOf(error.getHttpStatusCode());
            }
            else {
                if (ex instanceof ApiException
                        && ((ApiException) ex).getStatusCode() != null) {
                    status = ((ApiException) ex).getStatusCode();
                }
            }
            ApiError.Error apiError = new ApiError.Error(code, msg, status);
            
            errors.add(apiError);
        }
        return errors;
    }
    
    protected int getStatusValue(ApiError template, ServletWebRequest request,
            Exception ex) {
        
        if (ex instanceof ApiException && ((ApiException) ex).getStatusCode() != null) {
            return ((ApiException) ex).getStatusCode().value();
        }
        return template.getStatus();
    }
    
    protected int getCode(ApiError template, ServletWebRequest request, Exception ex) {
        int code = -1;
        if (template.getError(0) != null) {
            code = template.getError(0).getCode();
        }
        if (code <= 0 && defaultEmptyCodeToStatus) {
            code = getStatusValue(template, request, ex);
        }
        return code;
    }
    
    protected String getMessage(ApiError template, ServletWebRequest request, Exception ex) {
        if (template.getError(0) == null) {
            return getMessage(DEFAULT_EXCEPTION_MESSAGE_VALUE, request, ex);
        }
        return getMessage(template.getError(0).getMessage(), request, ex);
    }
    
    
    /**
     * Returns the response status message to return to the client, or
     * {@code null} if no status message should be returned.
     * 
     * @return the response status message to return to the client, or
     *         {@code null} if no status message should be returned.
     */
    protected String getMessage(String msg, ServletWebRequest webRequest, Exception ex) {
        
        if (msg != null) {
            if (msg.equalsIgnoreCase("null") || msg.equalsIgnoreCase("off")) {
                return null;
            }
            if (msg.equalsIgnoreCase(DEFAULT_EXCEPTION_MESSAGE_VALUE)) {
                log.debug("ignoring exception message in response " + ex.getMessage());
                msg = ex instanceof ApiException ? ex.getMessage() : "";
            }
            if (messageSource != null) {
                Locale locale = null;
                if (localeResolver != null) {
                    locale = localeResolver.resolveLocale(webRequest.getRequest());
                }
                msg = messageSource.getMessage(msg, null, msg, locale);
            }
        }
        
        return msg;
    }
    
    /**
     * Returns the config-time 'template' ApiError instance configured for the
     * specified Exception, or {@code null} if a match was not found.
     * <p/>
     * The config-time template is used as the basis for the ApiError
     * constructed at runtime.
     * 
     * @param ex
     * @return the template to use for the ApiError instance to be constructed.
     */
    private ApiError getApiErrorTemplate(Exception ex) {
        Map<String, ApiError> mappings = this.exceptionMappings;
        if (CollectionUtils.isEmpty(mappings)) {
            return null;
        }
        ApiError template = null;
        String dominantMapping = null;
        int deepest = Integer.MAX_VALUE;
        for (Map.Entry<String, ApiError> entry : mappings.entrySet()) {
            String key = entry.getKey();
            int depth = getDepth(key, ex);
            if (depth >= 0 && depth < deepest) {
                deepest = depth;
                dominantMapping = key;
                template = entry.getValue();
            }
        }
        if (template != null && log.isDebugEnabled()) {
            log.debug("Resolving to ApiError template '" + template
                    + "' for exception of type [" + ex.getClass().getName()
                    + "], based on exception mapping [" + dominantMapping + "]");
        }
        return template;
    }
    
    /**
     * Return the depth to the superclass matching.
     * <p>
     * 0 means ex matches exactly. Returns -1 if there's no match. Otherwise,
     * returns depth. Lowest depth wins.
     */
    protected int getDepth(String exceptionMapping, Exception ex) {
        return getDepth(exceptionMapping, ex.getClass(), 0);
    }
    
    private int getDepth(String exceptionMapping, Class<?> exceptionClass, int depth) {
        if (exceptionClass.getName().contains(exceptionMapping)) {
            // Found it!
            return depth;
        }
        // If we've gone as far as we can go and haven't found it...
        if (exceptionClass.equals(Throwable.class)) {
            return -1;
        }
        return getDepth(exceptionMapping, exceptionClass.getSuperclass(), depth + 1);
    }
    
    
    protected Map<String, ApiError> toApiErrors(Map<String, String> smap) {
        if (CollectionUtils.isEmpty(smap)) {
            return Collections.emptyMap();
        }
        
        Map<String, ApiError> map = new LinkedHashMap<String, ApiError>(smap.size());
        
        for (Map.Entry<String, String> entry : smap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            ApiError template = toApiError(value);
            map.put(key, template);
        }
        
        return map;
    }
    
    protected ApiError toApiError(String exceptionConfig) {
        String[] values = StringUtils.commaDelimitedListToStringArray(exceptionConfig);
        if (values == null || values.length == 0) {
            throw new IllegalStateException(
                    "Invalid config mapping.  Exception names must map to a string configuration.");
        }
        
        ApiError.Builder builder = new ApiError.Builder();
        
        boolean statusSet = false;
        boolean codeSet = false;
        boolean msgSet = false;
        ApiError.Error apiError = new ApiError.Error();
        for (String value : values) {
            
            String trimmedVal = StringUtils.trimWhitespace(value);
            
            // check to see if the value is an explicitly named key/value pair:
            String[] pair = StringUtils.split(trimmedVal, "=");
            if (pair != null) {
                // explicit attribute set:
                String pairKey = StringUtils.trimWhitespace(pair[0]);
                if (!StringUtils.hasText(pairKey)) {
                    pairKey = null;
                }
                String pairValue = StringUtils.trimWhitespace(pair[1]);
                if (!StringUtils.hasText(pairValue)) {
                    pairValue = null;
                }
                if ("status".equalsIgnoreCase(pairKey)) {
                    int statusCode = getRequiredInt(pairKey, pairValue);
                    builder.status(statusCode);
                    statusSet = true;
                }
                else if ("code".equalsIgnoreCase(pairKey)) {
                    int code = getRequiredInt(pairKey, pairValue);
                    apiError.setCode(code);
                    codeSet = true;
                }
                else if ("msg".equalsIgnoreCase(pairKey)) {
                    apiError.setMessage(pairValue);
                    msgSet = true;
                }
                
            }
            else {
                // not a key/value pair - use heuristics to determine what value
                // is being set:
                int val;
                if (!statusSet) {
                    val = getInt("status", trimmedVal);
                    if (val > 0) {
                        builder.status(val);
                        statusSet = true;
                        continue;
                    }
                }
                if (!codeSet) {
                    val = getInt("code", trimmedVal);
                    if (val > 0) {
                        apiError.setCode(val);
                        codeSet = true;
                        continue;
                    }
                }
                if (!msgSet) {
                    apiError.setMessage(trimmedVal);
                    msgSet = true;
                    continue;
                }
            }
        }
        if (codeSet || msgSet) {
            List<ApiError.Error> errors = new ArrayList<ApiError.Error>();
            errors.add(apiError);
            builder.errors(errors);
        }
        
        return builder.build();
    }
    
    private static int getRequiredInt(String key, String value) {
        try {
            int anInt = Integer.valueOf(value);
            return Math.max(-1, anInt);
        }
        catch (NumberFormatException e) {
            String msg = "Configuration element '" + key
                    + "' requires an integer value.  The value " + "specified: " + value;
            throw new IllegalArgumentException(msg, e);
        }
    }
    
    private static int getInt(String key, String value) {
        try {
            return getRequiredInt(key, value);
        }
        catch (IllegalArgumentException iae) {
            return 0;
        }
    }
}
