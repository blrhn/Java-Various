package xmlreader.jaxp.sax;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import xmlreader.abstractions.XMLRunner;
import xmlreader.jaxp.models.ChannelJAXP;
import xmlreader.jaxp.sax.handlers.CurrencyHandler;

import java.io.File;
import java.io.IOException;

public class SAXBRunner implements XMLRunner {
    private static final CurrencyHandler currencyHandler = new CurrencyHandler();

    @Override
    public ChannelJAXP run(File file) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        parser.parse(file, currencyHandler);

        return currencyHandler.getChannel();
    }
}
