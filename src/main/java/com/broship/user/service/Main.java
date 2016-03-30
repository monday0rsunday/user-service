package com.broship.user.service;

import java.io.FileInputStream;

import org.apache.log4j.Logger;

public class Main {

	public static void main(String args[]) {
		System.setProperty("log4j.configurationFile", "./conf/log4j.properties");
		try {
			Config.load(new FileInputStream("./conf/config.properties"));
		} catch (Exception e) {
			Logger.getLogger(Main.class).error("load config error", e);
		}
		JettyStorageServer server = new JettyStorageServer(Config.getHttpPort());
		server.start();
	}
}
