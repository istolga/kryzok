package com.kruzok.api.rest.admin.upsert.controllers;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.kruzok.api.exposed.exception.ApiException;
import com.kruzok.api.rest.admin.upsert.beans.ActivityUpsertRequestWrapper;
import com.kruzok.api.rest.admin.upsert.beans.ActivityUpsertResponseWrapper;
import com.kruzok.api.rest.admin.upsert.managers.ActivityManager;

@Controller
public class ActivityUpsertController {

	@Resource
	private ActivityManager activitykManager;

	private static final Log log = LogFactory
			.getLog(ActivityUpsertController.class);

	@RequestMapping(value = { "/admin/rest/v{version:[\\d]*}/activity/upsert" }, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ActivityUpsertResponseWrapper update(
			@RequestBody ActivityUpsertRequestWrapper request) throws Exception {
		try {
			return new ActivityUpsertResponseWrapper(
					activitykManager.upsert(request.getActivities()));
		} catch (Exception e) {
			throw new ApiException(
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SYS_ERROR",
					"");
		}
	}
}
