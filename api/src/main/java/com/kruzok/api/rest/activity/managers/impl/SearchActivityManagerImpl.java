package com.kruzok.api.rest.activity.managers.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import com.kruzok.api.domain.Activity;
import com.kruzok.api.rest.activity.beans.SearchActivityRequest;
import com.kruzok.api.rest.activity.converters.SearchActivityRequestToQueryConverter;
import com.kruzok.api.rest.activity.managers.SearchActivityManager;

@Component
public class SearchActivityManagerImpl implements SearchActivityManager {

	private static final Log log = LogFactory
			.getLog(SearchActivityManager.class);

	@Resource(name = "mongoTemplate")
	private MongoOperations mongoOperation;
	@Resource
	private SearchActivityRequestToQueryConverter converter;

	@Override
	public List<Activity> search(SearchActivityRequest request) {
		List<Activity> response = new ArrayList<Activity>();

		response.addAll(mongoOperation.find(converter.convert(request),
				Activity.class));

		return response;
	}

	// private void test() {
	// // save
	// mongoOperation.save(activity);
	//
	// // now user object got the created id.
	// System.out.println("1. activity : " + activity);
	//
	// // query to search user
	// Query searchActivityQuery = new Query(Criteria.where("title")
	// .is(activity.getTitle()));
	//
	// // find the saved user again.
	// Activity savedActivity = mongoOperation.findOne(
	// searchActivityQuery, Activity.class);
	// System.out
	// .println("2. find - savedActivity : " + savedActivity);
	//
	// // update password
	// mongoOperation.updateFirst(searchActivityQuery,
	// Update.update("city", "Foster City"), Activity.class);
	//
	// // find the updated user object
	// Activity updatedActivity = mongoOperation.findOne(new Query(
	// Criteria.where("title").is(activity.getTitle())),
	// Activity.class);
	//
	// System.out.println("3. updatedActivity : " + updatedActivity);
	//
	// // delete
	// mongoOperation.remove(searchActivityQuery, Activity.class);
	//
	// // List, it should be empty now.
	// List<Activity> listActivity = mongoOperation
	// .findAll(Activity.class);
	// System.out
	// .println("4. Number of user = " + listActivity.size());
	// }

}
