package dev.creoii.coursebrowser.backend.quiz;

import dev.creoii.coursebrowser.backend.course.Course;
import dev.creoii.coursebrowser.backend.quiz.question.Question;

import java.util.HashMap;
import java.util.Map;

public class Quiz implements QuizElement {
    private final Course course;
    private final Map<Question, Question> questions;
    private int correctAnswerCount;

    public Quiz(Course course) {
        this.course = course;
        questions = new HashMap<>();

        for (Question question : course.getAllQuestions()) {
            questions.put(question, question.build());
        }
    }

    public Course getCourse() {
        return course;
    }

    public Map<Question, Question> getQuestions() {
        return questions;
    }

    public void incrementCorrect() {
        ++correctAnswerCount;
    }

    public String toScoreString() {
        double percentage = (correctAnswerCount * 100d) / questions.size();
        return correctAnswerCount + "/" + questions.size() + " | " + String.format("%.2f", percentage) + "%";
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
