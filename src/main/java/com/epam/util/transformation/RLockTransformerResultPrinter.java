package com.epam.util.transformation;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import com.epam.util.SingleRWLock;

public class RLockTransformerResultPrinter {
	public static void write(String stylesheetPath, String filePath,
			Writer resultWriter, Map<String, Object>... paramsMap) {

		Lock readLock = SingleRWLock.INSTANCE.readLock();
		readLock.lock();
		try {
			TransformerResultPrinter.write(stylesheetPath, filePath, resultWriter,
					paramsMap);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			readLock.unlock();
		}
	}
}
