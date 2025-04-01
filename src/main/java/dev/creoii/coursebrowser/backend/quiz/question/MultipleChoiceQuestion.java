package dev.creoii.coursebrowser.backend.quiz.question;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MultipleChoiceQuestion extends Question {
    private final List<Answer> answers;
    private Answer correctAnswer;

    public MultipleChoiceQuestion(String text, List<RangeValue> values, List<Answer> answers, boolean built) {
        super("multiple_choice", text, values, built);
        this.answers = answers;
    }

    @Override
    public List<Answer> getAnswers() {
        return answers;
    }

    @Override
    public List<Answer> getCorrectAnswers() {
        return new ArrayList<>(List.of(correctAnswer));
    }

    @Override
    public boolean tryAnswer(String attempt) {
        return Objects.equals(attempt, correctAnswer.getText());
    }

    public static MultipleChoiceQuestion fromJson(JsonElement element) {
        JsonObject object = element.getAsJsonObject();

        List<RangeValue> values = new ArrayList<>();
        object.getAsJsonArray("values").forEach(element1 -> {
            values.add(RangeValue.fromJson(element1));
        });

        List<Answer> answers = new ArrayList<>();
        object.getAsJsonArray("answers").forEach(element1 -> {
            answers.add(Answer.fromJson(element1));
        });

        return new MultipleChoiceQuestion(object.get("text").getAsString(), values, answers, false);
    }

    @Override
    public Question finishBuild(Question preBuilt, String text, List<RangeValue> builtValues, List<Answer> answers) {
        Answer correct = new Answer(preBuilt.getText(), true, new ArrayList<>(), true).build(builtValues);
        answers.add(correct);

        MultipleChoiceQuestion question = new MultipleChoiceQuestion(text, builtValues, answers, true);
        question.correctAnswer = correct;
        return question;
    }
}
