package lib.person.stats;

import lib.person.model.Person;
import spi.FileAnalyzer;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PersonStatsAnalyzer implements FileAnalyzer<PersonStats, Person> {
    @Override
    public boolean supports(String objectName) {
        return objectName.endsWith("person");
    }

    @Override
    public PersonStats analyze(List<Person> persons) {
        String mostPopularFirstName = getMostPopular(persons, Person::firstName);
        String mostPopularLastName = getMostPopular(persons, Person::surname);
        long totalCount = persons.size();
        long peopleWithoutSecondNameCount = getPeopleWithoutSecondName(persons);

        return new PersonStats(mostPopularFirstName, mostPopularLastName, totalCount, peopleWithoutSecondNameCount);
    }

    private String getMostPopular(List<Person> persons, Function<Person, String> personGetter) {
        return persons.stream()
                .map(personGetter)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .orElse("-");
    }

    private long getPeopleWithoutSecondName(List<Person> persons) {
        return persons.stream()
                .filter(p -> p.secondName().isEmpty())
                .count();
    }
}
