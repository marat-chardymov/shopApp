package com.epam.controller.actions;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.controller.Action;
import com.epam.util.PathsHolder;
import com.epam.util.transformation.RLockTransformerResultPrinter;

public class CategoriesListAction implements Action {
	
	public static final String CATEGORIES_LIST_PATH="/xslt/categoriesList.xsl";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		PrintWriter resultWriter = response.getWriter();		
		RLockTransformerResultPrinter.write(CATEGORIES_LIST_PATH, PathsHolder.CATALOG, resultWriter);

	}
}
