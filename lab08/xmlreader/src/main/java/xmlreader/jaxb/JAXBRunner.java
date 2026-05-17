package xmlreader.jaxb;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import xmlreader.jaxb.models.ChannelJAXB;

import java.io.File;

// https://www.oracle.com/java/technologies/jaxb.html
public final class JAXBRunner {
    private JAXBRunner() {}

    public static ChannelJAXB run(File file) throws JAXBException {
        final JAXBContext context = JAXBContext.newInstance(ChannelJAXB.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();

        return (ChannelJAXB) unmarshaller.unmarshal(file);
    }
}
