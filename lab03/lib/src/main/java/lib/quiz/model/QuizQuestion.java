package lib.quiz.model;

import java.util.List;

public record QuizQuestion(
    String questionKey,
    List<String> questionValueKeys,
    List<String> answerKeys,
    String correctAnswerKey
) {}
