package com.broduce.user.service;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.broduce.user.service.model.User;

public class UserDbTest {

	private static UserDb userDb = null;
	private static SecureRandom random = null;

	@BeforeClass
	public static void setup() {
		try {
			Config.load(UserDbTest.class
					.getResourceAsStream("/config.properties"));
		} catch (Exception e) {
			Logger.getLogger(UserDb.class).error("load test config failed", e);
		}
		userDb = new UserDb(new Config());
		random = new SecureRandom();
	}

	public User createRandomUser() {
		User user = new User();
		user.setName("random name " + Math.random());
		user.setFirstName("random first name");
		user.setMiddleName("random middle name");
		user.setLastName("random last name");
		user.setUsername("randomusername" + (int) (Math.random() * 100000));
		user.setPassword("random password");
		user.setProvider("facebook");
		user.setRole("asker");
		user.setChatUsername(user.getProvider().replaceAll("[^a-z0-9]", "")
				+ user.getUsername());
		user.setChatPassword(new BigInteger(130, random).toString(8).substring(
				0, 16));
		return user;
	}

	@Test
	public void testAddUser() {
		User user = createRandomUser();
		try {
			User addedUser = userDb.addUser(user);
			assertEquals(user.getName(), addedUser.getName());
			assertEquals(user.getFirstName(), addedUser.getFirstName());
			assertEquals(user.getMiddleName(), addedUser.getMiddleName());
			assertEquals(user.getLastName(), addedUser.getLastName());
			assertEquals(user.getUsername(), addedUser.getUsername());
			assertEquals(user.getPassword(), addedUser.getPassword());
			assertEquals(user.getProvider(), addedUser.getProvider());
			assertTrue(addedUser.getChatUsername().equals(
					addedUser.getProvider() + addedUser.getUsername()));
			assertTrue(addedUser.getChatPassword().matches("[0-9]{16}"));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	public void testUpdateUser() {
		User user = createRandomUser();
		try {
			User addedUser = userDb.addUser(user);
			addedUser.setFirstName("new firstname");
			User updatedUser = userDb.updateUser(addedUser);
			assertEquals(addedUser.getName(), updatedUser.getName());
			assertEquals(addedUser.getFirstName(), updatedUser.getFirstName());
			assertEquals(addedUser.getMiddleName(), updatedUser.getMiddleName());
			assertEquals(addedUser.getLastName(), updatedUser.getLastName());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
