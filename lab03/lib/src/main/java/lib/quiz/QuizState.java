package lib.quiz;

import lib.quiz.model.QuizQuestion;

import java.util.List;

public class QuizState {
    private final List<QuizQuestion> questions;
    private int currentQuestionIndex = 0;
    private int currentScore = 0;

    public QuizState(List<QuizQuestion> questions) {
        this.questions = questions;
    }

    public QuizQuestion getCurrentQuestion() {
        return questions.get(currentQuestionIndex);
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void nextQuestion() {
        currentQuestionIndex++;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getTotalQuestions() {
        return questions.size();
    }
}
