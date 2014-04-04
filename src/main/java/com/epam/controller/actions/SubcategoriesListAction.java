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
import com.epam.util.transformation.RLockTransformerResultPrinter;

public class SubcategoriesListAction implements Action {
	
	public static final String SUBCAT_LIST_PATH = "/xslt/subcategoriesList.xsl";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		PrintWriter resultWriter = response.getWriter();

		
		InputStream catalog = SubcategoriesListAction.class
				.getResourceAsStream("/catalog.xml");
		String name = request.getParameter("name");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("name", name);
		RLockTransformerResultPrinter.write(SUBCAT_LIST_PATH, catalog, resultWriter,
				paramsMap);

	}

}
