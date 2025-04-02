package dev.creoii.coursebrowser.backend.quiz.question;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.creoii.coursebrowser.backend.quiz.QuizElement;
import dev.creoii.coursebrowser.backend.util.JsonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO: Abstract out into Multiple Choice, Numerical/Text, Fill-in-the-blank, etc.
 */
public abstract class Question implements QuizElement {
    private final String questionType;
    private final String text;
    private final List<RangeValue> values;
    private final boolean built;
    /**
     * Null on any non-built questions
     */
    private Response response;

    public Question(String questionType, String text, List<RangeValue> values, boolean built) {
        this.questionType = questionType;
        this.text = text;
        this.values = values;
        this.built = built;
        response = null;
    }

    public abstract List<Answer> getAnswers();

    public abstract List<Answer> getCorrectAnswers();

    public abstract Question finishBuild(Question preBuilt, String text, List<RangeValue> builtValues, List<Answer> answers);

    public abstract boolean tryAnswer(String attempt);

    public String getQuestionType() {
        return questionType;
    }

    public String getText() {
        return text;
    }

    public List<RangeValue> getValues() {
        return values;
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

    public static QuizElement fromJson(JsonElement element, String type) {
        JsonObject object = element.getAsJsonObject();

        if ("multiple_choice".equals(type)) {
            return MultipleChoiceQuestion.fromJson(object);
        }

        return null;
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
        List<Answer> answers = new ArrayList<>(this.getAnswers().stream().map(answer -> answer.build(builtValues)).toList());
        Collections.shuffle(answers);
        return finishBuild(this, text, builtValues, answers);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(text);
        for (Answer answer : getAnswers()) {
            builder.append("\n  ").append(answer.toString());
        }
        return builder.toString();
    }
}
