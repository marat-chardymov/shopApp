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

	public static void write(InputStream stylesheet, InputStream data,
			Writer resultWriter, Map<String, String>... paramsMap)
			throws IOException {
		StreamSource styleSource = new StreamSource(stylesheet);
		Transformer t = null;
		try {
			t = TransformerFactory.newInstance().newTransformer(styleSource);
			//Template template=TransformerFactory.newInstance().newTemplates(styleSource);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			readLock.unlock();
		}
	}

	public static void save(InputStream stylesheet, InputStream data,
			Writer resultWriter, Map<String, Object>... paramsMap)
			throws IOException {
		StreamSource styleSource = new StreamSource(stylesheet);
		Transformer t = null;
		try {
			t = TransformerFactory.newInstance().newTransformer(styleSource);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			readLock.unlock();
		}
	}

}
