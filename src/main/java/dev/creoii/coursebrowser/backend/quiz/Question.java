package dev.creoii.coursebrowser.backend.quiz;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question implements QuizElement {
    private final String text;
    private final List<RangeValue> values;
    private final List<Answer> answers;
    private final boolean built;

    public Question(String text, List<RangeValue> values, List<Answer> answers, boolean built) {
        this.text = text;
        this.values = values;
        this.answers = answers;
        this.built = built;
    }

    public static Question fromJson(JsonElement element) {
        JsonObject object = element.getAsJsonObject();

        List<RangeValue> values = new ArrayList<>();
        object.getAsJsonArray("values").forEach(element1 -> {
            values.add(RangeValue.fromJson(element1));
        });

        List<Answer> answers = new ArrayList<>();
        object.getAsJsonArray("answers").forEach(element1 -> {
            answers.add(Answer.fromJson(element1));
        });

        return new Question(object.get("text").getAsString(), values, answers, false);
    }

    public List<RangeValue> getValues() {
        return values;
    }

    public boolean isBuilt() {
        return built;
    }

    public Question build() {
        String text = this.text;
        List<RangeValue> builtValues = new ArrayList<>();
        if (!values.isEmpty()) {
            Object[] valuesArray = values.stream().map(rangeValue -> {
                int randomValue = rangeValue.getRandom();
                builtValues.add(new RangeValue(randomValue, randomValue));
                return randomValue;
            }).toArray();
            text = String.format(this.text, valuesArray);
        }
        List<Answer> answers = new ArrayList<>(this.answers.stream().map(answer -> answer.build(builtValues)).toList());
        answers.add(new Answer(this.text, true, new ArrayList<>(), true).build(builtValues));
        Collections.shuffle(answers);
        return new Question(text, builtValues, answers, true);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(text);
        for (Answer answer : answers) {
            builder.append("\n  ").append(answer.toString());
        }
        return builder.toString();
    }
}
