package com.broship.user.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessTokenResponse {

	@JsonProperty("access_token")
	private String accessToken;
	private String scope;
	@JsonProperty("token_type")
	private String tokenType;
	@JsonProperty("expires_in")
	private long expiresIn;
	@JsonProperty("expires_at")
	private long expiresAt;
	@JsonProperty("user_id")
	private long userId;
	@JsonProperty("refresh_token")
	private String refreshToken;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
