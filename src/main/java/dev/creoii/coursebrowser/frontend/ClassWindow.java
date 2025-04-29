package dev.creoii.coursebrowser.frontend;

import dev.creoii.coursebrowser.backend.course.Course;
import dev.creoii.coursebrowser.backend.quiz.Quiz;
import dev.creoii.coursebrowser.backend.quiz.question.Answer;
import dev.creoii.coursebrowser.backend.quiz.question.Question;

import javax.swing.*;
import java.awt.*;

public class ClassWindow extends JFrame {
    private final Question[] questions;
    private Question currentQuestion;
    private int currentIndex = 0;

    private final JLabel questionLabel;
    private final JComboBox<String> answerBox;
    private final JButton submitButton;

    public ClassWindow(Course course) {
        setTitle("Class - " + course.name());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Quiz quiz = new Quiz(course);
        questions = quiz.getQuestions().values().toArray(new Question[0]);

        currentQuestion = questions[currentIndex];

        JPanel panel = new JPanel(new BorderLayout());
        questionLabel = new JLabel(currentQuestion.getText(), SwingConstants.CENTER);

        answerBox = new JComboBox<>();
        updateAnswers();

        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> handleSubmit());

        panel.add(questionLabel, BorderLayout.NORTH);
        panel.add(answerBox, BorderLayout.CENTER);
        panel.add(submitButton, BorderLayout.SOUTH);
        add(panel);
    }

    private void handleSubmit() {
        String answer = (String) answerBox.getSelectedItem();
        boolean isCorrect = currentQuestion.getCorrectAnswers().stream().anyMatch(ans -> ans.getText().equals(answer));

        if (isCorrect) {
            JOptionPane.showMessageDialog(this, "Correct!");

            if (++currentIndex >= questions.length) {
                JOptionPane.showMessageDialog(this, "Quiz complete!");
                submitButton.setEnabled(false);
                answerBox.setEnabled(false);
                return;
            }

            currentQuestion = questions[currentIndex];
            questionLabel.setText(currentQuestion.getText());
            updateAnswers();
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect. Try again.");
        }
    }

    private void updateAnswers() {
        answerBox.removeAllItems();
        for (Answer answer : currentQuestion.getAnswers()) {
            answerBox.addItem(answer.getText());
        }
    }
}
