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

public class ProductListAction implements Action {

	public static final String PRODUCT_LIST_PATH = "/xslt/productList.xsl";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		PrintWriter resultWriter = response.getWriter();

		InputStream catalog = CategoriesListAction.class
				.getResourceAsStream("/catalog.xml");

		String catName = request.getParameter("catName");
		String subcatName = request.getParameter("subcatName");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("catName", catName);
		paramsMap.put("subcatName", subcatName);

		RLockTransformerResultPrinter.write(PRODUCT_LIST_PATH, catalog,
				resultWriter, paramsMap);

	}

}
