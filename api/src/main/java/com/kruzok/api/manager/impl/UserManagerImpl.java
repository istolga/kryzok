package com.kruzok.api.manager.impl;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.kruzok.api.domain.User;
import com.kruzok.api.manager.UserManager;

@Component
public class UserManagerImpl implements UserManager {

	private static final Log log = LogFactory.getLog(UserManager.class);

	@Resource(name = "mongoTemplate")
	private MongoOperations mongoOperation;

	@Override
	public User findByUserIdAndDeviceId(String userId, String deviceId) {
		Query query = new Query(Criteria
				.where("id")
				.is(userId)
				.andOperator(
						Criteria.where("devices").elemMatch(
								Criteria.where("id").is(deviceId))));
		return mongoOperation.findOne(query, User.class);
	}

	@Override
	public void save(User user) {
		Date today = Calendar.getInstance().getTime();
		if (user.getId() == null) {
			user.setCreateDate(today);
		}
		user.setUpdateDate(today);
		mongoOperation.save(user);
	}

}
