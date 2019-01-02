package com.moneytransfer;

import com.moneytransfer.service.AccountsService;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.ws.rs.ApplicationPath;

/**
 * Main Class (Starting point) 
 */
@ApplicationPath("/")
public class Application {

	private static Logger log = Logger.getLogger(Application.class);

	public static void main(String[] args) throws Exception {
		// Host service on jetty
		startService();
	}

	private static void startService() throws Exception {
		Server server = new Server(8080);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);
		ServletHolder servletHolder = context.addServlet(ServletContainer.class, "/*");
		servletHolder.setInitParameter("jersey.config.server.provider.classnames",
				 AccountsController.class.getCanonicalName()+ "," + AccountsService.class.getCanonicalName() );
		try {
			server.start();
			server.join();
		} finally {
			server.destroy();
		}
	}

}
