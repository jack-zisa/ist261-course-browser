package dev.creoii.coursebrowser.api;

import dev.creoii.coursebrowser.backend.course.Course;
import dev.creoii.coursebrowser.backend.quiz.Quiz;
import dev.creoii.coursebrowser.backend.quiz.question.Question;

import java.util.Scanner;

public class SessionManager {
    private static Course selectedCourse = null;

    public static Course getSelectedCourse() {
        return selectedCourse;
    }

    public static void setSelectedCourse(Course selectedCourse) {
        SessionManager.selectedCourse = selectedCourse;
    }

    public static void takeQuiz(Scanner scanner) {
        if (selectedCourse == null)
            return;

        Quiz quiz = new Quiz(selectedCourse);

        Question[] questions = quiz.getQuestions().values().toArray(new Question[0]);
        for (int i = 0; i < questions.length; ++i) {
            Question question = questions[i];
            System.out.println("Question " + (i + 1) + ": " + question.getText());

            for (int j = 0; j < question.getAnswers().size(); ++j) {
                System.out.println((j + 1) + ": " + question.getAnswers().get(j).getText());
            }

            if (question.tryAnswer(scanner.nextLine())) {
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect.");
            }
        }
    }
}
