package com.broship.user.model;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class App {

	private String id;
	private String name;
	@JsonProperty("fb_app_id")
	private String fbAppId;
	@JsonProperty("fb_app_secret")
	private String fbAppSecret;
	@JsonProperty("configs")
	private HashMap<String, String> versionConfigMap = new HashMap<String, String>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFbAppId() {
		return fbAppId;
	}

	public void setFbAppId(String fbAppId) {
		this.fbAppId = fbAppId;
	}

	public String getFbAppSecret() {
		return fbAppSecret;
	}

	public void setFbAppSecret(String fbAppSecret) {
		this.fbAppSecret = fbAppSecret;
	}

	public HashMap<String, String> getVersionConfigMap() {
		return versionConfigMap;
	}

	public void setVersionConfigMap(HashMap<String, String> versionConfigMap) {
		this.versionConfigMap = versionConfigMap;
	}

}
