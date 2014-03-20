package com.epam;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

/**
 * Created by Marat_Chardymau on 3/19/14.
 */
public class asdf {
    public static void main(String[] args) throws TransformerConfigurationException {
        File styleSheet = new File("makehtml.xsl");
        StreamSource styleSource = new StreamSource(styleSheet);
        Transformer t = TransformerFactory.newInstance().newTransformer(styleSource);
        //t.transform(source, result);



    }
}
