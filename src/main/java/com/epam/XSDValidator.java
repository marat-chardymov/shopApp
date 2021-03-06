package com.epam;

import org.xml.sax.SAXException;


import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XSDValidator {
    public static void main(String[] args) throws IOException, SAXException {
        File schemaFile = new File("schema.xsd");
        //InputStream schemaStream = XSDValidator.class.getResourceAsStream("/schema.xsd");
        Source xmlFile = new StreamSource(new File("catalog.xml"));
        //InputStream xmlFileStream = XSDValidator.class.getResourceAsStream("/catalog.xml");
        
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(schemaFile);
        Validator validator = schema.newValidator();
        try {
            validator.validate(xmlFile);
            System.out.println(xmlFile.getSystemId() + " is valid");
        } catch (SAXException e) {
            System.out.println(xmlFile.getSystemId() + " is NOT valid");
            System.out.println("Reason: " + e.getLocalizedMessage());
        }
    }
}
