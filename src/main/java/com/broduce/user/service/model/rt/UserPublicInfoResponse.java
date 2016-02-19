package com.broduce.user.service.model.rt;

import com.broduce.user.service.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserPublicInfoResponse {

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
	private String gender;
	@JsonProperty
	private String avatar_url;

	public UserPublicInfoResponse(long id, String name, String first_name, String middle_name, String last_name,
			String gender, String avatar_url) {
		super();
		this.id = id;
		this.name = name;
		this.first_name = first_name;
		this.middle_name = middle_name;
		this.last_name = last_name;
		this.gender = gender;
		this.avatar_url = avatar_url;
	}

	public UserPublicInfoResponse(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.first_name = user.getFirstName();
		this.middle_name = user.getMiddleName();
		this.last_name = user.getLastName();
		this.gender = user.getGender();
		this.avatar_url = user.getAvatarUrl();
	}
}
