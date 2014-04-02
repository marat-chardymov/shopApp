package com.epam.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import com.epam.controller.actions.CategoriesListAction;
import com.epam.controller.actions.SubcategoriesListAction;

public class TemplatesHolder {

	private static Map<String, Templates> templatesMap = new HashMap<String, Templates>();
	private static TransformerFactory transformerFactory = TransformerFactory
			.newInstance();
	
	static {
				
		InputStream categoriesListIS = TemplatesHolder.class
				.getResourceAsStream("/xslt/categoriesList.xsl");
		putTemplate("categoriesList", categoriesListIS);

		InputStream addingPageIS = TemplatesHolder.class
				.getResourceAsStream("/xslt/addingPage.xsl");
		putTemplate("addingPage", addingPageIS);

		InputStream productListIS = TemplatesHolder.class
				.getResourceAsStream("/xslt/productList.xsl");
		putTemplate("productList", productListIS);

		InputStream saveProductIS = TemplatesHolder.class
				.getResourceAsStream("/xslt/saveProduct.xsl");
		putTemplate("saveProduct", saveProductIS);

		InputStream subcategoriesListIS = TemplatesHolder.class
				.getResourceAsStream("/xslt/subcategoriesList.xsl");
		putTemplate("subcategoriesList", subcategoriesListIS);

	}

	private static void putTemplate(String key, InputStream inputStream) {
		Source source = new StreamSource(inputStream);
		Templates templates = null;
		try {
			templates = transformerFactory.newTemplates(source);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		templatesMap.put(key, templates);
	}

	public static Templates getTemplates(String key) {
		return templatesMap.get(key);
	}
}
