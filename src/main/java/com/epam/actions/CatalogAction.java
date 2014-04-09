package com.epam.actions;

import com.epam.forms.ProductsForm;
import com.epam.util.PathsHolder;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;

public final class CatalogAction extends DispatchAction {

	private static final String CATEGORIES = "categories";
	private static final String SUBCATEGORIES = "subcategories";
	private static final String PRODUCTS = "products";

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
		return mapping.findForward(SUBCATEGORIES);
	}

	public ActionForward productList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SAXBuilder saxBuilder = new SAXBuilder();
		InputStream catalogIS = CatalogAction.class
				.getResourceAsStream(PathsHolder.CATALOG);
		Document document = saxBuilder.build(catalogIS);

		ProductsForm productsForm = (ProductsForm) form;
		productsForm.setDocument(document);
		return mapping.findForward(PRODUCTS);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Document document = ((ProductsForm) form).getDocument();
		File catalogFile = new File(PathsHolder.PATH_TO_CATALOG);
		XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.output(document, new FileWriter(catalogFile));
		return mapping.findForward(PRODUCTS);
	}
}