package com.kruzok.api.rest.activity.managers.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.kruzok.api.domain.Activity;
import com.kruzok.api.manager.ActivityManager;
import com.kruzok.api.rest.activity.beans.SearchActivityRequest;
import com.kruzok.api.rest.activity.converters.SearchActivityRequestToQueryConverter;
import com.kruzok.api.rest.activity.managers.SearchActivityManager;

@Component
public class SearchActivityManagerImpl implements SearchActivityManager {

	private static final Log log = LogFactory
			.getLog(SearchActivityManager.class);

	@Resource
	private ActivityManager activityManager;
	@Resource
	private SearchActivityRequestToQueryConverter converter;

	@Override
	public List<Activity> search(SearchActivityRequest request) {
		List<Activity> response = new ArrayList<Activity>();

		response.addAll(activityManager.find(converter.convert(request)));

		return response;
	}
}
