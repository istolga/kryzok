package com.kruzok.api.rest.activity.managers.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.kruzok.api.domain.Activity;
import com.kruzok.api.rest.activity.beans.SearchActivityRequest;
import com.kruzok.api.rest.activity.managers.SearchActivityManager;

@Component
public class SearchActivityManagerImpl implements SearchActivityManager {

	@Resource(name = "mongoTemplate")
	private MongoOperations mongoOperation;

	@Override
	public List<Activity> search(SearchActivityRequest request) {
		List<Activity> response = new ArrayList<Activity>();

		try {
			// query to search user
			Query searchActivityQuery = new Query(Criteria.where("title").is(
					request.getTitle()));
			// find the saved user again.
			response.addAll(mongoOperation.find(searchActivityQuery,
					Activity.class));
		} catch (Exception exception) {

		}

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
