package com.epam.actions;

import com.epam.forms.ProductsForm;
import com.epam.util.PathsHolder;
import com.epam.util.SingleRWLock;
import com.epam.util.transformation.RLockTransformerResultPrinter;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

public final class CatalogAction extends DispatchAction {

	private static final String CATEGORIES = "categories";
	private static final String SUBCATEGORIES = "subcategories";
	private static final String PRODUCTS = "products";
	private static final String PRODUCTS_LIST_ACTION = "productsListAction";

	public ActionForward categories(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SAXBuilder saxBuilder = new SAXBuilder();
		InputStream catalogIS = CatalogAction.class
				.getResourceAsStream(PathsHolder.CATALOG);
		Document document = saxBuilder.build(catalogIS);

		ProductsForm productsForm = (ProductsForm) form;
		productsForm.setDocument(document);
		return mapping.findForward(CATEGORIES);
	}

	public ActionForward subcategories(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SAXBuilder saxBuilder = new SAXBuilder();
		InputStream catalogIS = CatalogAction.class
				.getResourceAsStream(PathsHolder.CATALOG);
		Document document = saxBuilder.build(catalogIS);

		ProductsForm productsForm = (ProductsForm) form;
		productsForm.setDocument(document);
		return mapping.findForward(SUBCATEGORIES);
	}

	public ActionForward productList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SAXBuilder saxBuilder = new SAXBuilder();
//		InputStream catalogIS = CatalogAction.class
//				.getResourceAsStream(PathsHolder.CATALOG);
		File catalogFile=new File(PathsHolder.PATH_TO_CATALOG);
		Document document = saxBuilder.build(catalogFile);

		ProductsForm productsForm = (ProductsForm) form;
		productsForm.setDocument(document);
		return mapping.findForward(PRODUCTS);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Document document = ((ProductsForm) form).getDocument();
		File catalogFile = new File(PathsHolder.PATH_TO_CATALOG);
		XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.output(document, new FileWriter(catalogFile));
		return mapping.findForward(PRODUCTS);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProductsForm productsForm = (ProductsForm) form;
		int catIndex = productsForm.getCatIndex();
		int subcatIndex = productsForm.getSubcatIndex();
		
		//get category and subcategory names by indexes
		Document document = productsForm.getDocument();
		String catName = indexToCatName(document,catIndex);
		String subcatName = indexToSubcatname(document, catIndex, subcatIndex);
		
		PrintWriter resultWriter = response.getWriter();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("catName", catName);
		paramsMap.put("subcatName", subcatName);

		RLockTransformerResultPrinter.write(PathsHolder.ADDING_PAGE_PATH,
				PathsHolder.CATALOG, resultWriter, paramsMap);
		return null;
	}
	
	public ActionForward saveProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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

		Map<String, Object> transParams = new HashMap<String, Object>();
		transParams.put("catName", catName);
		transParams.put("subcatName", subcatName);
		transParams.put("model", model);
		transParams.put("color", color);
		transParams.put("dateOfIssue", dateOfIssue);
		transParams.put("price", price);
		transParams.put("producer", producer);
		transParams.put("notInStock", notInStock);

		StringBuilder modelError = new StringBuilder();
		StringBuilder colorError = new StringBuilder();
		StringBuilder dateOfIssueError = new StringBuilder();
		StringBuilder priceError = new StringBuilder();
		StringBuilder producerError = new StringBuilder();

		transParams.put("modelError", (Object)modelError);
		transParams.put("colorError", (Object)colorError);
		transParams.put("dateOfIssueError", (Object)dateOfIssueError);
		transParams.put("priceError", (Object)priceError);
		transParams.put("producerError", (Object)producerError);

		// get last modified before read from file and write to buffer
		File catalogFile = new File(PathsHolder.PATH_TO_CATALOG);
		long lastMod = catalogFile.lastModified();

		Boolean validSkip = false;
		transParams.put("validSkip", validSkip);
			
		// read from catalog file write to buffer
//		Writer resultWriter = new StringWriter();
		Writer resultWriter2 = new StringWriter();
//		RLockTransformerResultPrinter.write(PathsHolder.SAVE_PRODUCT_PATH,
//				PathsHolder.PATH_TO_CATALOG, resultWriter, transParams);
		
		RLockTransformerResultPrinter.write(PathsHolder.SAVE_PRODUCT_PATH,
				PathsHolder.CATALOG, resultWriter2, transParams);

		if (noErrors(modelError, colorError, dateOfIssueError, priceError,
				producerError)) {
			Lock writeLock = SingleRWLock.INSTANCE.writeLock();
			writeLock.lock();
			try {
				long lastModCheck = catalogFile.lastModified();
				if (lastModCheck == lastMod) {
					// try to write into the catalog file
					Writer fileWriter = new PrintWriter(catalogFile, "UTF-8");
					fileWriter.write(resultWriter2.toString());
					fileWriter.flush();
					fileWriter.close();
				} else {
					// read from catalog file write to buffer but skip
					// validation
					validSkip = true;
					RLockTransformerResultPrinter.write(PathsHolder.SAVE_PRODUCT_PATH,
							PathsHolder.PATH_TO_CATALOG, resultWriter2, transParams);
					// try to write into the catalog file
					Writer fileWriter = new PrintWriter(catalogFile, "UTF-8");
					fileWriter.write(resultWriter2.toString());
					fileWriter.flush();
					fileWriter.close();
				}

			} finally {
				writeLock.unlock();
			}
//			String redirect = "catalog.do?action=productList";
//			response.sendRedirect(redirect);
			return mapping.findForward(PRODUCTS_LIST_ACTION);
		} else {
			// forward back to adding page with validation errors
			Writer writer = response.getWriter();
			writer.write(resultWriter2.toString());
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
    private String indexToCatName(Document document,int catIndex){
        Element rootElement = document.getRootElement();
        Attribute catNameAttr = rootElement.getChildren().get(catIndex)
                .getAttribute("name");
        return catNameAttr.getValue();
    }
    private String indexToSubcatname(Document document,int catIndex,int subcatIndex){
        Element rootElement = document.getRootElement();
        Attribute subcatNameAtr = rootElement.getChildren().get(catIndex)
                .getChildren().get(subcatIndex).getAttribute("name");
        return subcatNameAtr.getValue();
    }
}