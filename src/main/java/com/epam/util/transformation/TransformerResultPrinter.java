package com.epam.util.transformation;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.epam.controller.actions.CategoriesListAction;
import com.epam.util.TemplatesHolder;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Map;
import java.util.concurrent.locks.Lock;

public class TransformerResultPrinter {

	public static void write(String stylesheetPath, String filePath,
			Writer resultWriter, Map<String, Object>... paramsMap)
			throws IOException {
		Transformer t = null;
		try {
			t = TemplatesHolder.getTransformer(stylesheetPath);
		} catch (TransformerConfigurationException e1) {
			e1.printStackTrace();
		}		
		InputStream fileIS = TransformerResultPrinter.class
				.getResourceAsStream(filePath);
		Source text = new StreamSource(fileIS);		
		StreamResult streamResult = new StreamResult(resultWriter);
		if (paramsMap.length != 0) {
			// set all maps values as transformer parameters
			for (Map<String, Object> map : paramsMap) {
				for (String key : map.keySet()) {
					t.setParameter(key, map.get(key));
				}
			}
		}
		try {
			t.transform(text, streamResult);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}
