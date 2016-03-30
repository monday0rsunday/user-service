package com.broship.user.service;

import com.broship.user.model.User;

public interface IUserDb {

	public void addUser(User user);

	public void updateUser(User user);

	public void updateUserAvatar(long userId, byte[] avatar);

	public byte[] getUserAvatar(long userId);

	public User getUser(long id);

	public User getUser(String username, String provider);

	public long getUserIdByAccessToken(String accessToken);

	public void setUserIdByAccessToken(long userId, String accessToken);
}
