package com.kruzok.api.manager.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.kruzok.api.domain.Activity;
import com.kruzok.api.manager.ActivityManager;

@Component
public class ActivityManagerImpl implements ActivityManager {

	private static final Log log = LogFactory.getLog(ActivityManager.class);

	@Resource(name = "mongoTemplate")
	private MongoOperations mongoOperation;

	@Override
	public Activity findById(String activityId) {
		Query searchActivityQuery = new Query(Criteria.where("id").is(
				activityId));
		return mongoOperation.findOne(searchActivityQuery, Activity.class);
	}

	@Override
	public void save(Activity activity) throws Exception {
		Date today = Calendar.getInstance().getTime();
		if (activity.getId() == null) {
			activity.setCreateDate(today);
			activity.setUpdateDate(today);
		} else {
			Activity savedActivity = this.findById(activity.getId());
			if (savedActivity == null) {
				throw new Exception("Activity is not exist");
			}

			activity.setCreateDate(savedActivity.getCreateDate());
			activity.setUpdateDate(today);
		}
		mongoOperation.save(activity);
	}

	@Override
	public List<Activity> find(Query query) {
		return mongoOperation.find(query, Activity.class);
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
