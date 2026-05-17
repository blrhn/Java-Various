package xmlreader.jaxb.models;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;
import lombok.Setter;
import xmlreader.jaxb.adapters.DateAdapter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "channel")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChannelJAXB {
    @XmlElement
    private String title;

    @XmlElement
    private String link;

    @XmlElement
    private String xmlLink;

    @XmlElement
    private String description;

    @XmlElement
    private String language;

    @XmlElement
    private String baseCurrency;

    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class)
    private ZonedDateTime pubDate;

    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class)
    private ZonedDateTime lastBuildDate;

    @XmlElement(name = "item")
    private List<ItemJAXB> items;
}
