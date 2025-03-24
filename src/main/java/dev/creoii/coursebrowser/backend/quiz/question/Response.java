package dev.creoii.coursebrowser.backend.quiz.question;

public class Response {
    private final Question question;
    private final Answer answer;

    public Response(Question question, Answer answer) {
        this.question = question;
        this.answer = answer;
    }

    public boolean isAnswered() {
        return answer != null;
    }

    public boolean isCorrect() {
        return answer == question.getCorrectAnswer();
    }
}
