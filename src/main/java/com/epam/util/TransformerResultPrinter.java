package com.epam.util;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Map;
import java.util.concurrent.locks.Lock;

public class TransformerResultPrinter {

	public static void write(String keyPath, InputStream data,
			Writer resultWriter, Map<String, Object>... paramsMap)
			throws IOException {
		Transformer t = null;
		try {
			t = TemplatesHolder.getTransformer(keyPath);
		} catch (TransformerConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Source text = new StreamSource(data);
		StreamResult streamResult = new StreamResult(resultWriter);
		if (paramsMap.length != 0) {
			// set all maps values as transformer parameters
			for (Map<String, Object> map : paramsMap) {
				for (String key : map.keySet()) {
					t.setParameter(key, map.get(key));
				}
			}
		}
		Lock readLock = SingleRWLock.INSTANCE.readLock();
		readLock.lock();
		try {
			t.transform(text, streamResult);
		} catch (TransformerException e) {
			e.printStackTrace();
		} finally {
			readLock.unlock();
		}
	}
}
