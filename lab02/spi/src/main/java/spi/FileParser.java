package spi;

import java.util.List;

public interface FileParser<T> {
    boolean supports(String objectName);
    List<T> parse(List<String> lines);
}
