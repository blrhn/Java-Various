package ui;

import lib.client.ApiClient;
import lib.client.CountryRepository;
import lib.client.model.dto.ResponseWrapper;
import lib.quiz.QuizState;
import lib.quiz.generator.QuizStateFactory;

public class DataInitializer {
    private DataInitializer() {}

    public static QuizState initData() {
        ResponseWrapper response = ApiClient.fetchData();
        CountryRepository countryRepository = new CountryRepository();
        countryRepository.loadCountries(response);
        QuizStateFactory quizStateFactory = new QuizStateFactory();

        return quizStateFactory.generateQuizState(countryRepository, 5);
    }
}
