package com.kruzok.api.rest.admin.upsert.beans;

import java.util.List;

import com.kruzok.api.domain.Activity;

public class ActivityUpsertRequestWrapper {
	private List<Activity> activities;

	public ActivityUpsertRequestWrapper() {
		super();
	}

	public ActivityUpsertRequestWrapper(List<Activity> activities) {
		super();
		setActivities(activities);
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
}