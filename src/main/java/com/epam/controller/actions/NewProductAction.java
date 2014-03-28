package com.epam.controller.actions;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.Action;
import com.epam.util.HTMLWriter;

public class NewProductAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {		
				
		PrintWriter resultWriter = response.getWriter();
		InputStream styleSheet = NewProductAction.class
				.getResourceAsStream("/xslt/addingPage.xsl");
		InputStream catalog = CategoriesListAction.class
				.getResourceAsStream("/catalog.xml");
		
		String catName=request.getParameter("catName");
		String subcatName=request.getParameter("subcatName");
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("catName", catName);
		paramsMap.put("subcatName", subcatName);		
		HTMLWriter.write(styleSheet, catalog, resultWriter, paramsMap);
	}

}
