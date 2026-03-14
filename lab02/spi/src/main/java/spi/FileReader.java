package spi;

import java.io.File;
import java.util.List;

public interface FileReader {
    boolean supports(String fileExtension);
    List<String> read(File file);
}
