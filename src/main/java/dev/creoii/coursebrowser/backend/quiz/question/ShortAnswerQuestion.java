package dev.creoii.coursebrowser.backend.quiz.question;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShortAnswerQuestion extends Question {
    private final Answer answer;
    private int correctAnswerIndex;

    public ShortAnswerQuestion(String text, List<RangeValue> values, boolean built) {
        super("short_answer", text, values, built);
        answer = new Answer("", true, values, false);
    }

    @Override
    public List<Answer> getAnswers() {
        return List.of();
    }

    @Override
    public List<Answer> getCorrectAnswers() {
        return new ArrayList<>(List.of(getAnswers().get(correctAnswerIndex)));
    }

    @Override
    public boolean tryAnswer(String attempt) {
        return Objects.equals(attempt, getCorrectAnswers().getFirst().getText());
    }

    public static ShortAnswerQuestion fromJson(JsonElement element) {
        JsonObject object = element.getAsJsonObject();

        List<RangeValue> values = new ArrayList<>();
        object.getAsJsonArray("values").forEach(element1 -> {
            values.add(RangeValue.fromJson(element1));
        });

        List<Answer> answers = new ArrayList<>();
        object.getAsJsonArray("answers").forEach(element1 -> {
            answers.add(Answer.fromJson(element1));
        });

        return new ShortAnswerQuestion(object.get("text").getAsString(), values, false);
    }

    @Override
    public Question finishBuild(Question preBuilt, String text, List<RangeValue> builtValues, List<Answer> answers) {
        Answer correct = new Answer(preBuilt.getText(), true, new ArrayList<>(), true).build(builtValues);
        answers.add(correct);

        ShortAnswerQuestion question = new ShortAnswerQuestion(text, builtValues, true);
        question.correctAnswerIndex = answers.size() - 1;
        return question;
    }
}
