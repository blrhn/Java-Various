package lib.quiz.generator;

import lib.client.CountryRepository;
import lib.client.model.dto.CountryResponse;
import lib.quiz.QuizState;
import lib.quiz.model.QuizQuestion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizStateFactory {
    private final Random random = new Random();
    private final QuizGenerator quizGenerator = new QuizGenerator();
    private final List<QuestionGenerator> questionGenerators = quizGenerator.getAllGenerators();

    public QuizState generateQuizState(CountryRepository countryRepository, int questionNumber) {
        List<QuizQuestion> quizQuestions = new ArrayList<>();

        for (int i = 0; i < questionNumber; i++) {
            CountryResponse randomCountry = countryRepository.getRandomCountry();
            quizQuestions.add(questionGenerators.get(random.nextInt(questionGenerators.size())).generate(randomCountry));
        }

        return new QuizState(quizQuestions);
    }

}
