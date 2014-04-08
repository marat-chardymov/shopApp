package com.epam.actions;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.epam.forms.ProductsForm;
import com.epam.util.PathsHolder;

public class CatalogAction extends DispatchAction {

	private static final String CATEGORIES = "categories";

	public ActionForward categories(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SAXBuilder saxBuilder = new SAXBuilder();
		InputStream catalogIS = CatalogAction.class
				.getResourceAsStream(PathsHolder.CATALOG);
		Document document = saxBuilder.build(catalogIS);		
		ProductsForm productsForm = (ProductsForm) form;
		productsForm.setDocument(document);
		return mapping.findForward(CATEGORIES);
	}
	public ActionForward subcategories(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SAXBuilder saxBuilder = new SAXBuilder();
		InputStream catalogIS = CatalogAction.class
				.getResourceAsStream(PathsHolder.CATALOG);
		Document document = saxBuilder.build(catalogIS);
		ProductsForm productsForm = (ProductsForm) form;
		productsForm.setDocument(document);
		return mapping.findForward(CATEGORIES);
	}
}