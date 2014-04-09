package com.epam.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PathsHolder implements ServletContextListener {
	public static final String CATALOG = "/catalog.xml";
	public static String PATH_TO_CATALOG = null;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		PATH_TO_CATALOG = event.getServletContext().getRealPath(
				"WEB-INF/classes/catalog.xml");
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}

}
