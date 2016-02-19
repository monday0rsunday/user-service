package com.broduce.user.service.model.rt;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionResponse {

	@JsonProperty
	private String token;
	@JsonProperty
	private long last_signin;

	public SessionResponse(String token, long last_signin) {
		super();
		this.token = token;
		this.last_signin = last_signin;
	}

}
