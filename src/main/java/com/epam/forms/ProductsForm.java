package com.epam.forms;

import org.apache.struts.action.ActionForm;
import org.jdom2.Document;

public class ProductsForm extends ActionForm {
	
	private String catName;
	private String subcatName;
    private Document document;
    
    public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getSubcatName() {
		return subcatName;
	}

	public void setSubcatName(String subcatName) {
		this.subcatName = subcatName;
	}

	public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

}
