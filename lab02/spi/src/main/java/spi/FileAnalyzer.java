package spi;

import java.util.List;

public interface FileAnalyzer<T, U> {
    boolean supports(String objectName);
    T analyze(List<U> objects);
}
