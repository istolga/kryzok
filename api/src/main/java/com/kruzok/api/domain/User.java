package com.kruzok.api.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
	@Id
	private String id;

	private List<Device> devices;

	private List<Activity> activities;

	private Date createDate;

	private Date updateDate;

	public User() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public void addDevice(Device device) {
		if (CollectionUtils.isEmpty(devices)) {
			devices = new ArrayList<Device>();
		}
		devices.add(device);
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public void addActivities(Activity activity) {
		if (CollectionUtils.isEmpty(activities)) {
			activities = new ArrayList<Activity>();
		}
		activities.add(activity);
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
