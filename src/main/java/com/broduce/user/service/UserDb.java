package com.broduce.user.service;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;

import org.apache.log4j.Logger;

import com.broduce.user.service.model.User;

import snaq.db.ConnectionPool;

public class UserDb {

	private static final Logger logger = Logger.getLogger(UserDb.class);
	private ConnectionPool pool;

	public UserDb(Config config) {
		try {
			Class<?> c = Class.forName("com.mysql.jdbc.Driver");
			Driver driver = (Driver) c.newInstance();
			DriverManager.registerDriver(driver);
			pool = new ConnectionPool(config.getDbPoolName(), config.getDbPoolMinpool(), config.getDbPoolMaxpool(),
					config.getDbPoolMaxsize(), config.getDbPoolIdleTimeout(), config.getDbUrl(), config.getDbUsername(),
					config.getDbPassword());
		} catch (Exception e) {
			logger.error("init driver error", e);
		}
	}

	public User addUser(User user) throws Exception {
		User result = null;
		try (Connection con = pool.getConnection();
				PreparedStatement insUserStm = con.prepareStatement(
						"INSERT IGNORE INTO quiz_user (name,first_name,middle_name,last_name,birthday,email,phone,gender,avatar_url,username,password,provider,chat_username) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)",
						PreparedStatement.RETURN_GENERATED_KEYS);
				PreparedStatement insChatStm = con
						.prepareStatement("INSERT IGNORE INTO users (username, password, role) VALUES(?,?,?)");
				PreparedStatement insTagStm = con
						.prepareStatement("INSERT IGNORE INTO quiz_user_htag (jid, tag) VALUES(?,?)");
				PreparedStatement getUserStm = con.prepareStatement(
						"SELECT * FROM quiz_user LEFT JOIN users ON quiz_user.chat_username = users.username WHERE id=?")) {
			con.setAutoCommit(false);
			Savepoint savepoint = con.setSavepoint();
			try {
				insChatStm.setString(1, user.getChatUsername());
				insChatStm.setString(2, user.getChatPassword());
				insChatStm.setString(3, user.getRole());

				int idx = 0;
				insUserStm.setString(++idx, user.getName());
				insUserStm.setString(++idx, user.getFirstName());
				insUserStm.setString(++idx, user.getMiddleName());
				insUserStm.setString(++idx, user.getLastName());
				insUserStm.setString(++idx, user.getBirthday());
				insUserStm.setString(++idx, user.getEmail());
				insUserStm.setString(++idx, user.getPhone());
				insUserStm.setString(++idx, user.getGender());
				insUserStm.setString(++idx, user.getAvatarUrl());
				insUserStm.setString(++idx, user.getUsername());
				insUserStm.setString(++idx, user.getPassword());
				insUserStm.setString(++idx, user.getProvider());
				insUserStm.setString(++idx, user.getChatUsername());

				insTagStm.setString(1, user.getChatUsername());
				insTagStm.setString(2, "any");

				insChatStm.executeUpdate();
				insUserStm.executeUpdate();
				insTagStm.executeUpdate();
				con.commit();

				ResultSet idRs = insUserStm.getGeneratedKeys();
				if (idRs.next()) {
					long id = idRs.getLong(1);
					getUserStm.setLong(1, id);
					ResultSet rs = getUserStm.executeQuery();
					if (rs.next()) {
						result = new User();
						result.setId(rs.getLong("id"));
						result.setName(rs.getString("name"));
						result.setFirstName(rs.getString("first_name"));
						result.setMiddleName(rs.getString("middle_name"));
						result.setLastName(rs.getString("last_name"));
						result.setBirthday(rs.getString("birthday"));
						result.setEmail(rs.getString("email"));
						result.setPhone(rs.getString("phone"));
						result.setGender(rs.getString("gender"));
						result.setAvatarUrl(rs.getString("avatar_url"));
						result.setUsername(rs.getString("username"));
						result.setPassword(rs.getString("password"));
						result.setProvider(rs.getString("provider"));
						result.setChatUsername(rs.getString("chat_username"));
						result.setChatPassword(rs.getString("users.password"));
					}
					rs.close();
				}
			} catch (SQLException e) {
				logger.error("roll back transaction", e);
				con.rollback(savepoint);
			}
		}
		return result;
	}

