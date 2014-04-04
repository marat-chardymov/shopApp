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
import com.epam.util.MapUtil;
import com.epam.util.transformation.RLockTransformerResultPrinter;

public class NewProductAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		PrintWriter resultWriter = response.getWriter();
		String addingPagePath = "/xslt/addingPage.xsl";
		InputStream catalog = CategoriesListAction.class
				.getResourceAsStream("/catalog.xml");

		String catName = request.getParameter("catName");
		String subcatName = request.getParameter("subcatName");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("catName", catName);
		paramsMap.put("subcatName", subcatName);

		Map<String, String[]> productMap = (Map<String, String[]>) request
				.getAttribute("productMap");
		Map<String, Object> errors = (Map<String, Object>) request
				.getAttribute("errors");

		// if productMap is not null,then previous adding failed cause
		// validation errors
		if (productMap != null) {
			Map<String, Object> productProps = MapUtil.convert(productMap);
			RLockTransformerResultPrinter.write(addingPagePath, catalog,
					resultWriter, productProps, errors);

		} else {
			RLockTransformerResultPrinter.write(addingPagePath, catalog,
					resultWriter, paramsMap);
		}

	}

}
