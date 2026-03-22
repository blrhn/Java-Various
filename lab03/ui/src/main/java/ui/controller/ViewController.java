package ui.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import lib.quiz.QuizState;
import lib.quiz.model.QuizQuestion;

import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ViewController {
    private static final String BUNDLE_LANGUAGE = "i18n.Language";
    private static final String BUNDLE_SCORE = "i18n.ScoreMessages";
    private static final Locale LOCALE_PL = new Locale("pl", "PL");

    @FXML private Label answerLabel;
    @FXML private ComboBox<String> answersComboBox;
    @FXML private Label questionLabel;
    @FXML private Button englishButton;
    @FXML private Button polishButton;
    @FXML private Button nextQuestionButton;
    @FXML private Button checkAnswerButton;
    @FXML private Label titleLabel;
    @FXML private Label chooseAnswerLabel;

    private QuizState quizState;
    private final ObjectProperty<ResourceBundle> bundleProperty = new SimpleObjectProperty<>();

    private int savedSelectionIndex = -1;
    private boolean isAnswerChecked = false;

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.bundleProperty.set(resourceBundle);
        setUpLanguageListener();
    }

    public void setQuizState(QuizState quizState) {
        this.quizState = quizState;
    }

    public void startQuiz() {
        updateLanguageButtons();
        displayQuestion(quizState.getCurrentQuestion());
    }

    private void setUpLanguageListener() {
        bundleProperty.addListener((observable, oldValue, newValue) -> {
            updateLanguageButtons();
            updateStaticText();

            if (quizState != null && quizState.getCurrentQuestion() != null) {
                savedSelectionIndex = answersComboBox.getSelectionModel().getSelectedIndex();
                displayQuestion(quizState.getCurrentQuestion());
            }
        });
    }

    @FXML
    void changeToEnglish(ActionEvent event) {
        switchLanguage(Locale.ENGLISH);
    }

    @FXML
    void changeToPolish(ActionEvent event) {
        switchLanguage(LOCALE_PL);
    }

    private void switchLanguage(Locale locale) {
        ResourceBundle newBundle = ResourceBundle.getBundle(BUNDLE_LANGUAGE, locale);
        bundleProperty.set(newBundle);
        //updateLanguageButtons();
    }

    private void updateLanguageButtons() {
        boolean isPolish = bundleProperty.get().getLocale().getLanguage().equals(LOCALE_PL.getLanguage());
        polishButton.setDisable(isPolish);
        englishButton.setDisable(!isPolish);
    }

    @FXML
    void checkAnswer(ActionEvent event) {
        if (isAnswerChecked) {
            return;
        }

        String selectedAnswer = answersComboBox.getSelectionModel().getSelectedItem();
        if (selectedAnswer == null) {
            return;
        }

        isAnswerChecked = true;
        QuizQuestion currentQuestion = quizState.getCurrentQuestion();
        String translatedCorrectAnswer = bundleProperty.get().getString(currentQuestion.correctAnswerKey());

        boolean isCorrect = selectedAnswer.equals(translatedCorrectAnswer);
        if (isCorrect) {
            quizState.setCurrentScore(quizState.getCurrentScore() + 1);
        }

        displayResult(isCorrect, translatedCorrectAnswer);
    }

    @FXML
    private void toggleNextQuestion() {
        isAnswerChecked = false;
        savedSelectionIndex = -1;

        quizState.nextQuestion();

        if (quizState.getCurrentQuestionIndex() < quizState.getTotalQuestions()) {
            displayQuestion(quizState.getCurrentQuestion());
        } else {
            handleQuizEnd();
        }
    }

    private void disableButtons() {
        nextQuestionButton.setDisable(true);
        englishButton.setDisable(true);
        polishButton.setDisable(true);
        checkAnswerButton.setDisable(true);
    }

    private String getScoreHeaderText() {
        ResourceBundle scoreBundle = ResourceBundle.getBundle(BUNDLE_SCORE, bundleProperty.get().getLocale());
        double[] limits = (double[]) scoreBundle.getObject("score.limits");
        String[] formats = (String[])  scoreBundle.getObject("score.formats");

        ChoiceFormat pluralFormat = new ChoiceFormat(limits, formats);
        String score = pluralFormat.format(quizState.getCurrentScore());
        String scorePattern = scoreBundle.getString("general.score");

        return MessageFormat.format(scorePattern, quizState.getCurrentScore(), score, quizState.getTotalQuestions());
    }

    private void displayQuestion(QuizQuestion quizQuestion) {
        ResourceBundle bundle = bundleProperty.get();

        Object[] translatedArgs = quizQuestion.questionValueKeys().stream()
                .map(bundle::getString)
                .toArray();
        String questionTemplate = bundle.getString(quizQuestion.questionKey());
        questionLabel.setText(MessageFormat.format(questionTemplate, translatedArgs));

        answersComboBox.getItems().clear();
        List<String> translatedItems = quizQuestion.answerKeys().stream()
                .map(bundle::getString)
                .toList();
        answersComboBox.getItems().addAll(translatedItems);

        if (savedSelectionIndex >= 0) {
            answersComboBox.getSelectionModel().select(savedSelectionIndex);
        }

        if (isAnswerChecked) {
            restoreUI();
        } else {
            answerLabel.setText("");
        }
    }

    private void restoreUI() {
        String selectedAnswer = answersComboBox.getSelectionModel().getSelectedItem();
        QuizQuestion currentQuestion = quizState.getCurrentQuestion();
        String translatedCorrectAnswer = bundleProperty.get().getString(currentQuestion.correctAnswerKey());

        boolean isCorrect = selectedAnswer.equals(translatedCorrectAnswer);
        displayResult(isCorrect, translatedCorrectAnswer);
    }

    private void displayResult(boolean isCorrect, String correctAnswer) {
        ResourceBundle bundle = bundleProperty.get();

        if (isCorrect) {
            answerLabel.setText(bundle.getString("general.correct"));
        } else {
            String incorrect = bundle.getString("general.incorrect");
            answerLabel.setText(MessageFormat.format(incorrect, correctAnswer));
        }
    }

    private void handleQuizEnd() {
        disableButtons();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundleProperty.get().getString("general.end"));
        alert.setHeaderText(getScoreHeaderText());
        alert.showAndWait();

    }

    private void updateStaticText() {
        ResourceBundle bundle = bundleProperty.get();

        nextQuestionButton.setText(bundle.getString("general.nextQuestion"));
        checkAnswerButton.setText(bundle.getString("general.checkAnswer"));
        englishButton.setText(bundle.getString("general.englishVersion"));
        polishButton.setText(bundle.getString("general.polishVersion"));
        titleLabel.setText(bundle.getString("label.quizText"));
        chooseAnswerLabel.setText(bundle.getString("general.chooseAnswer"));
    }
}
