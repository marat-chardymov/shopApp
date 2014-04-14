package com.epam.util.holders;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PathsHolder implements ServletContextListener {
	public static final String CATALOG = "/catalog.xml";
	public static final String ADDING_PAGE_PATH = "/xslt/addingPage.xsl";
	public static final String SAVE_PRODUCT_PATH = "/xslt/saveProduct.xsl";
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
