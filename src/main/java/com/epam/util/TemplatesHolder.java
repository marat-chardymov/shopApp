package com.epam.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import com.epam.controller.actions.CategoriesListAction;
import com.epam.controller.actions.SubcategoriesListAction;

public class TemplatesHolder {

	private static Map<String, Templates> templatesMap = new HashMap<String, Templates>();
	private static TransformerFactory transformerFactory = TransformerFactory
			.newInstance();

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

	public static Transformer getTransformer(String keyPath)
			throws TransformerConfigurationException {
		if (templatesMap.containsKey(keyPath)) {
			return templatesMap.get(keyPath).newTransformer();
		} else {
			InputStream inputStream = TemplatesHolder.class
					.getResourceAsStream(keyPath);
			putTemplate(keyPath, inputStream);
			return templatesMap.get(keyPath).newTransformer();
		}

	}
}
