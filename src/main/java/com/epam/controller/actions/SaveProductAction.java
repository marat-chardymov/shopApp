package com.epam.controller.actions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.Action;
import com.epam.entities.Product;
import com.epam.util.HTMLWriter;

public class SaveProductAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String catName = request.getParameter("catName");
		String subcatName = request.getParameter("subcatName");

		String model = request.getParameter("model");
		String color = request.getParameter("color");
		String dateOfIssue = request.getParameter("dateOfIssue");
		String price = request.getParameter("price");
		String producer = request.getParameter("producer");
		String notInStock = request.getParameter("notInStock");
		
		InputStream styleSheet = SaveProductAction.class
				.getResourceAsStream("/xslt/saveProduct.xsl");
		InputStream catalog = SaveProductAction.class
				.getResourceAsStream("/catalog.xml");
			
		Writer resultWriter = new StringWriter();

		Map<String, String> transParams = new HashMap<String, String>();
		transParams.put("catName", catName);
		transParams.put("subcatName", subcatName);
		transParams.put("model", model);
		transParams.put("color", color);
		transParams.put("dateOfIssue", dateOfIssue);
		transParams.put("price", price);
		transParams.put("producer", producer);
		transParams.put("notInStock", notInStock);
		HTMLWriter.write(styleSheet, catalog, resultWriter, transParams);
		

		String pathToCatalog = request.getServletContext().getRealPath("WEB-INF/classes/catalog.xml");	
		File catalogFile=new File(pathToCatalog); //"d:/catalog.xml"
		Writer fileWriter = new PrintWriter(catalogFile, "UTF-8");
		fileWriter.write(resultWriter.toString());
		fileWriter.flush();
		fileWriter.close();
		
		String redirect = "FrontController.do?action=productList&catName="+catName+"&subcatName="+
				subcatName;
			response.sendRedirect(redirect);
	}

}
