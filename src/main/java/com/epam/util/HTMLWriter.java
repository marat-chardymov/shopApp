package com.epam.util;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.PrintWriter;

public class HTMLWriter {
	
	public static void write(InputStream stylesheet, InputStream data,
			PrintWriter resultWriter) {
		StreamSource styleSource = new StreamSource(stylesheet);
		Transformer t = null;
		try {
			t = TransformerFactory.newInstance().newTransformer(styleSource);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		Source text = new StreamSource(data);
		StreamResult streamResult = new StreamResult(resultWriter);
		try {
			t.transform(text, streamResult);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
