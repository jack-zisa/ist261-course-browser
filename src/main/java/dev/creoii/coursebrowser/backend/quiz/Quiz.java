package dev.creoii.coursebrowser.backend.quiz;

import dev.creoii.coursebrowser.backend.course.Course;

import java.util.HashMap;
import java.util.Map;

public class Quiz implements QuizElement {
    private final Course course;
    private final Map<Question, Question> questions;

    public Quiz(Course course) {
        this.course = course;
        questions = new HashMap<>();

        for (Question question : course.getAllQuestions()) {
            questions.put(question, question.build());
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(course.getName());
        for (Question question : questions.values()) {
            builder.append("\n").append(question.toString());
        }
        return builder.toString();
    }
}
