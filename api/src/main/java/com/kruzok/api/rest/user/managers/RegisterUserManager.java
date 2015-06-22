package com.kruzok.api.rest.user.managers;

import com.kruzok.api.domain.User;
import com.kruzok.api.exposed.exception.ApiException;
import com.kruzok.api.rest.user.beans.RegisterUserRequest;

public interface RegisterUserManager {

	User register(RegisterUserRequest request) throws ApiException;
}
