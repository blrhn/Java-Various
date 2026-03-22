package lib.quiz.model.enums;

import lib.quiz.model.KeyProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum QuizCurrency implements KeyProvider {
    SOL("currencies.pen", "PEN"),
    PESO("currencies.ars", "ARS"),
    DONG("currencies.vnd", "VND"),
    RINGGIT("currencies.myr", "MYR"),
    LIRA("currencies.try", "TRY"),
    FRANK("currencies.xof", "XOF");

    private final String bundleKey;
    private final String apiName;

    private static final Map<String, QuizCurrency> MAP;

    static {
        Map<String, QuizCurrency> quizCapitalMap = Arrays.stream(values())
                .collect(Collectors.toMap(qc -> qc.apiName, qc -> qc));
        MAP = Collections.unmodifiableMap(quizCapitalMap);
    }

    QuizCurrency(String bundleKey, String apiName) {
        this.bundleKey = bundleKey;
        this.apiName = apiName;
    }

    @Override
    public String getBundleKey() {
        return bundleKey;
    }


    public static QuizCurrency of(String apiName) {
        return MAP.get(apiName);
    }
}
