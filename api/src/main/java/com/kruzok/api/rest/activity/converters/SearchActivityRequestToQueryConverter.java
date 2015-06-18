package com.kruzok.api.rest.activity.converters;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.kruzok.api.rest.activity.beans.SearchActivityRequest;

@Component
public class SearchActivityRequestToQueryConverter implements
		Converter<SearchActivityRequest, Query> {

	static int DEFAULT_PAGE_SIZE = 50;

	@Value("${api.search.max.offset}")
	private int SEARCH_MAX_OFFSET = 1000;

	@Override
	public Query convert(SearchActivityRequest request) {
		Query mainQuery = new Query();

		if (StringUtils.isNotBlank(request.getFreeText())) {
			mainQuery.addCriteria(Criteria.where("title").is(
					request.getFreeText()));
		} else {
			List<Criteria> andCriteriaList = new ArrayList<Criteria>();
			
			add("url", request.getUrl(), andCriteriaList);
			add("title", request.getTitle(), andCriteriaList);
			add("category", request.getCategory(), andCriteriaList);
			add("city", request.getCity(), andCriteriaList);
			add("state", request.getState(), andCriteriaList);
			add("zip", request.getZip(), andCriteriaList);

			mainQuery.addCriteria(new Criteria().andOperator(andCriteriaList
					.toArray(new Criteria[andCriteriaList.size()])));
		}

		mainQuery.limit(Math.max(
				Math.min(request.getPageSize(), DEFAULT_PAGE_SIZE), 0));
		mainQuery.skip(Math.max(
				Math.min(request.getOffset(), SEARCH_MAX_OFFSET), 0));

		return mainQuery;
	}

	private void add(String fieldName, String fieldValue,
			List<Criteria> criteria) {
		if (StringUtils.isNotBlank(fieldValue)) {
			criteria.add(Criteria.where(fieldName).is(fieldValue));
		}
	}

	private void add(String fieldName, List<String> fieldValues,
			List<Criteria> criteria) {

		if (CollectionUtils.isNotEmpty(fieldValues)) {
			List<Criteria> or = new ArrayList<Criteria>();

			for (String category : fieldValues) {
				or.add(Criteria.where(fieldName).is(category));
			}

			criteria.add(new Criteria().orOperator(or.toArray(new Criteria[or
					.size()])));
		}
	}
}
