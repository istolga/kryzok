package com.kruzok.api.domain;

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

	public Activity() {
		super();
	}
	
	public Activity(Activity activity) {
		super();
		
		setId(activity.getId());
		setUrl(activity.getUrl());
		setTitle(activity.getTitle());
		setBody(activity.getBody());
		setCategory(activity.getCategory());
		setCity(activity.getCity());
		setState(activity.getState());
		setZip(activity.getZip());
		setStreetAddress(activity.getStreetAddress());
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

	@Override
	public String toString() {
		return "Activity [id=" + id + ", url=" + url + ", title=" + title
				+ ", body=" + body + ", category=" + category + ", city="
				+ city + ", state=" + state + ", zip=" + zip
				+ ", streetAddress=" + streetAddress + "]";
	}
}
