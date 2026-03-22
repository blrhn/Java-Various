package lib.quiz.model.enums;

import lib.quiz.model.KeyProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum QuizCountry implements KeyProvider {
    PERU("country.peru", "Peru"),
    ARGENTINA("country.argentina", "Argentina"),
    VIETNAM("country.vietnam", "Vietnam"),
    MALAYSIA("country.malaysia", "Malaysia"),
    MALI("country.mali", "Mali"),;

    private final String bundleKey;
    private final String apiName;

    private static final Map<String, QuizCountry> MAP;

    static {
        Map<String, QuizCountry> quizCapitalMap = Arrays.stream(values())
                .collect(Collectors.toMap(qc -> qc.apiName, qc -> qc));
        MAP = Collections.unmodifiableMap(quizCapitalMap);
    }

    QuizCountry(String bundleKey, String apiName) {
        this.bundleKey = bundleKey;
        this.apiName = apiName;
    }

    @Override
    public String getBundleKey() {
        return bundleKey;
    }

    public static QuizCountry of(String apiName) {
        return MAP.get(apiName);
    }
}
