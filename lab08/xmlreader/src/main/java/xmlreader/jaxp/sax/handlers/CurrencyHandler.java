package xmlreader.jaxp.sax.handlers;

import lombok.Getter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import xmlreader.jaxp.models.ChannelJAXP;
import xmlreader.jaxp.models.ItemJAXP;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// https://stackoverflow.com/questions/4827344/how-to-parse-xml-using-the-sax-parser
// https://docs.oracle.com/en/database/oracle/oracle-database/26/adxdk/XML-parsing-for-Java.html
public class CurrencyHandler extends DefaultHandler {
    @Getter
    private ChannelJAXP channel;

    private StringBuilder element;
    private ItemJAXP currentItem;
    private boolean inItem = false;
    private List<ItemJAXP> tempItems = new ArrayList<>();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME;

    @Override
    public void startElement (String uri, String localName, String qName, Attributes attributes) throws SAXException {
        element = new StringBuilder();
        if (qName.equals("channel")) {
            channel = new ChannelJAXP();
        } else if (qName.equals("item")) {
            currentItem = new ItemJAXP();
            inItem = true;
        }
    }

    @Override
    public void endElement (String uri, String localName, String qName) throws SAXException {
        String text = element.toString().trim();

        if (inItem) {
            mapItem(qName, text);
        } else {
            mapChannel(qName, text);
        }
    }

    private void mapItem(String qName, String text) {
        switch (qName) {
            case "title" -> currentItem.setTitle(text);
            case "link" ->  currentItem.setLink(text);
            case "description" ->  currentItem.setDescription(text);
            case "pubDate" ->  currentItem.setPubDate(convertToDate(text));
            case "baseCurrency" ->  currentItem.setBaseCurrency(text);
            case "baseName" ->  currentItem.setBaseName(text);
            case "targetCurrency" ->  currentItem.setTargetCurrency(text);
            case "targetName" ->  currentItem.setTargetName(text);
            case "exchangeRate" ->  currentItem.setExchangeRate(Double.parseDouble(text));
            case "inverseRate" ->  currentItem.setInverseRate(Double.parseDouble(text));
            case "inverseDescription" ->  currentItem.setInverseDescription(text);
            case "item" ->  {
                tempItems.add(currentItem);
                inItem = false;
            }
            default -> throw new IllegalStateException("Unexpected value: " + qName);
        }

    }

    private void mapChannel(String qName, String text) {
        switch (qName) {
            case "title" -> channel.setTitle(text);
            case "link" ->  channel.setLink(text);
            case "xmlLink" ->  channel.setXmlLink(text);
            case "description" ->  channel.setDescription(text);
            case "language" ->  channel.setLanguage(text);
            case "baseCurrency" ->  channel.setBaseCurrency(text);
            case "pubDate" ->  channel.setPubDate(convertToDate(text));
            case "lastBuildDate" ->  channel.setLastBuildDate(convertToDate(text));
            case "channel" -> channel.setItems(tempItems);
            default -> throw new IllegalStateException("Unexpected value: " + qName);
        }

    }

    private ZonedDateTime convertToDate(String text) {
        return FORMATTER.parse(text, ZonedDateTime::from);
    }

    @Override
    public void characters (char ch[], int start, int length) throws SAXException {
        element.append(ch, start, length);
    }
}
