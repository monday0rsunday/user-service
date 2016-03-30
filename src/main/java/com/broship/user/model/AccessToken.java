package com.broship.user.model;

import java.util.ArrayList;
import java.util.List;

public class AccessToken {

	private long userId;
	private String appId;
	private String deviceId;
	private String provider;
	private String os;
	private long expiresIn;
	private long expiresAt;
	private List<String> scopeList;

	public AccessToken() {
		scopeList = new ArrayList<String>();
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public long getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(long expiresAt) {
		this.expiresAt = expiresAt;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public List<String> getScopeList() {
		return scopeList;
	}

	public void setScopeList(List<String> scopeList) {
		this.scopeList = scopeList;
	}

}
