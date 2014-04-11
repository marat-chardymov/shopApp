package com.epam.forms;

import org.apache.struts.action.ActionForm;
import org.jdom2.Document;

public class ProductsForm extends ActionForm {
	
	private int catIndex;
	private int subcatIndex;
    private Document document;   
    private long lastMod;
    
    public int getCatIndex() {
		return catIndex;
	}

	public void setCatIndex(int catIndex) {
		this.catIndex = catIndex;
	}

	public int getSubcatIndex() {
		return subcatIndex;
	}

	public void setSubcatIndex(int subcatIndex) {
		this.subcatIndex = subcatIndex;
	}

	public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

	public long getLastMod() {
		return lastMod;
	}

	public void setLastMod(long lastMod) {
		this.lastMod = lastMod;
	}
    

}
