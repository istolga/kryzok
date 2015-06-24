package com.kruzok.api.rest.activity.managers.impl;

import java.util.Calendar;
import java.util.Iterator;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.kruzok.api.domain.Activity;
import com.kruzok.api.domain.User;
import com.kruzok.api.exposed.exception.ApiException;
import com.kruzok.api.manager.ActivityManager;
import com.kruzok.api.manager.UserManager;
import com.kruzok.api.rest.activity.beans.SubscribeActivityRequest;
import com.kruzok.api.rest.activity.beans.UnSubscribeActivityRequest;
import com.kruzok.api.rest.activity.managers.SubscribeActivityManager;

@Component
public class SubscribeActivityManagerImpl implements SubscribeActivityManager {

	private static final Log log = LogFactory
			.getLog(SubscribeActivityManager.class);

	@Resource
	private UserManager userManager;
	@Resource
	private ActivityManager activityManager;

	@Override
	public void subscribe(SubscribeActivityRequest request) throws ApiException {
		User user = userManager.findByUserIdAndDeviceId(request.getUserId(),
				request.getDeviceId());
		if (user == null) {
			throw new ApiException("User(id='" + request.getUserId()
					+ "', device(id='" + request.getDeviceId()
					+ "')) not exist");
		}

		Activity activity = activityManager.findById(request.getActivityId());
		if (activity == null) {
			throw new ApiException("Activity(id='" + request.getActivityId()
					+ "')) not exist");
		}

		boolean isExist = false;
		if (CollectionUtils.isNotEmpty(user.getActivities())) {
			for (Activity item : user.getActivities()) {
				if (activity.equals(item)) {
					isExist = true;
					break;
				}
			}

		}
		if (!isExist) {
			Activity addActivity = new Activity(activity.getId());
			addActivity.setCreateDate(Calendar.getInstance().getTime());
			user.addActivities(addActivity);
			userManager.save(user);
		}
	}

	@Override
	public void unsubscribe(UnSubscribeActivityRequest request)
			throws ApiException {
		User user = userManager.findByUserIdAndDeviceId(request.getUserId(),
				request.getDeviceId());
		if (user == null) {
			throw new ApiException("User(id='" + request.getUserId()
					+ "', device(id='" + request.getDeviceId()
					+ "')) not exist");
		}

		Activity activity = activityManager.findById(request.getActivityId());
		if (activity == null) {
			throw new ApiException("Activity(id='" + request.getActivityId()
					+ "')) not exist");
		}
		
		boolean isUpdated = false;
		if (CollectionUtils.isNotEmpty(user.getActivities())) {
			for (Iterator<Activity> iterator = user.getActivities().iterator(); iterator
					.hasNext();) {
				Activity item = iterator.next();
				if (activity.equals(item)) {
					user.getActivities().remove(activity);
					isUpdated = true;
					break;
				}
			}
		}
		if (isUpdated) {
			userManager.save(user);
		}
	}

}
