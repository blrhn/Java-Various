package lib.quiz.generator;

import lib.client.model.dto.CountryResponse;
import lib.client.model.dto.LanguageResponse;
import lib.quiz.model.KeyProvider;
import lib.quiz.model.QuizQuestion;
import lib.quiz.model.enums.QuizCapital;
import lib.quiz.model.enums.QuizCountry;
import lib.quiz.model.enums.QuizCurrency;
import lib.quiz.model.enums.QuizLanguage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizGenerator {
    private static final String SUFFIX_NOM = ".nom";
    private static final String SUFFIX_GEN = ".gen";
    private static final String SUFFIX_LOC = ".loc";
    private static final String ANSWER_YES = "general.yes";
    private static final String ANSWER_NO = "general.no";
    private static final int MAX_ANSWERS = 4;
    private final Random random = new Random();

    public List<QuestionGenerator> getAllGenerators() {
        return List.of(
                this::generateCurrenciesFirst,
                this::generateCurrenciesSecond,
                this::generateLanguagesFirst,
                this::generateLanguagesSecond,
                this::generateCapitalsFirst,
                this::generateCapitalsSecond
        );
    }

    private QuizQuestion generateCurrenciesFirst(CountryResponse randomCountry) {
        List<String> questionValueKeys = List.of(QuizCountry.of(randomCountry.name()).getBundleKey() + SUFFIX_LOC);
        String correctAnswerKey = QuizCurrency.of(randomCountry.currency()).getBundleKey() + SUFFIX_NOM;
        List<String> answerKeys = generateAllAnswers(QuizCurrency.values(), correctAnswerKey, SUFFIX_NOM);

        return new QuizQuestion("question.currencies.first", questionValueKeys, answerKeys, correctAnswerKey);
    }

    private QuizQuestion generateCurrenciesSecond(CountryResponse randomCountry) {
        List<String> questionValueKeys = List.of(QuizCurrency.of(randomCountry.currency()).getBundleKey() + SUFFIX_GEN);
        String correctAnswerKey = QuizCountry.of(randomCountry.name()).getBundleKey() + SUFFIX_NOM;
        List<String> answerKeys = generateAllAnswers(QuizCountry.values(), correctAnswerKey, SUFFIX_NOM);

        return new QuizQuestion("question.currencies.second", questionValueKeys, answerKeys, correctAnswerKey);
    }

    private QuizQuestion generateLanguagesFirst(CountryResponse randomCountry) {
        List<String> questionValueKeys = List.of(QuizCountry.of(randomCountry.name()).getBundleKey() + SUFFIX_LOC);
        String correctAnswerKey = QuizLanguage.of(
                randomCountry.languages().get(random.nextInt(randomCountry.languages().size())).name()).getBundleKey() + SUFFIX_LOC;
        List<String> answerKeys = generateAllAnswers(QuizLanguage.values(), correctAnswerKey, SUFFIX_LOC);

        return new QuizQuestion("question.languages.first", questionValueKeys, answerKeys, correctAnswerKey);
    }

    private QuizQuestion generateLanguagesSecond(CountryResponse randomCountry) {
        QuizLanguage actualLanguage = getRandomLanguage(randomCountry);
        QuizLanguage randomLanguage = getRandomValue(QuizLanguage.values());

        List<String> questionValueKeys = List.of(
                QuizCountry.of(randomCountry.name()).getBundleKey() + SUFFIX_LOC,
                randomLanguage.getBundleKey() + SUFFIX_LOC
        );
        String correctAnswerKey = randomLanguage.equals(actualLanguage) ? ANSWER_YES : ANSWER_NO;

        return new QuizQuestion("question.languages.second", questionValueKeys, List.of(ANSWER_YES, ANSWER_NO), correctAnswerKey);
    }

    private QuizQuestion generateCapitalsFirst(CountryResponse randomCountry) {
        List<String> questionValueKeys = List.of(QuizCountry.of(randomCountry.name()).getBundleKey() + SUFFIX_GEN);
        String correctAnswerKey = QuizCapital.of(randomCountry.capital()).getBundleKey() + SUFFIX_NOM;
        List<String> answerKeys = generateAllAnswers(QuizCapital.values(), correctAnswerKey, SUFFIX_NOM);

        return new QuizQuestion("question.capitals.first", questionValueKeys, answerKeys, correctAnswerKey);
    }

    private QuizQuestion generateCapitalsSecond(CountryResponse randomCountry) {
        QuizCountry actualCountry = QuizCountry.of(randomCountry.name());
        QuizCountry randomQuizCountry = getRandomValue(QuizCountry.values());

        List<String> questionValueKeys = List.of(
                QuizCapital.of(randomCountry.capital()).getBundleKey() + SUFFIX_NOM,
                randomQuizCountry.getBundleKey() + SUFFIX_GEN
        );
        String correctAnswerKey = randomQuizCountry.equals(actualCountry) ? ANSWER_YES : ANSWER_NO;

        return new QuizQuestion("question.capitals.second", questionValueKeys, List.of(ANSWER_YES, ANSWER_NO), correctAnswerKey);
    }

    private <T extends KeyProvider> List<String> generateAllAnswers(T[] values, String correctAnswerKey, String suffix) {
        List<String> answers = new ArrayList<>();
        answers.add(correctAnswerKey);

        List<T> allValues = Arrays.asList(values);
        Collections.shuffle(allValues);

        int i = 0;
        for (T value : allValues) {
            String answer = value.getBundleKey() + suffix;

            if (!answer.equals(correctAnswerKey) && i < MAX_ANSWERS) {
                answers.add(answer);
            }
            i++;
        }
        Collections.shuffle(answers);

        return answers;
    }

    private <T> T getRandomValue(T[] array) {
        return array[random.nextInt(array.length)];
    }

    private QuizLanguage getRandomLanguage(CountryResponse country) {
        List<LanguageResponse>  languages = country.languages();
        LanguageResponse randomLang = languages.get(random.nextInt(languages.size()));
        return QuizLanguage.of(randomLang.name());
    }
}
