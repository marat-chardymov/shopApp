package com.epam.forms;

import org.apache.struts.action.ActionForm;
import org.jdom.Document;

public class ProductsForm extends ActionForm {

    private Document document;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

}
