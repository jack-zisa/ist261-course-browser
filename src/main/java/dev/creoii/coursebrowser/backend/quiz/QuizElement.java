package dev.creoii.coursebrowser.backend.quiz;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.creoii.coursebrowser.backend.quiz.question.Question;

public interface QuizElement {
    static QuizElement fromJson(JsonElement element) {
        JsonObject object = element.getAsJsonObject();

        String type = object.get("type").getAsString();

        if ("question".equals(type)) {
            return Question.fromJson(object);
        } else if ("section".equals(type)) {
            return QuizSection.fromJson(object);
        }

        return null;
    }
}
