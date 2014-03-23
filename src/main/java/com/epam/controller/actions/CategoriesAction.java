package com.epam.controller.actions;

import com.epam.controller.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class CategoriesAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
//        File styleSheet = new File("makehtml.xsl");
        InputStream styleSheet = CategoriesAction.class.getResourceAsStream("/makehtml.xsl");
        StreamSource styleSource = new StreamSource(styleSheet);
        Transformer t = null;
        try {
            t = TransformerFactory.newInstance().newTransformer(styleSource);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        InputStream staffFile = CategoriesAction.class.getResourceAsStream("/staff.xml");
        Source text = new StreamSource(staffFile);

        PrintWriter writer=response.getWriter();
        StreamResult streamResult=new StreamResult(writer);
        try {
            t.transform(text, streamResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }
}
