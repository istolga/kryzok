package com.kruzok.api.rest.admin.upsert.managers.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import com.kruzok.api.common.Message;
import com.kruzok.api.common.MessageType;
import com.kruzok.api.domain.Activity;
import com.kruzok.api.rest.admin.upsert.beans.ActivityUpsertResponse;
import com.kruzok.api.rest.admin.upsert.beans.ActivityUpsertResponseState;
import com.kruzok.api.rest.admin.upsert.managers.ActivityManager;

@Component
public class ActivityManagerImpl implements ActivityManager {

	@Resource(name = "mongoTemplate")
	private MongoOperations mongoOperation;

	@Override
	public List<ActivityUpsertResponse> upsert(List<Activity> activities) {
		List<ActivityUpsertResponse> response = new ArrayList<ActivityUpsertResponse>();

		if (CollectionUtils.isNotEmpty(activities)) {
			for (Activity activity : activities) {
				ActivityUpsertResponse acResponse = null;
				try {
					if (activity.getId() == null) {
						mongoOperation.save(activity);

						acResponse = new ActivityUpsertResponse(activity);
						acResponse
								.setResponseState(ActivityUpsertResponseState.ADDED);
					} else {
						mongoOperation.save(activity);

						acResponse = new ActivityUpsertResponse(activity);
						acResponse
								.setResponseState(ActivityUpsertResponseState.UPDATED);
					}
				} catch (Exception exception) {
					acResponse
							.setResponseState(ActivityUpsertResponseState.ERROR);
					acResponse.setMessages(Arrays.asList(new Message(
							"We can not add " + activity.toString(),
							MessageType.DANGER)));
				}

				response.add(acResponse);
			}
		}
		return response;
	}
}
