package com.kruzok.api.rest.admin.upsert.beans;

import java.util.List;

public class ActivityUpsertResponseWrapper {
	private List<ActivityUpsertResponse> activities;

	public ActivityUpsertResponseWrapper() {
		super();
	}

	public ActivityUpsertResponseWrapper(List<ActivityUpsertResponse> activities) {
		super();
		setActivities(activities);
	}

	public List<ActivityUpsertResponse> getActivities() {
		return activities;
	}

	public void setActivities(List<ActivityUpsertResponse> activities) {
		this.activities = activities;
	}
}