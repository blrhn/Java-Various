package lib.person.model;

import lib.person.stats.PersonStats;

import java.util.List;

public record ProcessedPersonsData(
        List<String> previewLines,
        List<Person> parsedPersons,
        PersonStats personStats
) {}
