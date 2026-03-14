package lib.person.reader;

import spi.FileReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class PersonFileReader implements FileReader {
    @Override
    public boolean supports(String fileExtension) {
        return fileExtension.equalsIgnoreCase("csv");
    }

    @Override
    public List<String> read(File file) {
        try {
            return Files.readAllLines(file.getAbsoluteFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
