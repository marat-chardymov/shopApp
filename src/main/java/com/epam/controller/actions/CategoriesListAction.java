package com.epam.controller.actions;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Templates;

import com.epam.controller.Action;
import com.epam.util.TransformerResultPrinter;
import com.epam.util.SingleRWLock;
import com.epam.util.TemplatesHolder;

public class CategoriesListAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		PrintWriter resultWriter = response.getWriter();
		String categoriesListPath="/xslt/categoriesList.xsl";
		InputStream catalog = CategoriesListAction.class
				.getResourceAsStream("/catalog.xml");
		TransformerResultPrinter.write(categoriesListPath, catalog, resultWriter);

	}
}
