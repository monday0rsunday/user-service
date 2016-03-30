package com.broship.user.service;

import com.basho.riak.client.api.RiakClient;
import com.broship.user.model.User;

public class RiakUserDb implements IUserDb {

	private RiakClient riakClient;

	public RiakUserDb(RiakClient riakClient) {
		this.riakClient = riakClient;
	}

	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUserAvatar(long userId, byte[] avatar) {
		// TODO Auto-generated method stub

	}

	@Override
	public byte[] getUserAvatar(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(String username, String provider) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getUserIdByAccessToken(String accessToken) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setUserIdByAccessToken(long userId, String accessToken) {
		// TODO Auto-generated method stub

	}

}
