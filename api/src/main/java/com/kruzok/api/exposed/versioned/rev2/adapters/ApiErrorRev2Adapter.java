package com.kruzok.api.exposed.versioned.rev2.adapters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kruzok.api.exposed.adapter.OutputAdapter;
import com.kruzok.api.exposed.exception.ApiError;
import com.kruzok.api.exposed.exception.ApiError.Error;
import com.kruzok.api.exposed.versioned.rev2.beans.ApiErrorRev2;
import com.kruzok.api.exposed.versioned.rev2.beans.ApiErrorRev2.ErrorRev2;

@Component
public class ApiErrorRev2Adapter implements
		OutputAdapter<ApiError, ApiErrorRev2> {

	@Value("${api.latest.version}")
	private int latestVersion;

	@Override
	public ApiErrorRev2 adapt(ApiError from) {

		ApiErrorRev2 to = new ApiErrorRev2();
		to.setStatus(from.getStatus());
		to.setThrowable(from.getThrowable());
		to.setErrors(adapt(from.getErrors(), from.getStatus()));

		return to;
	}

	private List<ErrorRev2> adapt(List<Error> errors, int statusCode) {
		if (errors == null) {
			return null;
		}
		List<ErrorRev2> errorsRev2 = new ArrayList<ErrorRev2>();
		for (Error error : errors) {
			ErrorRev2 errorRev2 = new ErrorRev2();
			errorRev2.setCode(error.getCode());
			errorRev2.setMessage(error.getMessage());
			errorsRev2.add(errorRev2);
		}
		return errorsRev2;
	}

	@Override
	public Class<ApiError> getFromClass() {
		return ApiError.class;

	}

	@Override
	public Class<ApiErrorRev2> getToClass() {
		return ApiErrorRev2.class;

	}

	@Override
	public boolean canSupport(int version) {
		if (version > 1 && version <= latestVersion)
			return true;
		return false;
	}
}
