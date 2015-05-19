package com.kruzok.api.impl.exception;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.util.WebUtils;

import com.kruzok.api.exposed.exception.ApiError;
import com.kruzok.api.exposed.exception.ApiException;

public class ApiExceptionHandlerImpl extends AbstractHandlerExceptionResolver {

	private static final Log log = LogFactory
			.getLog(ApiExceptionHandlerImpl.class);

	// private List<HttpMessageConverter<?>> allMessageConverters;

	@Resource(name = "versionHttpMessageConverter")
	HttpMessageConverter<Object> converter;

	private ApiErrorResolver errorResolver;

	public ApiExceptionHandlerImpl() {
		this.errorResolver = new ApiErrorResolverImpl();
	}

	public void setErrorResolver(ApiErrorResolver errorResolver) {
		this.errorResolver = errorResolver;
	}

	public ApiErrorResolver getErrorResolver() {
		return this.errorResolver;
	}

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		String logMessage = "Exception happened for url="
				+ request.getRequestURL() + "?" + request.getQueryString();
		if (isUserInputError(ex)) {
			log.info(logMessage, ex);
		} else if (log.isErrorEnabled()) {
			log.error(logMessage, ex);
		}

		ServletWebRequest webRequest = new ServletWebRequest(request, response);

		ApiErrorResolver resolver = getErrorResolver();

		ApiError error = resolver.resolveError(webRequest, ex);
		if (error == null) {
			return null;
		}

		ModelAndView mav = null;

		try {
			mav = getModelAndView(webRequest, handler, error);
		} catch (InvalidMediaTypeException e) {

		} catch (Exception invocationEx) {
			log.error("Acquiring ModelAndView for Exception [" + ex
					+ "] resulted in an exception.", invocationEx);
		}

		return mav;
	}

	protected ModelAndView getModelAndView(ServletWebRequest webRequest,
			Object handler, ApiError error) throws Exception {

		if (!WebUtils.isIncludeRequest(webRequest.getRequest())) {
			webRequest.getResponse().setStatus(error.getStatus());
		}
		// TODO do we need to support response.sendError ?

		Object body = error;

		return handleResponseBody(body, webRequest);
	}

	ModelAndView handleResponseBody(Object body, ServletWebRequest webRequest)
			throws ServletException, IOException {

		HttpInputMessage inputMessage = new ServletServerHttpRequest(
				webRequest.getRequest());

		List<MediaType> acceptedMediaTypes = inputMessage.getHeaders()
				.getAccept();
		if (acceptedMediaTypes.isEmpty()) {
			acceptedMediaTypes = Collections.singletonList(MediaType.ALL);
		}

		MediaType.sortByQualityValue(acceptedMediaTypes);

		HttpOutputMessage outputMessage = new ServletServerHttpResponse(
				webRequest.getResponse());

		Class<?> bodyType = body.getClass();

		for (MediaType acceptedMediaType : acceptedMediaTypes) {

			if (converter.canWrite(ApiError.class, acceptedMediaType)) {
				converter.write(body, acceptedMediaType, outputMessage);
			}
			// return empty model and view to short circuit the
			// iteration and to let
			// Spring know that we've rendered the view ourselves:
			return new ModelAndView();

		}

		if (logger.isWarnEnabled()) {
			logger.warn("Could not find HttpMessageConverter that supports return type ["
					+ bodyType + "] and " + acceptedMediaTypes);
		}
		return null;
	}

	// Only for test
	void setConverterForTest(HttpMessageConverter<Object> converter) {
		this.converter = converter;
	}

	private boolean isUserInputError(Exception exception) {
		if (exception instanceof ApiException) {
			ApiException apiException = (ApiException) exception;
			HttpStatus statusCode = apiException.getStatusCode();
			if (statusCode != null) {
				if (statusCode.value() >= 400 && statusCode.value() <= 499) {
					return true;
				}
			}
		}
		return false;
	}

}
