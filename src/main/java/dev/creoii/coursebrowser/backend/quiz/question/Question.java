package dev.creoii.coursebrowser.backend.quiz.question;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.creoii.coursebrowser.backend.quiz.QuizElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question implements QuizElement {
    private final String text;
    private final List<RangeValue> values;
    private final List<Answer> answers;
    private final boolean built;
    /**
     * Null on any non-built questions
     */
    private Answer correctAnswer;
    private Response response;

    public Question(String text, List<RangeValue> values, List<Answer> answers, boolean built) {
        this.text = text;
        this.values = values;
        this.answers = answers;
        this.built = built;
        response = null;
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

    public String getText() {
        return text;
    }

    public List<RangeValue> getValues() {
        return values;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public boolean isBuilt() {
        return built;
    }

    public void respond(Response response) {
        this.response = response;
    }

    public boolean hasResponse() {
        return response != null;
    }

    public Response getResponse() {
        return response;
    }

    public Answer getCorrectAnswer() {
        return correctAnswer;
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
        Answer correct = new Answer(this.text, true, new ArrayList<>(), true).build(builtValues);
        answers.add(correct);
        Collections.shuffle(answers);
        Question question = new Question(text, builtValues, answers, true);
        question.correctAnswer = correctAnswer;
        return question;
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
