package xmlreader.jaxb.models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;
import lombok.Setter;
import xmlreader.jaxb.adapters.DateAdapter;

import java.time.ZonedDateTime;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemJAXB {
    @XmlElement
    private String title;

    @XmlElement
    private String link;

    @XmlElement
    private String description;

    @XmlJavaTypeAdapter(DateAdapter.class)
    private ZonedDateTime pubDate;

    @XmlElement
    private String baseCurrency;

    @XmlElement
    private String baseName;

    @XmlElement
    private String targetCurrency;

    @XmlElement
    private String targetName;

    @XmlElement
    private Double exchangeRate;

    @XmlElement
    private Double inverseRate;

    @XmlElement
    private String inverseDescription;
}
