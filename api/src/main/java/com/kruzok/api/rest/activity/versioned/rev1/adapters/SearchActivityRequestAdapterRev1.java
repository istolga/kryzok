package com.kruzok.api.rest.activity.versioned.rev1.adapters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kruzok.api.exposed.adapter.InputAdapter;
import com.kruzok.api.rest.activity.beans.SearchActivityRequest;
import com.kruzok.api.rest.activity.versioned.rev1.beans.SearchActivityRequestRev1;

import static com.kruzok.api.util.CommonUtil.normalizeListBySize;

@Component
public class SearchActivityRequestAdapterRev1 implements
		InputAdapter<SearchActivityRequestRev1, SearchActivityRequest> {

	private static final Log log = LogFactory
			.getLog(SearchActivityRequestAdapterRev1.class);

	static int DEFAULT_PAGE_SIZE = 50;

	@Value("${api.search.max.offset}")
	private int SEARCH_MAX_OFFSET = 1000;
	
	@Value("${api.latest.version}")
	private int latestVersion;

	@Override
	public SearchActivityRequest adapt(SearchActivityRequestRev1 from) {

		SearchActivityRequest to = new SearchActivityRequest();

		if (log.isDebugEnabled()) {
			log.debug("output adapter called for " + from);
		}

		BeanUtils.copyProperties(from, to);
		
		normalizeListBySize(to.getCategory());
		normalizeListBySize(to.getCity());
		normalizeListBySize(to.getState());
		normalizeListBySize(to.getZip());
		
		if(to.getPageSize() == null) {
			to.setPageSize(DEFAULT_PAGE_SIZE);
		}
		
		if(to.getOffset() == null) {
			to.setOffset(0);
		}

		return to;
	}

	@Override
	public boolean canSupport(int version) {
		if (version <= latestVersion) {
			return true;
		}
		return false;
	}

	@Override
	public Class<SearchActivityRequestRev1> getFromClass() {
		return SearchActivityRequestRev1.class;
	}

	@Override
	public Class<SearchActivityRequest> getToClass() {
		return SearchActivityRequest.class;
	}
}
