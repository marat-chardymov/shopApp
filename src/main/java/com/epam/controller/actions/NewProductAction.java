package com.epam.controller.actions;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Templates;

import com.epam.controller.Action;
import com.epam.util.HTMLWriter;
import com.epam.util.MapUtil;
import com.epam.util.SingleRWLock;
import com.epam.util.TemplatesHolder;

public class NewProductAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		PrintWriter resultWriter = response.getWriter();
		Templates addingPageTemp = TemplatesHolder.getTemplates("addingPage");
		InputStream catalog = CategoriesListAction.class
				.getResourceAsStream("/catalog.xml");

		String catName = request.getParameter("catName");
		String subcatName = request.getParameter("subcatName");
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("catName", catName);
		paramsMap.put("subcatName", subcatName);

		Map<String, String[]> productMap = (Map<String, String[]>) request
				.getAttribute("productMap");
		Map<String, String> errors = (Map<String, String>) request
				.getAttribute("errors");

		// if productMap is not null,then previous adding failed cause
		// validation errors
		if (productMap != null) {
			Map<String, String> productProps = MapUtil.convert(productMap);
			HTMLWriter.write(addingPageTemp, catalog, resultWriter,
					productProps, errors);

		} else {
			HTMLWriter.write(addingPageTemp, catalog, resultWriter, paramsMap);
		}

	}

}
