package lib.person.stats;

public record PersonStats(
   String mostPopularFirstName,
   String mostPopularLastName,
   long totalCount,
   long peopleWithoutSecondNameCount
) {}
