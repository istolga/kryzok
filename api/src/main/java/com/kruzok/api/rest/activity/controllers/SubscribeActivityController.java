package com.kruzok.api.rest.activity.controllers;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kruzok.api.exposed.exception.ApiException;
import com.kruzok.api.rest.activity.beans.SubscribeActivityRequest;
import com.kruzok.api.rest.activity.managers.SubscribeActivityManager;
import com.kruzok.api.rest.activity.validators.SubscribeActivityRequestValidator;

@Controller
public class SubscribeActivityController {

	private static final Log log = LogFactory
			.getLog(SubscribeActivityController.class);

	@Resource
	private SubscribeActivityRequestValidator subscribeValidator;
	@Resource
	private SubscribeActivityManager subscribeManager;

	@RequestMapping(value = { "/rest/v{version:[\\d]*}/activity/subscribe"

	}, method = { RequestMethod.POST })
	@ResponseStatus(HttpStatus.OK)
	public void subscribe(SubscribeActivityRequest request) throws Exception {
		try {
			subscribeValidator.validate(request);
			subscribeManager.subscribe(request);
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

	private String getLogMessage(SubscribeActivityRequest request) {
		StringBuilder builder = new StringBuilder(
				"We can't unsubscribe activity by request('"
						+ request.toString() + "').");
		// TODO: KA we need to log user information here
		return builder.toString();
	}

}
