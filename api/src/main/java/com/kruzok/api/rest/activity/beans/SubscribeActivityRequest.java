package com.kruzok.api.rest.activity.beans;

import com.kruzok.api.common.IRequest;

public class SubscribeActivityRequest implements IRequest {

	private String userId;
	private String deviceId;
	private String activityId;

	public SubscribeActivityRequest() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

}