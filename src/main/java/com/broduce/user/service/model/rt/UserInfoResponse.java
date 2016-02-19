package com.broduce.user.service.model.rt;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserInfoResponse {

	@JsonProperty
	private long id;
	@JsonProperty
	private String name;
	@JsonProperty
	private String first_name;
	@JsonProperty
	private String middle_name;
	@JsonProperty
	private String last_name;
	@JsonProperty
	private String birthday;
	@JsonProperty
	private String email;
	@JsonProperty
	private String phone;
	@JsonProperty
	private String gender;
	@JsonProperty
	private String avatar_url;
	@JsonProperty
	private String username;
	@JsonProperty
	private String password;
	@JsonProperty
	private String provider;

	public UserInfoResponse(long id, String name, String first_name, String middle_name, String last_name,
			String birthday, String email, String phone, String gender, String avatar_url, String username,
			String password, String provider) {
		super();
		this.id = id;
		this.name = name;
		this.first_name = first_name;
		this.middle_name = middle_name;
		this.last_name = last_name;
		this.birthday = birthday;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.avatar_url = avatar_url;
		this.username = username;
		this.password = password;
		this.provider = provider;
	}

}
