package com.kruzok.api.manager;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import com.kruzok.api.domain.Activity;

public interface ActivityManager {
	Activity findById(String activityId);
	
	List<Activity> find(Query query);

	void save(Activity activity) throws Exception;
}
