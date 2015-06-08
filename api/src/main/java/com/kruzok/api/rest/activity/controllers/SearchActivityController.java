package com.kruzok.api.rest.activity.controllers;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kruzok.api.exposed.exception.ApiException;
import com.kruzok.api.rest.activity.beans.SearchActivityRequest;
import com.kruzok.api.rest.activity.beans.SearchActivityResponseWrapper;
import com.kruzok.api.rest.activity.managers.SearchActivityManager;

@Controller
public class SearchActivityController {

	@Resource
	private SearchActivityManager manager;

	private static final Log log = LogFactory
			.getLog(SearchActivityController.class);

	@RequestMapping(value = { "/rest/v{version:[\\d]*}/activity/search" }, method = { RequestMethod.GET })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody SearchActivityResponseWrapper update(SearchActivityRequest request)
			throws Exception {
		try {
			return new SearchActivityResponseWrapper(manager.search(request));
		} catch (Exception e) {
			throw new ApiException(
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SYS_ERROR",
					"");
		}
	}
}
