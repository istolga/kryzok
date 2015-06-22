package com.kruzok.api.domain;

import java.util.Date;

public class Device {
	private String id;
	private Boolean isWebView;
	private Boolean isIPad;
	private Boolean isIOS;
	private Boolean isAndroid;
	private Boolean isWindowsPhone;
	private String platform;
	private Number version;
	private Date createDate;

	public Device() {
		super();
	}

	public Device(String id) {
		this();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getIsWebView() {
		return isWebView;
	}

	public void setIsWebView(Boolean isWebView) {
		this.isWebView = isWebView;
	}

	public Boolean getIsIPad() {
		return isIPad;
	}

	public void setIsIPad(Boolean isIPad) {
		this.isIPad = isIPad;
	}

	public Boolean getIsIOS() {
		return isIOS;
	}

	public void setIsIOS(Boolean isIOS) {
		this.isIOS = isIOS;
	}

	public Boolean getIsAndroid() {
		return isAndroid;
	}

	public void setIsAndroid(Boolean isAndroid) {
		this.isAndroid = isAndroid;
	}

	public Boolean getIsWindowsPhone() {
		return isWindowsPhone;
	}

	public void setIsWindowsPhone(Boolean isWindowsPhone) {
		this.isWindowsPhone = isWindowsPhone;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Number getVersion() {
		return version;
	}

	public void setVersion(Number version) {
		this.version = version;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "Device [id=" + id + "]";
	}
}
