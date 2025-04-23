package dev.creoii.coursebrowser.backend.course;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.creoii.coursebrowser.backend.quiz.question.Question;
import dev.creoii.coursebrowser.backend.quiz.QuizElement;
import dev.creoii.coursebrowser.backend.quiz.QuizSection;

import java.util.ArrayList;
import java.util.List;

public record Course(String name, String description, double price, List<QuizElement> quizElements) {
    public static Course fromJson(JsonElement element) {
        JsonObject object = element.getAsJsonObject();

        List<QuizElement> elements = new ArrayList<>();

        object.getAsJsonArray("elements").forEach(element1 -> {
            elements.add(QuizElement.fromJson(element1));
        });

        return new Course(object.get("name").getAsString(), object.get("description").getAsString(), object.get("price").getAsDouble(), elements);
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        for (QuizElement element : quizElements) {
            if (element instanceof Question question) {
                questions.add(question);
            } else if (element instanceof QuizSection section) {
                questions.addAll(section.getAllQuestions());
            }
        }
        return questions;
    }

    @Override
    public String toString() {
        return name + " - $" + price;
    }
}
