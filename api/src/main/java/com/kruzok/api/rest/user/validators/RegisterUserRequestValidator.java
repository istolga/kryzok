package com.kruzok.api.rest.user.validators;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import com.kruzok.api.common.validators.IValidator;
import com.kruzok.api.exposed.beans.Errors;
import com.kruzok.api.exposed.exception.ApiException;
import com.kruzok.api.rest.user.beans.RegisterUserRequest;

@Component
public class RegisterUserRequestValidator implements
		IValidator<RegisterUserRequest> {

	private static final String REQUEST_EMPTY_QUERY = "At least one required parameter must be specified in the request";

	@Override
	public void validate(RegisterUserRequest request) throws Exception {
		Errors errors = new Errors();
		// is query empty
		if (request.getDevice() == null) {
			errors.addError(HttpServletResponse.SC_BAD_REQUEST,
					REQUEST_EMPTY_QUERY);
		}

		if (CollectionUtils.isNotEmpty(errors.getErrorList())) {
			throw new ApiException(errors);
		}
	}
}
