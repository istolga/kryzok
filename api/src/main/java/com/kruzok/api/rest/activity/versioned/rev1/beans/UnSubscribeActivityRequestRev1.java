package com.kruzok.api.rest.activity.versioned.rev1.beans;

import java.io.Serializable;

public class UnSubscribeActivityRequestRev1 implements Serializable {

	private static final long serialVersionUID = -1333597532964591667L;
	private String userId;
	private String deviceId;
	private String activityId;

	public UnSubscribeActivityRequestRev1() {
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