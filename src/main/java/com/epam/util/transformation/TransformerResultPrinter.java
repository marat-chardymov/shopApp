package com.epam.util.transformation;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.epam.util.TemplatesHolder;

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
