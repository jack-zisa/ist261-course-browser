package dev.creoii.coursebrowser;

import dev.creoii.coursebrowser.backend.DataLoader;
import dev.creoii.coursebrowser.backend.course.Courses;
import dev.creoii.coursebrowser.backend.quiz.Quiz;

/**
 * <li>package frontend/ is for frontend stuff</li>
 * <li>package backend/ is for backend stuff</li>
 * <li>package api/ is to connect the two</li>
 */
public class Main {
    public static void main(String[] args) {
        DataLoader.load();

        Quiz quiz = new Quiz(Courses.getCourses().getFirst());
        System.out.println(quiz);
    }
}