package xmlreader.xslt;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.StringWriter;

public class XSLTTransformer {
    private XSLTTransformer() {}

    public static String run(File xml, File style) throws TransformerException {
        Source styleSource = new StreamSource(style);
        Source xmlSource = new StreamSource(xml);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(styleSource);

        StringWriter writer = new StringWriter();
        transformer.transform(xmlSource, new StreamResult(writer));

        return writer.toString();
    }
}
