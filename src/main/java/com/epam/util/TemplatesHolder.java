package com.epam.util;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

public class TemplatesHolder {

	private static Map<String, Templates> templatesMap = new ConcurrentHashMap<String, Templates>();
	private static TransformerFactory transformerFactory = TransformerFactory
			.newInstance();

	public static Transformer getTransformer(String keyPath)
			throws TransformerConfigurationException {
		if (templatesMap.containsKey(keyPath)) {
			return templatesMap.get(keyPath).newTransformer();
		} else {
			InputStream inputStream = TemplatesHolder.class
					.getResourceAsStream(keyPath);
			Source source = new StreamSource(inputStream);
			Templates templates = transformerFactory.newTemplates(source);
			templatesMap.put(keyPath, templates);
			return templatesMap.get(keyPath).newTransformer();
		}

	}
}
