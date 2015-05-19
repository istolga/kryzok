package com.kruzok.api.impl.context;

import javax.servlet.http.HttpServletRequest;

import com.kruzok.api.exposed.context.ApiContext;

/**
 * this class should NOT be initialized outside of this package
 */
class ApiContextImpl implements ApiContext {

	private final Integer version;
	private final HttpServletRequest request;

	ApiContextImpl(final Integer version, final HttpServletRequest request) {

		this.version = version;
		this.request = request;
	}

	@Override
	public int getRequestVersion() {
		return version;
	}

	@Override
	public String getHeader(String headerName) {

		return request.getHeader(headerName);
	}

}
