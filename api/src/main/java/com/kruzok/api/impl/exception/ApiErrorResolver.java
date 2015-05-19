package com.kruzok.api.impl.exception;

import org.springframework.web.context.request.ServletWebRequest;

import com.kruzok.api.exposed.exception.ApiError;

public interface ApiErrorResolver {

	// ApiError resolveError(ServletWebRequest request, Object handler,
	// Exception ex);
	ApiError resolveError(ServletWebRequest request, Exception ex);
}
