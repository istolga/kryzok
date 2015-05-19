package com.kruzok.api.exposed.versioned.rev1.adapters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kruzok.api.exposed.adapter.OutputAdapter;
import com.kruzok.api.exposed.exception.ApiError;
import com.kruzok.api.exposed.exception.ApiError.Error;
import com.kruzok.api.exposed.versioned.rev1.beans.ApiErrorRev1;
import com.kruzok.api.exposed.versioned.rev1.beans.ApiErrorRev1.ErrorRev1;

@Component
public class ApiErrorRev1Adapter implements
		OutputAdapter<ApiError, ApiErrorRev1> {

	@Value("${api.latest.version}")
	private int latestVersion;

	@Override
	public ApiErrorRev1 adapt(ApiError from) {

		ApiErrorRev1 to = new ApiErrorRev1();
		to.setStatus(from.getStatus());
		to.setErrors(adapt(from.getErrors(), from.getStatus()));

		return to;
	}

	private List<ErrorRev1> adapt(List<Error> errors, int statusCode) {
		if (errors == null) {
			return null;
		}
		List<ErrorRev1> errorsRev1 = new ArrayList<ErrorRev1>();
		for (Error error : errors) {
			ErrorRev1 errorRev1 = new ErrorRev1();
			errorRev1.setCode(error.getCode());
			errorRev1.setMessage(error.getMessage());
			if (error.getStatus() > 0) {
				errorRev1.setStatus(error.getStatus());
			} else {
				errorRev1.setStatus(statusCode);
			}
			errorsRev1.add(errorRev1);
		}
		return errorsRev1;
	}

	@Override
	public Class<ApiError> getFromClass() {
		return ApiError.class;

	}

	@Override
	public Class<ApiErrorRev1> getToClass() {
		return ApiErrorRev1.class;

	}

	@Override
	public boolean canSupport(int version) {
		if (version <= 1)
			return true;
		return false;
	}
}
