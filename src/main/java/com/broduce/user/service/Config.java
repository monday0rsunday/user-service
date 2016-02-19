package com.broduce.user.service;

import java.io.InputStream;
import java.util.Properties;

public class Config {
	public static final String DB_USERNAME = "db.username";
	public static final String DB_PASSWORD = "db.password";
	public static final String DB_URL = "db.url";
	public static final String DB_POOL_NAME = "db.pool.name";
	public static final String DB_POOL_MINPOOL = "db.pool.minpool";
	public static final String DB_POOL_MAXPOOL = "db.pool.maxpool";
	public static final String DB_POOL_MAXSIZE = "db.pool.maxsize";
	public static final String DB_POOL_IDLE_TIMEOUT = "db.pool.idle_timeout";
	public static final String HTTP_PORT = "http.port";

	public static String dbUsername = "anyquiz";
	public static String dbPassword = "anyquiz";
	public static String dbUrl = "jdbc:mysql://192.168.1.113:3306/ztry";
	public static String dbPoolName = "anyquiz";
	public static int dbPoolMinpool = 5;
	public static int dbPoolMaxpool = 20;
	public static int dbPoolMaxsize = 50;
	public static long dbPoolIdleTimeout = 3600;
	public static int httpPort = 8888;

	public static void load(InputStream is) throws Exception {
		Properties properties = new Properties();
		properties.load(is);
		dbUsername = properties.getProperty(DB_USERNAME, dbUsername);
		dbPassword = properties.getProperty(DB_PASSWORD, dbPassword);
		dbUrl = properties.getProperty(DB_URL, dbUrl);
		dbPoolName = properties.getProperty(DB_POOL_NAME, dbPoolName);
		dbPoolMinpool = Integer.parseInt(properties.getProperty(DB_POOL_MINPOOL, dbPoolMinpool + ""));
		dbPoolMaxpool = Integer.parseInt(properties.getProperty(DB_POOL_MAXPOOL, dbPoolMaxpool + ""));
		dbPoolMaxsize = Integer.parseInt(properties.getProperty(DB_POOL_MAXSIZE, dbPoolMaxsize + ""));
		dbPoolIdleTimeout = Long.parseLong(properties.getProperty(DB_POOL_IDLE_TIMEOUT, dbPoolIdleTimeout + ""));
		httpPort = Integer.parseInt(properties.getProperty(HTTP_PORT, httpPort + ""));
	}

	public static String getDbUsername() {
		return dbUsername;
	}

	public static void setDbUsername(String dbUsername) {
		Config.dbUsername = dbUsername;
	}

	public static String getDbPassword() {
		return dbPassword;
	}

	public static void setDbPassword(String dbPassword) {
		Config.dbPassword = dbPassword;
	}

	public static String getDbUrl() {
		return dbUrl;
	}

	public static void setDbUrl(String dbUrl) {
		Config.dbUrl = dbUrl;
	}

	public static String getDbPoolName() {
		return dbPoolName;
	}

	public static void setDbPoolName(String dbPoolName) {
		Config.dbPoolName = dbPoolName;
	}

	public static int getDbPoolMinpool() {
		return dbPoolMinpool;
	}

	public static void setDbPoolMinpool(int dbPoolMinpool) {
		Config.dbPoolMinpool = dbPoolMinpool;
	}

	public static int getDbPoolMaxpool() {
		return dbPoolMaxpool;
	}

	public static void setDbPoolMaxpool(int dbPoolMaxpool) {
		Config.dbPoolMaxpool = dbPoolMaxpool;
	}

	public static int getDbPoolMaxsize() {
		return dbPoolMaxsize;
	}

	public static void setDbPoolMaxsize(int dbPoolMaxsize) {
		Config.dbPoolMaxsize = dbPoolMaxsize;
	}

	public static long getDbPoolIdleTimeout() {
		return dbPoolIdleTimeout;
	}

	public static void setDbPoolIdleTimeout(long dbPoolIdleTimeout) {
		Config.dbPoolIdleTimeout = dbPoolIdleTimeout;
	}

	public static int getHttpPort() {
		return httpPort;
	}

	public static void setHttpPort(int httpPort) {
		Config.httpPort = httpPort;
	}

}
