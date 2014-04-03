package com.epam.controller.actions;

import com.epam.controller.Action;
import com.epam.util.HTMLWriter;
import com.epam.util.SingleRWLock;
import com.epam.util.TemplatesHolder;
import com.epam.util.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Templates;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

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
		boolean notInStock = false;
		if ("on".equals(notInStockStr)) {
			notInStock = true;
			price = "";
		}

		Templates saveProductTemp = TemplatesHolder.getTemplates("saveProduct");
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

		// get last modified before read from file and write to buffer
		String pathToCatalog = request.getServletContext().getRealPath(
				"WEB-INF/classes/catalog.xml");
		File catalogFile = new File(pathToCatalog); // "d:/catalog.xml"
		long lastMod = catalogFile.lastModified();

		Boolean validSkip = false;
		transParams.put("validSkip", validSkip);

		// read from catalog file write to buffer
		HTMLWriter.save(saveProductTemp, catalog, resultWriter, transParams,
				errors);

		if (errors.isEmpty()) {	
			Lock writeLock = SingleRWLock.INSTANCE.writeLock();
			writeLock.lock();
			try {
				long lastModCheck = catalogFile.lastModified();
				if (lastModCheck == lastMod) {
					// try to write into the catalog file
					Writer fileWriter = new PrintWriter(catalogFile, "UTF-8");
					fileWriter.write(resultWriter.toString());
					fileWriter.flush();
					fileWriter.close();
				} else {
					// read from catalog file write to buffer but skip
					// validation
					validSkip = true;
					InputStream catalogIS = SaveProductAction.class
							.getResourceAsStream("/catalog.xml");
					HTMLWriter.save(saveProductTemp, catalog, resultWriter,
							transParams, errors);
					// try to write into the catalog file
					Writer fileWriter = new PrintWriter(catalogFile, "UTF-8");
					fileWriter.write(resultWriter.toString());
					fileWriter.flush();
					fileWriter.close();
				}

			} finally {
				writeLock.unlock();
			}
			String redirect = "FrontController.do?action=productList&catName="
					+ catName + "&subcatName=" + subcatName;
			response.sendRedirect(redirect);
		} else {
			// forward back to adding page with validation errors
			String forwardPath = "FrontController.do?action=newProduct&catName="
					+ catName + "&subcatName=" + subcatName;
			request.setAttribute("productMap", request.getParameterMap());
			request.setAttribute("errors", errors);
			request.getRequestDispatcher(forwardPath)
					.forward(request, response);
		}

	}

}
