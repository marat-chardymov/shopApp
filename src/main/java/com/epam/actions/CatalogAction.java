package com.epam.actions;

import static com.epam.util.holders.PathsHolder.*;
import static com.epam.util.holders.ParamsHolder.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import com.epam.forms.ProductsForm;
import com.epam.util.SingleRWLock;
import com.epam.util.transformation.TransformerResultPrinter;

public final class CatalogAction extends DispatchAction {

	private static final String CATEGORIES = "categories";
	private static final String SUBCATEGORIES = "subcategories";
	private static final String PRODUCTS = "products";
	private static final String PRODUCTS_LIST_ACTION = "productsListAction";
	private static final String ERROR = "errorPage";
	
	public ActionForward categories(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		readDocAndPutIntoForm(form);
		return mapping.findForward(CATEGORIES);
	}

	public ActionForward subcategories(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		readDocAndPutIntoForm(form);
		return mapping.findForward(SUBCATEGORIES);
	}

	public ActionForward productList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		readDocAndPutIntoForm(form);
		return mapping.findForward(PRODUCTS);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductsForm productsForm = (ProductsForm) form;
		Document document = productsForm.getDocument();
		File catalogFile = new File(PATH_TO_CATALOG);
		Lock writeLock = SingleRWLock.INSTANCE.writeLock();
		writeLock.lock();
		try {
			long lastModCheck = catalogFile.lastModified();
			long lastMod = productsForm.getLastMod();
			if (lastMod != lastModCheck) {
				return mapping.findForward(ERROR);
			} else {
				Writer fileWrite = new FileWriter(catalogFile);
				new XMLOutputter().output(document, fileWrite);
			}
		} finally {
			writeLock.unlock();
		}
		return mapping.findForward(PRODUCTS);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductsForm productsForm = (ProductsForm) form;
		int catIndex = productsForm.getCatIndex();
		int subcatIndex = productsForm.getSubcatIndex();

		Document document = productsForm.getDocument();
		// get category and subcategory names by indexes
		String catName = indexToCatName(document, catIndex);
		String subcatName = indexToSubcatname(document, catIndex, subcatIndex);

		PrintWriter resultWriter = response.getWriter();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put(CAT_NAME, catName);
		paramsMap.put(SUBCAT_NAME, subcatName);

		Lock readLock = SingleRWLock.INSTANCE.readLock();
		readLock.lock();
		try {
			TransformerResultPrinter.write(ADDING_PAGE_PATH, CATALOG,
					resultWriter, paramsMap);
		} finally {
			readLock.unlock();
		}
		return null;
	}

	public ActionForward saveProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> transParams = new HashMap<String, Object>();
		fillParams(transParams, request); // fill params from request

		StringBuilder modelError = new StringBuilder();
		StringBuilder colorError = new StringBuilder();
		StringBuilder dateOfIssueError = new StringBuilder();
		StringBuilder priceError = new StringBuilder();
		StringBuilder producerError = new StringBuilder();

		transParams.put("MODEL_ERROR", (Object) modelError);
		transParams.put("COLOR_ERROR", (Object) colorError);
		transParams.put("DATE_OF_ISSUE_ERROR", (Object) dateOfIssueError);
		transParams.put("PRICE_ERROR", (Object) priceError);
		transParams.put("PRODUCER_ERROR", (Object) producerError);

		Boolean validSkip = false;
		transParams.put("VALID_SKIP", validSkip);

		File catalogFile = new File(PATH_TO_CATALOG);
		// read from catalog file write to buffer
		Writer resultWriter = new StringWriter();

		Lock readLock = SingleRWLock.INSTANCE.readLock();
		readLock.lock();
		long lastMod = 0;
		try {
			// get last modified before read from file and write to buffer
			lastMod = catalogFile.lastModified();
			TransformerResultPrinter.write(SAVE_PRODUCT_PATH, CATALOG,
					resultWriter, transParams);
		} finally {
			readLock.unlock();
		}

		if (noErrors(modelError, colorError, dateOfIssueError, priceError,
				producerError)) {
			Lock writeLock = SingleRWLock.INSTANCE.writeLock();
			writeLock.lock();
			try {
				long lastModCheck = catalogFile.lastModified();
				if (lastModCheck != lastMod) {
					validSkip = true;
					TransformerResultPrinter.write(SAVE_PRODUCT_PATH, CATALOG,
							resultWriter, transParams);
				} else {
					// try to write into the catalog file
					Writer fileWriter = new PrintWriter(catalogFile, "UTF-8");
					fileWriter.write(resultWriter.toString());
					fileWriter.flush();
					fileWriter.close();
				}

			} finally {
				writeLock.unlock();
			}
			return mapping.findForward(PRODUCTS_LIST_ACTION);
		} else {
			// forward back to adding page with validation errors
			Writer pageWriter = response.getWriter();
			pageWriter.write(resultWriter.toString());
		}
		return null;
	}

	private boolean noErrors(StringBuilder... errorsStr) {
		for (StringBuilder err : errorsStr) {
			if (!err.toString().isEmpty()) {
				return false;
			}
		}
		return true;
	}

	private void readDocAndPutIntoForm(ActionForm form) throws JDOMException,
			IOException {
		SAXBuilder saxBuilder = new SAXBuilder();
		File catalogFile = new File(PATH_TO_CATALOG);
		Lock readLock = SingleRWLock.INSTANCE.readLock();
		Document document = null;
		long lastMod;
		readLock.lock();
		try {
			lastMod = catalogFile.lastModified();
			document = saxBuilder.build(catalogFile);
		} finally {
			readLock.unlock();
		}
		ProductsForm productsForm = (ProductsForm) form;
		productsForm.setDocument(document);
		productsForm.setLastMod(lastMod);
	}

	private String indexToCatName(Document document, int catIndex) {
		Element rootElement = document.getRootElement();
		Attribute catNameAttr = rootElement.getChildren().get(catIndex)
				.getAttribute(NAME);
		return catNameAttr.getValue();
	}

	private String indexToSubcatname(Document document, int catIndex,
			int subcatIndex) {
		Element rootElement = document.getRootElement();
		Attribute subcatNameAtr = rootElement.getChildren().get(catIndex)
				.getChildren().get(subcatIndex).getAttribute(NAME);
		return subcatNameAtr.getValue();
	}

	private void fillParams(Map<String, Object> params,
			HttpServletRequest request) {
		String catName = request.getParameter(CAT_NAME);
		String subcatName = request.getParameter(SUBCAT_NAME);

		String model = request.getParameter(MODEL);
		String color = request.getParameter(COLOR);
		String dateOfIssue = request.getParameter(DATE_OF_ISSUE);
		String producer = request.getParameter(PRODUCER);
		String notInStockStr = request.getParameter(NOT_IN_STOCK);
		String price = request.getParameter(PRICE);
		boolean notInStock = false;
		if ("on".equals(notInStockStr)) {
			notInStock = true;
			price = "";
		}
		params.put(CAT_NAME, catName);
		params.put(SUBCAT_NAME, subcatName);
		params.put(MODEL, model);
		params.put(COLOR, color);
		params.put(DATE_OF_ISSUE, dateOfIssue);
		params.put(PRICE, price);
		params.put(PRODUCER, producer);
		params.put(NOT_IN_STOCK, notInStock);

	}
}