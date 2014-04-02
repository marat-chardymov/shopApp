package com.epam.util;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Map;
import java.util.concurrent.locks.Lock;

public class HTMLWriter {

	public static void write(Templates templates, InputStream data,
			Writer resultWriter, Map<String, String>... paramsMap)
			throws IOException {
		Transformer t = null;
		try {
			t = templates.newTransformer();
		} catch (TransformerConfigurationException e1) {			
			e1.printStackTrace();
		}
		Source text = new StreamSource(data);
		StreamResult streamResult = new StreamResult(resultWriter);
		if (paramsMap.length != 0) {
			// set all maps values as transformer parameters
			for (Map<String, String> map : paramsMap) {
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

	public static void save(Templates templates, InputStream data,
			Writer resultWriter, Map<String, Object>... paramsMap)
			throws IOException {
		Transformer t = null;
		try {
			t = templates.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		Source text = new StreamSource(data);
		StreamResult streamResult = new StreamResult(resultWriter);
		if (paramsMap.length != 0) {
			for (String key : paramsMap[0].keySet()) {
				t.setParameter(key, paramsMap[0].get(key));
			}
		}
		t.setParameter("errors", (Object) paramsMap[1]);

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
