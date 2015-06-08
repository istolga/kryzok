package com.kruzok.api.rest.activity.versioned.rev1.beans;

import java.io.Serializable;
import java.util.List;

import com.kruzok.api.domain.Activity;

public class SearchActivityResponseWrapperRev1 implements Serializable {
	
	private static final long serialVersionUID = 1943431032471531276L;
	private List<Activity> activities;

	public SearchActivityResponseWrapperRev1() {
		super();
	}

	public SearchActivityResponseWrapperRev1(List<Activity> activities) {
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