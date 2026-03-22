package lib.quiz.model.enums;

import lib.quiz.model.KeyProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum QuizCapital implements KeyProvider {
    LIMA("country.lima", "Lima"),
    BUENOS_ARIES("country.buenosAries", "Buenos Aires"),
    HANOI("country.hanoi", "Hanoi"),
    KUALA_LUMPUR("country.kualaLumpur", "Kuala Lumpur"),
    BAMAKO("country.bamako", "Bamako");

    private final String bundleKey;
    private final String apiName;

    private static final Map<String, QuizCapital> MAP;

    static {
        Map<String, QuizCapital> quizCapitalMap = Arrays.stream(values())
                .collect(Collectors.toMap(qc -> qc.apiName, qc -> qc));
        MAP = Collections.unmodifiableMap(quizCapitalMap);
    }

    QuizCapital(String bundleKey, String apiName) {
        this.bundleKey = bundleKey;
        this.apiName = apiName;
    }

    @Override
    public String getBundleKey() {
        return bundleKey;
    }

    public static QuizCapital of(String apiName) {
        return MAP.get(apiName);
    }
}
