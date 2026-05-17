package xmlreader.jaxp.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import xmlreader.abstractions.XMLRunner;
import xmlreader.jaxp.models.ChannelJAXP;
import xmlreader.jaxp.models.ItemJAXP;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// https://www.oracle.com/java/technologies/jaxp-doc-object-model.html
// https://qaautomation.expert/2025/10/17/how-to-read-xml-using-java-dom-parser/
// https://docs.oracle.com/javase/tutorial/jaxp/dom/readingXML.html
public class DOMRunner implements XMLRunner {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME;

    @Override
    public ChannelJAXP run(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();

        return buildChannel(doc);
    }

    private ChannelJAXP buildChannel(Document doc) {
        ChannelJAXP channel = new ChannelJAXP();
        List<ItemJAXP> tempItems = new ArrayList<>();

        Element root = doc.getDocumentElement();

        channel.setTitle((getTagValue("title", root)));
        channel.setLink((getTagValue("link", root)));
        channel.setXmlLink((getTagValue("xmlLink", root)));
        channel.setDescription((getTagValue("description", root)));
        channel.setLanguage((getTagValue("language", root)));
        channel.setBaseCurrency((getTagValue("baseCurrency", root)));
        channel.setPubDate(convertToDate((getTagValue("pubDate", root))));
        channel.setLastBuildDate(convertToDate((getTagValue("lastBuildDate", root))));

        NodeList itemList =  doc.getElementsByTagName("item");
        for (int i = 0; i < itemList.getLength(); i++) {
            Node subnode = itemList.item(i);
            if (subnode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) subnode;
                ItemJAXP item = new ItemJAXP();

                item.setTitle(getTagValue("title", element));
                item.setLink(getTagValue("link", element));
                item.setDescription(getTagValue("description", element));
                item.setPubDate(convertToDate(getTagValue("pubDate", element)));
                item.setBaseCurrency(getTagValue("baseCurrency", element));
                item.setBaseName(getTagValue("baseName", element));
                item.setTargetCurrency(getTagValue("targetCurrency", element));
                item.setTargetName(getTagValue("targetName", element));
                item.setExchangeRate(Double.parseDouble(getTagValue("exchangeRate", element)));
                item.setInverseRate(Double.parseDouble(getTagValue("inverseRate", element)));
                item.setInverseDescription(getTagValue("inverseDescription", element));

                tempItems.add(item);
            }
        }

        channel.setItems(tempItems);

        return channel;
    }

    private String getTagValue(String tag, Element element) {
        return element.getElementsByTagName(tag).item(0).getTextContent();
    }

    private ZonedDateTime convertToDate(String text) {
        return FORMATTER.parse(text, ZonedDateTime::from);
    }
}
