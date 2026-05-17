package xmlreader.jaxb.adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

// https://stackoverflow.com/questions/6378227/how-to-change-programmatically-the-default-jaxb-date-serialization
public class DateAdapter extends XmlAdapter<String, ZonedDateTime> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME;

    @Override
    public ZonedDateTime unmarshal(String text) throws Exception {
        return FORMATTER.parse(text, ZonedDateTime::from);
    }

    @Override
    public String marshal(ZonedDateTime zonedDateTime) throws Exception {
        return FORMATTER.format(zonedDateTime);
    }
}