	public User getUser(long id) throws Exception {
		User result = null;
		try (Connection con = pool.getConnection();
				PreparedStatement getUserStm = con.prepareStatement(
						"SELECT * FROM quiz_user LEFT JOIN users ON quiz_user.chat_username = users.username WHERE id=?");) {
			con.setAutoCommit(false);
			try {
				getUserStm.setLong(1, id);
				ResultSet rs = getUserStm.executeQuery();
				if (rs.next()) {
					result = new User();
					result.setId(rs.getLong("id"));
					result.setName(rs.getString("name"));
					result.setFirstName(rs.getString("first_name"));
					result.setMiddleName(rs.getString("middle_name"));
					result.setLastName(rs.getString("last_name"));
					result.setBirthday(rs.getString("birthday"));
					result.setEmail(rs.getString("email"));
					result.setPhone(rs.getString("phone"));
					result.setGender(rs.getString("gender"));
					result.setAvatarUrl(rs.getString("avatar_url"));
					result.setUsername(rs.getString("username"));
					result.setPassword(rs.getString("password"));
					result.setProvider(rs.getString("provider"));
					result.setChatUsername(rs.getString("chat_username"));
					result.setChatPassword(rs.getString("users.password"));
				}
				rs.close();
			} catch (SQLException e) {
				logger.error("get user problem", e);
			}
		}
		return result;
	}

	public User getUser(String username, String provider) throws Exception {
		User result = null;
		try (Connection con = pool.getConnection();
				PreparedStatement getUserStm = con.prepareStatement(
						"SELECT * FROM quiz_user LEFT JOIN users ON quiz_user.chat_username = users.username WHERE quiz_user.username=? AND provider=?");) {
			con.setAutoCommit(false);
			try {
				getUserStm.setString(1, username);
				getUserStm.setString(2, provider);
				ResultSet rs = getUserStm.executeQuery();
				if (rs.next()) {
					result = new User();
					result.setId(rs.getLong("id"));
					result.setName(rs.getString("name"));
					result.setFirstName(rs.getString("first_name"));
					result.setMiddleName(rs.getString("middle_name"));
					result.setLastName(rs.getString("last_name"));
					result.setBirthday(rs.getString("birthday"));
					result.setEmail(rs.getString("email"));
					result.setPhone(rs.getString("phone"));
					result.setGender(rs.getString("gender"));
					result.setAvatarUrl(rs.getString("avatar_url"));
					result.setUsername(rs.getString("username"));
					result.setPassword(rs.getString("password"));
					result.setProvider(rs.getString("provider"));
					result.setChatUsername(rs.getString("chat_username"));
					result.setChatPassword(rs.getString("users.password"));
				}
				rs.close();
			} catch (SQLException e) {
				logger.error("get user problem", e);
			}
		}
		return result;
	}

	public User updateUser(User user) throws Exception {
		User result = null;
		try (Connection con = pool.getConnection();
				PreparedStatement upUserStm = con.prepareStatement(
						"UPDATE quiz_user SET name=?, first_name=?, middle_name=?, last_name=?, birthday=?, email=?, phone=?, gender=?, avatar_url=?, username=?, password=?, provider=? WHERE id=?");
				PreparedStatement getUserStm = con.prepareStatement(
						"SELECT * FROM quiz_user LEFT JOIN users ON quiz_user.chat_username = users.username WHERE id=?");) {
			con.setAutoCommit(false);
			Savepoint savepoint = con.setSavepoint();
			try {
				int idx = 0;
				upUserStm.setString(++idx, user.getName());
				upUserStm.setString(++idx, user.getFirstName());
				upUserStm.setString(++idx, user.getMiddleName());
				upUserStm.setString(++idx, user.getLastName());
				upUserStm.setString(++idx, user.getBirthday());
				upUserStm.setString(++idx, user.getEmail());
				upUserStm.setString(++idx, user.getPhone());
				upUserStm.setString(++idx, user.getGender());
				upUserStm.setString(++idx, user.getAvatarUrl());
				upUserStm.setString(++idx, user.getUsername());
				upUserStm.setString(++idx, user.getPassword());
				upUserStm.setString(++idx, user.getProvider());
				upUserStm.setLong(++idx, user.getId());
				upUserStm.executeUpdate();
				con.commit();
				getUserStm.setLong(1, user.getId());
				ResultSet rs = getUserStm.executeQuery();
				if (rs.next()) {
					result = new User();
					result.setId(rs.getLong("id"));
					result.setName(rs.getString("name"));
					result.setFirstName(rs.getString("first_name"));
					result.setMiddleName(rs.getString("middle_name"));
					result.setLastName(rs.getString("last_name"));
					result.setBirthday(rs.getString("birthday"));
					result.setEmail(rs.getString("email"));
					result.setPhone(rs.getString("phone"));
					result.setGender(rs.getString("gender"));
					result.setAvatarUrl(rs.getString("avatar_url"));
					result.setUsername(rs.getString("username"));
					result.setPassword(rs.getString("password"));
					result.setProvider(rs.getString("provider"));
					result.setChatUsername(rs.getString("chat_username"));
					result.setChatPassword(rs.getString("users.password"));
				}
				rs.close();
			} catch (SQLException e) {
				logger.error("roll back transaction", e);
				con.rollback(savepoint);
			}
		}
		return result;
	}

}
