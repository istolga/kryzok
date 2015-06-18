package com.kruzok.api.rest.activity.converters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.kruzok.api.rest.activity.beans.SearchActivityRequest;

@Component
public class SearchActivityRequestToQueryConverter implements
		Converter<SearchActivityRequest, Query> {

	static int DEFAULT_PAGE_SIZE = 200;

	@Value("${api.search.max.offset}")
	private int SEARCH_MAX_OFFSET = 50000;

	@Override
	public Query convert(SearchActivityRequest request) {
		Query query = new Query(Criteria.where("title").is(request.getTitle()));

		return query;
	}
}
