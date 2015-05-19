package com.kruzok.api.impl.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kruzok.api.exposed.context.ApiContext;
import com.kruzok.api.exposed.context.ApiContextFactory;
import com.kruzok.api.exposed.exception.ApiException;

/**
 * responsible for api context setup and version verification
 */
@Component
public class ApiContextFactoryInterceptorImpl extends HandlerInterceptorAdapter
		implements ApiContextFactory {

	private static final Log LOG = LogFactory
			.getLog(ApiContextFactoryInterceptorImpl.class);

	@Value("${api.latest.version}")
	int latestVersion;

	static ThreadLocal<ApiContext> localApiContext = new ThreadLocal<ApiContext>();

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		setupVersion(request);
		return super.preHandle(request, response, handler);
	}

	protected void setupVersion(HttpServletRequest request) throws ApiException {
		localApiContext.set(null);
		String uri = request.getRequestURI();
		int version = parseVersion(uri);
		if (version > latestVersion || version < 0) {
			throw new ApiException(404, "version " + version
					+ " is not supported");
		}
		localApiContext.set(new ApiContextImpl(version, request));
	}

	protected int parseVersion(String uri) {
		int version = 0;
		/**
		 * version appears as the first path of the api, for example
		 * "/v1/someapi" By contact, api without version is treated as version 0
		 */
		int startingPos = uri.indexOf("/v");
		if (startingPos == -1) {
			version = 0;
		} else {
			int endingPos = uri.indexOf("/", startingPos + 1);
			try {
				version = Integer.valueOf(uri.substring(startingPos + 2,
						endingPos));
			} catch (NumberFormatException nfe) {
				version = 0; // treat version as 0 for non-numeric, as it is
								// either a real api path or will get 404 anyway

			}
		}
		return version;
	}

	@Override
	public ApiContext getApiContext() {
		ApiContext context = localApiContext.get();
		return context;
	}

}
