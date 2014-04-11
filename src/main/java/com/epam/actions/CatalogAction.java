package com.epam.actions;

import static com.epam.util.PathsHolder.ADDING_PAGE_PATH;
import static com.epam.util.PathsHolder.CATALOG;
import static com.epam.util.PathsHolder.PATH_TO_CATALOG;
import static com.epam.util.PathsHolder.SAVE_PRODUCT_PATH;

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
		Document document = ((ProductsForm) form).getDocument();
		File catalogFile = new File(PATH_TO_CATALOG);
		Writer fileWrite = new FileWriter(catalogFile);
		new XMLOutputter().output(document, fileWrite);
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
		paramsMap.put("catName", catName);
		paramsMap.put("subcatName", subcatName);

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

		transParams.put("modelError", (Object) modelError);
		transParams.put("colorError", (Object) colorError);
		transParams.put("dateOfIssueError", (Object) dateOfIssueError);
		transParams.put("priceError", (Object) priceError);
		transParams.put("producerError", (Object) producerError);

		Boolean validSkip = false;
		transParams.put("validSkip", validSkip);

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
		readLock.lock();
		try {
			document = saxBuilder.build(catalogFile);
		} finally {
			readLock.unlock();
		}
		ProductsForm productsForm = (ProductsForm) form;
		productsForm.setDocument(document);
	}

	private String indexToCatName(Document document, int catIndex) {
		Element rootElement = document.getRootElement();
		Attribute catNameAttr = rootElement.getChildren().get(catIndex)
				.getAttribute("name");
		return catNameAttr.getValue();
	}

	private String indexToSubcatname(Document document, int catIndex,
			int subcatIndex) {
		Element rootElement = document.getRootElement();
		Attribute subcatNameAtr = rootElement.getChildren().get(catIndex)
				.getChildren().get(subcatIndex).getAttribute("name");
		return subcatNameAtr.getValue();
	}

	private void fillParams(Map<String, Object> params,
			HttpServletRequest request) {
		String catName = request.getParameter("catName");
		String subcatName = request.getParameter("subcatName");

		String model = request.getParameter("model");
		String color = request.getParameter("color");
		String dateOfIssue = request.getParameter("dateOfIssue");
		String producer = request.getParameter("producer");
		String notInStockStr = request.getParameter("notInStock");
		String price = request.getParameter("price");
		boolean notInStock = false;
		if ("on".equals(notInStockStr)) {
			notInStock = true;
			price = "";
		}
		params.put("catName", catName);
		params.put("subcatName", subcatName);
		params.put("model", model);
		params.put("color", color);
		params.put("dateOfIssue", dateOfIssue);
		params.put("price", price);
		params.put("producer", producer);
		params.put("notInStock", notInStock);

	}
}