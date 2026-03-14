package lib.person.model;

import java.time.LocalDate;

public record Person(
        String firstName,
        String secondName,
        String surname,
        String maidenName,
        LocalDate birthDate
) {}
