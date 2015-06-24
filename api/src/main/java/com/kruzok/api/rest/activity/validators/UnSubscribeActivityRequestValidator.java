package com.kruzok.api.rest.activity.validators;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.kruzok.api.common.validators.IValidator;
import com.kruzok.api.exposed.beans.Errors;
import com.kruzok.api.exposed.exception.ApiException;
import com.kruzok.api.rest.activity.beans.UnSubscribeActivityRequest;

@Component
public class UnSubscribeActivityRequestValidator implements
		IValidator<UnSubscribeActivityRequest> {

	private static final String REQUEST_EMPTY_QUERY = "At least one required parameter must be specified in the request";
	public static final String REQUEST_BAD_QUERY = "Bad {0} value";

	@Override
	public void validate(UnSubscribeActivityRequest request) throws Exception {
		Errors errors = new Errors();
		// is query empty
		if (StringUtils.isBlank(request.getUserId())
				&& StringUtils.isBlank(request.getDeviceId())
				&& StringUtils.isBlank(request.getActivityId())) {

			errors.addError(HttpServletResponse.SC_BAD_REQUEST,
					REQUEST_EMPTY_QUERY);
		}
		// are all fields valid
		if (isNotValid(request.getUserId())) {
			throwBadValueError("user", errors);
		}
		if (isNotValid(request.getDeviceId())) {
			throwBadValueError("device", errors);
		}
		if (isNotValid(request.getActivityId())) {
			throwBadValueError("activity", errors);
		}

		if (CollectionUtils.isNotEmpty(errors.getErrorList())) {
			throw new ApiException(errors);
		}
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

}
