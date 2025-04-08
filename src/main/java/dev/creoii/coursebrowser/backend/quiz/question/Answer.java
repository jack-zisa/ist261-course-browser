package dev.creoii.coursebrowser.backend.quiz.question;

import com.ezylang.evalex.EvaluationException;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.parser.ParseException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Answer {
    private final String text;
    private final boolean correct;
    private final List<RangeValue> values;
    private final boolean built;

    public Answer(String text, boolean correct, List<RangeValue> values, boolean built) {
        this.text = text;
        this.correct = correct;
        this.values = values;
        this.built = built;
    }

    public static Answer fromJson(JsonElement element) {
        JsonObject object = element.getAsJsonObject();

        List<RangeValue> values = new ArrayList<>();
        if (object.has("values")) {
            object.getAsJsonArray("values").forEach(element1 -> {
                values.add(RangeValue.fromJson(element1));
            });
        }

        return new Answer(object.get("text").getAsString(), false, values, false);
    }

    public String getText() {
        return text;
    }

    public boolean isCorrect() {
        return correct;
    }

    public Answer build(List<RangeValue> builtValues) {
        String text = this.text;

        List<Integer> allValues = new ArrayList<>(builtValues.stream().map(RangeValue::getBuilt).toList());
        allValues.addAll(values.stream().map(RangeValue::getRandom).toList());

        if (!allValues.isEmpty()) {
            try {
                Expression expression = new Expression(String.format(this.text, allValues.toArray()));
                text = expression.evaluate().getStringValue();
                String[] elements = text.split("\\.");
                if (elements.length > 1 && elements[1].length() > 2) { // rounding a string number
                    text = elements[0] + "." + elements[1].substring(0, 2);
                }
            } catch (ParseException | EvaluationException e) {
                e.printStackTrace();
            }
        }

        return new Answer(text, correct, List.of(), true);
    }

    @Override
    public String toString() {
        return text + (correct ? " [CORRECT]" : "");
    }
}
