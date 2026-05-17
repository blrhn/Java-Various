package xmlreader.jaxp.models;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
public class ChannelJAXP {
    private String title;
    private String link;
    private String xmlLink;
    private String description;
    private String language;
    private String baseCurrency;
    private ZonedDateTime pubDate;
    private ZonedDateTime lastBuildDate;
    private List<ItemJAXP> items;
}
