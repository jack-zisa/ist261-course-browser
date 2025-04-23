package dev.creoii.coursebrowser.backend.quiz.question;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MultipleChoiceQuestion extends Question {
    private final List<Answer> answers;
    private Answer correctAnswer;

    public MultipleChoiceQuestion(String text, List<Answer> answers, boolean built) {
        super("multiple_choice", text, new ArrayList<>(), built);
        this.answers = answers;
        correctAnswer = null;
    }

    @Override
    public List<Answer> getAnswers() {
        return answers;
    }

    @Override
    public List<Answer> getCorrectAnswers() {
        if (correctAnswer == null)
            return List.of();
        return new ArrayList<>(List.of(correctAnswer));
    }

    @Override
    public boolean tryAnswer(String attempt) {
        return Objects.equals(attempt, getCorrectAnswers().getFirst().getText());
    }

    public static MultipleChoiceQuestion fromJson(JsonElement element) {
        JsonObject object = element.getAsJsonObject();

        List<Answer> answers = new ArrayList<>();
        object.getAsJsonArray("answers").forEach(element1 -> {
            answers.add(Answer.fromJson(element1));
        });

        return new MultipleChoiceQuestion(object.get("text").getAsString(), answers, false);
    }

    @Override
    public Question finishBuild(Question preBuilt, String text, List<RangeValue> builtValues, List<Answer> answers) {
        MultipleChoiceQuestion question = new MultipleChoiceQuestion(text, answers, true);
        question.correctAnswer = answers.stream().filter(Answer::isCorrect).findFirst().get();
        return question;
    }
}
