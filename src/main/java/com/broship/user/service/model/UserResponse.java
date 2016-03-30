package com.broship.user.service.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.broship.user.model.User;

public class UserResponse {

	public static final DateFormat dobFormat = new SimpleDateFormat("yyyy-MM-dd");

	private String name;
	private String dob;
	private int hob;
	private String avatar;
	private String email;
	private String phone;
	private String identity;
	private String gender;

	public UserResponse() {

	}

	public UserResponse(User user) {
		this.name = user.getName();
		if (user.getDob() != null)
			this.dob = dobFormat.format(user.getDob());
		this.hob = user.getHob();
		this.avatar = user.getAvatar();
		this.email = user.getEmail();
		this.phone = user.getPhone();
		this.identity = user.getIdentity();
		this.gender = user.getGender();
	}

	public String getName() {
		return name;
	}

	public String getDob() {
		return dob;
	}

	public int getHob() {
		return hob;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getIdentity() {
		return identity;
	}

	public String getGender() {
		return gender;
	}

}
