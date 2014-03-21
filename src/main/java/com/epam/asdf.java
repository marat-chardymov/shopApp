package com.epam;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

/**
 * Created by Marat_Chardymau on 3/19/14.
 */
public class asdf {
    public static void main(String[] args) throws TransformerException {
        File styleSheet = new File("makehtml.xsl");
        StreamSource styleSource = new StreamSource(styleSheet);
        Transformer t = TransformerFactory.newInstance().newTransformer(styleSource);
        Source text = new StreamSource(new File("staff.xml"));

        t.transform(text, new StreamResult(new File("output.xml")));



    }
}
