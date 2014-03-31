package com.epam.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2059060746981975709L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Action action = ActionFactory.getAction(request);
		action.execute(request, response);
	}
}