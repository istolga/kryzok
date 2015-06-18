package com.kruzok.api.rest.activity.beans;

import java.util.List;

import com.kruzok.api.domain.Activity;

public class SearchActivityResponseWrapper {
	private List<Activity> activities;
	
	public SearchActivityResponseWrapper() {
		super();
	}

	public SearchActivityResponseWrapper(List<Activity> activities) {
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