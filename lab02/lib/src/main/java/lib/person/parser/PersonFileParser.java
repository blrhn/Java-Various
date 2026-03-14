package lib.person.parser;

import lib.person.model.Person;
import spi.FileParser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PersonFileParser implements FileParser<Person> {
    @Override
    public boolean supports(String extension) {
        return extension.equalsIgnoreCase("person");
    }

    @Override
    public List<Person> parse(List<String> lines) {
        List<Person> parsedContent = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);

            String[] fields = line.split(";");
            String[] bothNames =  fields[0].split(",");

            parsedContent.add(
                    new Person(
                            bothNames[0],
                            bothNames[1].trim(),
                            fields[1],
                            fields[2],
                            LocalDate.parse(fields[3], formatter)
                    ));
        }

        return parsedContent;
    }
}
