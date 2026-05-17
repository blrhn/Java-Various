package xmlreader.jaxp.models;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class ItemJAXP {
    private String title;
    private String link;
    private String description;
    private ZonedDateTime pubDate;
    private String baseCurrency;
    private String baseName;
    private String targetCurrency;
    private String targetName;
    private Double exchangeRate;
    private Double inverseRate;
    private String inverseDescription;
}
