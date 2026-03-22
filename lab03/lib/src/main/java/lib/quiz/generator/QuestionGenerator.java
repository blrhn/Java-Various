package lib.quiz.generator;

import lib.client.model.dto.CountryResponse;
import lib.quiz.model.QuizQuestion;

public interface QuestionGenerator {
    QuizQuestion generate(CountryResponse countryResponse);
}
