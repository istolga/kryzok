package com.kruzok.api.common.validators;

import com.kruzok.api.common.IRequest;

public interface IValidator<I extends IRequest> {

	void validate(I request) throws Exception;
}
