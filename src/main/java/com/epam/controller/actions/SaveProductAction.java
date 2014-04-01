package com.epam.controller.actions;

import com.epam.controller.Action;
import com.epam.util.HTMLWriter;
import com.epam.util.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SaveProductAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String catName = request.getParameter("catName");
		String subcatName = request.getParameter("subcatName");

		String model = request.getParameter("model");
		String color = request.getParameter("color");
		String dateOfIssue = request.getParameter("dateOfIssue");
		String producer = request.getParameter("producer");
		String notInStockStr = request.getParameter("notInStock");
		String price = request.getParameter("price");
		boolean notInStock=false;
		if("on".equals(notInStockStr)){
			notInStock=true;
			price="";
		}
//			notInStock="false";
//		}else{
//			notInStock="true";
//		}

		InputStream styleSheet = SaveProductAction.class
				.getResourceAsStream("/xslt/saveProduct.xsl");
		InputStream catalog = SaveProductAction.class
				.getResourceAsStream("/catalog.xml");

		Writer resultWriter = new StringWriter();

		Map<String, Object> transParams = new HashMap<String, Object>();
		transParams.put("catName", catName);
		transParams.put("subcatName", subcatName);
		transParams.put("model", model);
		transParams.put("color", color);
		transParams.put("dateOfIssue", dateOfIssue);
		transParams.put("price", price);
		transParams.put("producer", producer);
		transParams.put("notInStock", notInStock);
		Map<String, Object> errors = new HashMap<String, Object>();

		HTMLWriter.save(styleSheet, catalog, resultWriter, transParams, errors);

		System.out.println(errors.entrySet());
		if (errors.isEmpty()) {
			String pathToCatalog = request.getServletContext().getRealPath(
					"WEB-INF/classes/catalog.xml");
			File catalogFile = new File(pathToCatalog); // "d:/catalog.xml"
			Writer fileWriter = new PrintWriter(catalogFile, "UTF-8");
			fileWriter.write(resultWriter.toString());
			fileWriter.flush();
			fileWriter.close();

			String redirect = "FrontController.do?action=productList&catName="
					+ catName + "&subcatName=" + subcatName;
			response.sendRedirect(redirect);
		} else {
			String forwardPath = "FrontController.do?action=newProduct&catName="
					+ catName + "&subcatName=" + subcatName;
			request.setAttribute("productMap", request.getParameterMap());
			request.setAttribute("errors", errors);
			request.getRequestDispatcher(forwardPath)
					.forward(request, response);
		}

	}

}
