package com.epam.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.epam.controller.actions.CategoriesListAction;
import com.epam.controller.actions.NewProductAction;
import com.epam.controller.actions.ProductListAction;
import com.epam.controller.actions.SaveProductAction;
import com.epam.controller.actions.SubcategoriesListAction;

public class ActionFactory {

	public static Map<String, Action> actions = new HashMap<String, Action>();

	static {
		actions.put("categoriesList", new CategoriesListAction());
		actions.put("subcategoriesList", new SubcategoriesListAction());
		actions.put("productList", new ProductListAction());
		actions.put("newProduct", new NewProductAction());
		actions.put("saveProduct", new SaveProductAction());
	}

	public static Action getAction(HttpServletRequest request) {
		return actions.get(request.getParameter("action"));
	}
}