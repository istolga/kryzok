package com.kruzok.api.rest.admin.upsert.managers.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.kruzok.api.common.Message;
import com.kruzok.api.common.MessageType;
import com.kruzok.api.domain.Activity;
import com.kruzok.api.manager.ActivityManager;
import com.kruzok.api.rest.admin.upsert.beans.ActivityUpsertResponse;
import com.kruzok.api.rest.admin.upsert.beans.ActivityUpsertResponseState;
import com.kruzok.api.rest.admin.upsert.managers.ActivityUpsertManager;

@Component
public class ActivityUpsertManagerImpl implements ActivityUpsertManager {

	@Resource
	private ActivityManager activityManager;

	@Override
	public List<ActivityUpsertResponse> upsert(List<Activity> activities) {
		List<ActivityUpsertResponse> response = new ArrayList<ActivityUpsertResponse>();

		if (CollectionUtils.isNotEmpty(activities)) {
			for (Activity activity : activities) {
				ActivityUpsertResponse acResponse = null;
				try {
					ActivityUpsertResponseState state = (activity.getId() == null ? ActivityUpsertResponseState.ADDED
							: ActivityUpsertResponseState.UPDATED);
					activityManager.save(activity);
					acResponse = new ActivityUpsertResponse(activity);
					acResponse.setResponseState(state);
				} catch (Exception exception) {
					acResponse
							.setResponseState(ActivityUpsertResponseState.ERROR);
					acResponse.setMessages(Arrays.asList(new Message(
							"We can not add/update " + activity.toString(),
							MessageType.DANGER)));
				}

				response.add(acResponse);
			}
		}
		return response;
	}
}
