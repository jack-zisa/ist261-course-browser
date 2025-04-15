package dev.creoii.coursebrowser;

import dev.creoii.coursebrowser.api.CourseManager;
import dev.creoii.coursebrowser.api.SessionManager;
import dev.creoii.coursebrowser.api.UserManager;
import dev.creoii.coursebrowser.backend.DataLoader;
import dev.creoii.coursebrowser.backend.DataSaver;
import dev.creoii.coursebrowser.backend.User;
import dev.creoii.coursebrowser.backend.course.Course;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataLoader.load();

        Scanner scanner = new Scanner(System.in);

        User user = new User();
        UserManager.registerUser(user);
        user.purchase(CourseManager.getCourses().getFirst());

        System.out.println("Available courses: ");
        user.getPurchasedCourses().forEach(course -> {
            System.out.println("    " + course.getName());
        });

        String selectedCourseName = "";
        while (selectedCourseName.isEmpty()) {
            selectedCourseName = scanner.nextLine();
        }

        Course selectedCourse = CourseManager.getCourse(selectedCourseName);
        if (selectedCourse == null)
            return;

        SessionManager.setSelectedCourse(selectedCourse);
        SessionManager.takeQuiz(scanner);

        DataSaver.save(user);
    }
}