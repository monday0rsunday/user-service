package com.broduce.user.service;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;

public class JettyStorageServer extends Thread {
	private static final Logger logger = Logger
			.getLogger(JettyStorageServer.class);
	private Server server;
	private int port;

	public JettyStorageServer(int port) {
		this.port = port;
		setName("JettyStorageServer");
	}

	public void run() {
		server = new Server(port);
		WebAppContext context = new WebAppContext();
		context.setDescriptor(getClass().getResource("/webapp/WEB-INF/web.xml")
				.toExternalForm());
		context.setResourceBase(getClass().getResource("/webapp")
				.toExternalForm());
		context.setContextPath("/");
		ServletHolder h = new ServletHolder(new HttpServlet30Dispatcher());
		h.setInitParameter("javax.ws.rs.Application",
				MainApplication.class.getCanonicalName());
		context.addServlet(h, "/*");
		context.setParentLoaderPriority(true);

		server.setHandler(context);

		try {
			server.start();
			logger.info(getName() + " started");
			server.join();
		} catch (Exception e) {
			logger.error("", e);
		}
		logger.info(getName() + " stopped");
	}

	@Override
	public void interrupt() {
		logger.info("try stop " + getName());
		super.interrupt();
		if (server != null)
			try {
				server.stop();
			} catch (Exception e) {
				logger.error("", e);
			}
	}
}
