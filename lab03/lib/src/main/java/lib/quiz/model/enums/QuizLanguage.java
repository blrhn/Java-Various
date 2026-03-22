package lib.quiz.model.enums;

import lib.quiz.model.KeyProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum QuizLanguage implements KeyProvider {
    GUARANI("language.guarani", "Guarani"),
    SPANISH("language.spanish", "Spanish"),
    FRENCH("language.french", "French"),
    MALAY("language.malay", "Malay"),
    VIETNAMESE("language.vietnamese", "Vietnamese");

    private final String bundleKey;
    private final String apiName;

    private static final Map<String, QuizLanguage> MAP;

    static {
        Map<String, QuizLanguage> quizCapitalMap = Arrays.stream(values())
                .collect(Collectors.toMap(ql -> ql.apiName, ql -> ql));
        MAP = Collections.unmodifiableMap(quizCapitalMap);
    }

    QuizLanguage(String bundleKey, String apiName) {
        this.bundleKey = bundleKey;
        this.apiName = apiName;
    }

    @Override
    public String getBundleKey() {
        return bundleKey;
    }

    public static QuizLanguage of(String apiName) {
        return MAP.get(apiName);
    }
}
