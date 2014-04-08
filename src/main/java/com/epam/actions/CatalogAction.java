package com.epam.actions;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

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
		Element rootNode = document.getRootElement();
		List list = rootNode.getChildren();
		for (int i = 0; i < list.size(); i++) {

			Element node = (Element) list.get(i);

			System.out
					.println(node.getAttributeValue("name"));

		}
		ProductsForm productsForm = (ProductsForm) form;
		productsForm.setDocument(document);
		return mapping.findForward(CATEGORIES);
	}
}