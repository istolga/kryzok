package com.kruzok.api.domain;

import java.util.Calendar;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "activities")
public class Activity {
	@Id
	private String id;

	String url;

	String title;

	String body;

	String category;

	String city;

	String state;

	String zip;

	String streetAddress;
	
	Date createDate;
	
	Date updateDate;

	public Activity() {
		super();
	}
	
	public Activity( String id) {
		this();
		setId(id);
	}
	
	public Activity(Activity activity) {
		this();
		
		setUrl(activity.getUrl());
		setTitle(activity.getTitle());
		setBody(activity.getBody());
		setCategory(activity.getCategory());
		setCity(activity.getCity());
		setState(activity.getState());
		setZip(activity.getZip());
		setStreetAddress(activity.getStreetAddress());
		
		Date today = Calendar.getInstance().getTime();
		setCreateDate(today);
		setUpdateDate(today);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Activity [id=" + id + ", url=" + url + ", title=" + title
				+ ", body=" + body + ", category=" + category + ", city="
				+ city + ", state=" + state + ", zip=" + zip
				+ ", streetAddress=" + streetAddress + "]";
	}
}
