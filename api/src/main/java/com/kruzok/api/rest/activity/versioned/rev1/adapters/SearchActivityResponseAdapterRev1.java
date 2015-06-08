package com.kruzok.api.rest.activity.versioned.rev1.adapters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kruzok.api.exposed.adapter.OutputAdapter;
import com.kruzok.api.rest.activity.beans.SearchActivityResponseWrapper;
import com.kruzok.api.rest.activity.versioned.rev1.beans.SearchActivityResponseWrapperRev1;

@Component
public class SearchActivityResponseAdapterRev1
		implements
		OutputAdapter<SearchActivityResponseWrapper, SearchActivityResponseWrapperRev1> {

	private static final Log log = LogFactory
			.getLog(SearchActivityResponseAdapterRev1.class);

	@Value("${api.latest.version}")
	private int latestVersion;

	@Override
	public SearchActivityResponseWrapperRev1 adapt(
			SearchActivityResponseWrapper from) {

		if (log.isDebugEnabled()) {
			log.debug("output adapter called for " + from);
		}

		return new SearchActivityResponseWrapperRev1(from.getActivities());
	}

	@Override
	public boolean canSupport(int version) {
		if (version <= latestVersion) {
			return true;
		}
		return false;
	}

	@Override
	public Class<SearchActivityResponseWrapper> getFromClass() {
		return SearchActivityResponseWrapper.class;
	}

	@Override
	public Class<SearchActivityResponseWrapperRev1> getToClass() {
		return SearchActivityResponseWrapperRev1.class;
	}
}
