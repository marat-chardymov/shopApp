package com.epam.controller;

import com.epam.controller.actions.CategoriesListAction;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ActionFactory {

	public static Map<String, Action> actions = new HashMap<String, Action>();

	static {
		actions.put("categories", new CategoriesListAction());
	}

	public static Action getAction(HttpServletRequest request) {
		return actions.get(request.getParameter("action"));
	}
}