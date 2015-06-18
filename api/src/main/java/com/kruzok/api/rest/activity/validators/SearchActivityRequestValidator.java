package com.kruzok.api.rest.activity.validators;

import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kruzok.api.common.validators.IValidator;
import com.kruzok.api.exposed.beans.Errors;
import com.kruzok.api.exposed.exception.ApiException;
import com.kruzok.api.rest.activity.beans.SearchActivityRequest;

@Component
public class SearchActivityRequestValidator implements
		IValidator<SearchActivityRequest> {

	private static final String REQUEST_EMPTY_QUERY = "At least one required parameter must be specified in the request";
	public static final String REQUEST_BAD_QUERY = "Bad {0} value";
	public static final String REQUEST_UNSUPPORTED_QUERY = "Unsupported {0} value";

	@Value("${api.search.max.offset:1000}")
	private int SEARCH_MAX_OFFSET = 1000;

	@Override
	public void validate(SearchActivityRequest request) throws Exception {
		Errors errors = new Errors();
		// is query empty
		if (StringUtils.isBlank(request.getFreeText())
				&& StringUtils.isBlank(request.getUrl())
				&& StringUtils.isBlank(request.getTitle())
				&& CollectionUtils.isEmpty(request.getCategory())
				&& CollectionUtils.isEmpty(request.getCity())
				&& CollectionUtils.isEmpty(request.getState())
				&& CollectionUtils.isEmpty(request.getZip())) {

			errors.addError(HttpServletResponse.SC_BAD_REQUEST,
					REQUEST_EMPTY_QUERY);
		}
		// check if offset exceeds SEARCH_MAX_OFFSET
		if (request.getOffset() != null) {
			if (request.getOffset() > SEARCH_MAX_OFFSET) {
				String message = "The value for offset must not exceed "
						+ SEARCH_MAX_OFFSET;
				throw new ApiException(HttpServletResponse.SC_BAD_REQUEST,
						"PARAM_ERROR", message);
			}
		}
		// are all fields valid
		if (isNotValid(request.getFreeText())) {
			throwBadValueError("free text", errors);
		}
		if (isNotValid(request.getUrl())) {
			throwBadValueError("url", errors);
		}
		if (isNotValid(request.getTitle())) {
			throwBadValueError("title", errors);
		}

		if (isNotValid(request.getTitle())) {
			throwBadValueError("title", errors);
		}
		if (isNotValid(request.getCategory())) {
			throwBadValueError("category", errors);
		}
		if (isNotValid(request.getCity())) {
			throwBadValueError("city", errors);
		}
		if (isNotValid(request.getState())) {
			throwBadValueError("state", errors);
		}
		if (isNotValid(request.getZip())) {
			throwBadValueError("zip", errors);
		}
		
		if (CollectionUtils.isNotEmpty(errors.getErrorList())) {
			throw new ApiException(errors);
		}
	}

	private boolean isNotValid(Collection<String> collection) {
		if (CollectionUtils.isNotEmpty(collection)) {
			for (String value : collection) {
				if (value == null || StringUtils.isBlank(value)
						|| "0".equals(value)) {
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}

	private boolean isNotValid(String value) {
		if (StringUtils.isNotBlank(value)) {
			if ("0".equals(value)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	private void throwBadValueError(String field, Errors errors) {
		errors.addError(HttpServletResponse.SC_BAD_REQUEST,
				REQUEST_BAD_QUERY.replace("{0}", field));
	}

	private void throwUnsupportedValueError(String field, Errors errors) {
		errors.addError(HttpServletResponse.SC_BAD_REQUEST,
				REQUEST_UNSUPPORTED_QUERY.replace("{0}", field));
	}
}
