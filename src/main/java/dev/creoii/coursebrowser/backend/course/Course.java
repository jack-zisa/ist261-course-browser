package dev.creoii.coursebrowser.backend.course;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.creoii.coursebrowser.backend.quiz.question.Question;
import dev.creoii.coursebrowser.backend.quiz.QuizElement;
import dev.creoii.coursebrowser.backend.quiz.QuizSection;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private final String name;
    private final List<QuizElement> quizElements;

    public Course(String name, List<QuizElement> quizElements) {
        this.name = name;
        this.quizElements = quizElements;
    }

    public static Course fromJson(JsonElement element) {
        JsonObject object = element.getAsJsonObject();

        List<QuizElement> elements = new ArrayList<>();

        object.getAsJsonArray("elements").forEach(element1 -> {
            elements.add(QuizElement.fromJson(element1));
        });

        return new Course(object.get("name").getAsString(), elements);
    }

    public String getName() {
        return name;
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
        StringBuilder stringBuilder = new StringBuilder(getName() + "\n  elements:\n");
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
