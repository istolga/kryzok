package com.kruzok.api.rest.share.controllers;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kruzok.api.exposed.exception.ApiException;
import com.kruzok.api.rest.share.beans.ShareSetRequest;
import com.kruzok.api.rest.share.beans.ShareSetResponse;

@Controller
public class VersionedShareApiController {

	private static final Log log = LogFactory
			.getLog(VersionedShareApiController.class);

	@RequestMapping(value = { "/rest/v{version:[\\d]*}/share/set"

	}, method = { RequestMethod.POST })
	public @ResponseBody ShareSetResponse set(
			@RequestBody ShareSetRequest request) throws Exception {
		try {
			return null;
		} catch (Exception e) {
			log.error("We can not share a object.", e);
			throw new ApiException(
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SYS_ERROR",
					"");
		}
	}

}
