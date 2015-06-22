package com.kruzok.api.rest.user.controllers;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kruzok.api.exposed.exception.ApiException;
import com.kruzok.api.rest.user.beans.RegisterUserRequest;
import com.kruzok.api.rest.user.beans.RegisterUserResponse;
import com.kruzok.api.rest.user.managers.RegisterUserManager;
import com.kruzok.api.rest.user.validators.RegisterUserRequestValidator;

@Controller
public class RegisterUserController {

	private static final Log log = LogFactory
			.getLog(RegisterUserController.class);

	@Resource
	private RegisterUserManager manager;
	@Resource
	private RegisterUserRequestValidator validator;

	@RequestMapping(value = { "/rest/v{version:[\\d]*}/register"

	}, method = { RequestMethod.POST })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody RegisterUserResponse register(
			@RequestBody RegisterUserRequest request) throws Exception {
		try {
			validator.validate(request);
			return new RegisterUserResponse(manager.register(request));
		} catch (ApiException e) {
			log.info(getLogMessage(request));
			throw e;
		} catch (Exception e) {
			log.error(getLogMessage(request));
			throw new ApiException(
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SYS_ERROR",
					"");
		}
	}

	private String getLogMessage(RegisterUserRequest request) {
		StringBuilder builder = new StringBuilder(
				"We can't register new user('" + request.toString() + "').");
		return builder.toString();
	}

}
