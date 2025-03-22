package dev.creoii.coursebrowser.backend.quiz;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class QuizSection implements QuizElement {
    private final String name;
    public final List<QuizElement> quizElements;

    public QuizSection(String name, List<QuizElement> quizElements) {
        this.name = name;
        this.quizElements = quizElements;
    }

    public static QuizSection fromJson(JsonElement element) {
        JsonObject object = element.getAsJsonObject();

        List<QuizElement> elements = new ArrayList<>();

        object.getAsJsonArray("elements").forEach(element1 -> {
            elements.add(QuizElement.fromJson(element1));
        });

        return new QuizSection(object.get("name").getAsString(), elements);
    }

    public List<QuizElement> getQuizElements() {
        return quizElements;
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
        StringBuilder stringBuilder = new StringBuilder(name + "\n  elements:\n");
        for (QuizElement quizElement : getQuizElements()) {
            if (quizElement instanceof Question question) {
                stringBuilder.append(question);
            } else if (quizElement instanceof QuizSection section) {
                stringBuilder.append(section);
            }
        }
        return stringBuilder.toString();
    }
}
