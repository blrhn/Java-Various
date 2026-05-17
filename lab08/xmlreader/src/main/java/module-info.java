module xmlreader {
    exports xmlreader.jaxb;
    exports xmlreader.jaxb.models;
    exports xmlreader.jaxb.adapters;

    exports xmlreader.jaxp.sax;
    exports xmlreader.jaxp.sax.handlers;

    exports xmlreader.jaxp.dom;

    requires jakarta.xml.bind;
    requires static lombok;
    requires java.xml;

    opens xmlreader.jaxb.models to jakarta.xml.bind;
    exports xmlreader.jaxp.models;
    exports xmlreader.xslt;
    exports xmlreader.abstractions;
}