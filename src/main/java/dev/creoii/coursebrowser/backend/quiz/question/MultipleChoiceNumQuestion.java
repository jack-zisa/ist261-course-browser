package dev.creoii.coursebrowser.backend.quiz.question;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MultipleChoiceNumQuestion extends Question {
    private final List<Answer> answers;
    private int correctAnswerIndex;

    public MultipleChoiceNumQuestion(String text, List<RangeValue> values, List<Answer> answers, boolean built) {
        super("multiple_choice_num", text, values, built);
        this.answers = answers;
    }

    @Override
    public List<Answer> getAnswers() {
        return answers;
    }

    @Override
    public List<Answer> getCorrectAnswers() {
        return new ArrayList<>(List.of(getAnswers().get(correctAnswerIndex)));
    }

    @Override
    public boolean tryAnswer(String attempt) {
        return Objects.equals(attempt, getCorrectAnswers().getFirst().getText());
    }

    public static MultipleChoiceNumQuestion fromJson(JsonElement element) {
        JsonObject object = element.getAsJsonObject();

        List<RangeValue> values = new ArrayList<>();
        if (object.has("values")) {
            object.getAsJsonArray("values").forEach(element1 -> {
                values.add(RangeValue.fromJson(element1));
            });
        }

        List<Answer> answers = new ArrayList<>();
        object.getAsJsonArray("answers").forEach(element1 -> {
            answers.add(Answer.fromJson(element1));
        });

        return new MultipleChoiceNumQuestion(object.get("text").getAsString(), values, answers, false);
    }

    @Override
    public Question finishBuild(Question preBuilt, String text, List<RangeValue> builtValues, List<Answer> answers) {
        Answer correct = new Answer(preBuilt.getText(), true, new ArrayList<>(), true).build(builtValues);
        answers.add(correct);

        MultipleChoiceNumQuestion question = new MultipleChoiceNumQuestion(text, builtValues, answers, true);
        question.correctAnswerIndex = answers.size() - 1;
        return question;
    }
}
