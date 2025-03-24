package dev.creoii.coursebrowser;

import dev.creoii.coursebrowser.backend.DataLoader;
import dev.creoii.coursebrowser.backend.course.Courses;
import dev.creoii.coursebrowser.backend.quiz.Question;
import dev.creoii.coursebrowser.backend.quiz.Quiz;

import java.util.Scanner;

/**
 * <li>package frontend/ is for frontend stuff</li>
 * <li>package backend/ is for backend stuff</li>
 * <li>package api/ is to connect the two</li>
 */
public class Main {
    public static void main(String[] args) {
        DataLoader.load();

        Scanner scanner = new Scanner(System.in);

        Quiz quiz = new Quiz(Courses.getCourses().getFirst());

        Question[] questions = quiz.getQuestions().values().toArray(new Question[0]);
        for (int i = 0; i < questions.length; ++i) {
            Question question = questions[i];
            System.out.println("Question " + (i + 1) + ": " + question.getText());

            for (int j = 0; j < question.getAnswers().size(); ++j) {
                System.out.println((j + 1) + ": " + question.getAnswers().get(j).getText());
            }

            int input = Integer.parseInt(scanner.nextLine()) - 1;

            if (input >= question.getAnswers().size() || input < 0) {
                System.out.println("Invalid answer.");
                continue;
            }

            if (question.getAnswers().get(input).isCorrect()) {
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect.");
            }
        }
    }
}