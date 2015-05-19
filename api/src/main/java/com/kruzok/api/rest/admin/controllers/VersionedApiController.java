package com.kruzok.api.rest.admin.controllers;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kruzok.api.exposed.exception.ApiException;
import com.kruzok.api.rest.admin.beans.UpdateRequest;
import com.kruzok.api.rest.admin.beans.UpdateResponse;

@RestController
public class VersionedApiController {

	private static final Log log = LogFactory
			.getLog(VersionedApiController.class);

	@RequestMapping(
			value = { "/rest/v{version:[\\d]*}/update" }, 
			method = { RequestMethod.POST }, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public UpdateResponse update(UpdateRequest request) throws Exception {
		try {

			return new UpdateResponse(request.getRequestData());
		} catch (Exception e) {
			throw new ApiException(
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SYS_ERROR",
					"");
		}
	}
}
