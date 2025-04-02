package dev.creoii.coursebrowser.backend.quiz;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.creoii.coursebrowser.backend.quiz.question.Question;

public interface QuizElement {
    static QuizElement fromJson(JsonElement element) {
        JsonObject object = element.getAsJsonObject();

        String[] type = object.get("type").getAsString().split(":");

        if ("question".equals(type[0])) {
            return Question.fromJson(object, type[1]);
        } else if ("section".equals(type[0])) {
            return QuizSection.fromJson(object);
        }

        return null;
    }
}
