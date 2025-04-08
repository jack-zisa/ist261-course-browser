package dev.creoii.coursebrowser.backend;

import dev.creoii.coursebrowser.backend.course.Course;
import dev.creoii.coursebrowser.backend.course.CourseList;

public class User {
    private final CourseList purchasedCourses;

    public User() {
        this.purchasedCourses = new CourseList();
    }

    public CourseList getPurchasedCourses() {
        return purchasedCourses;
    }

    public void purchase(Course course) {
        if (!purchasedCourses.contains(course)) {
            purchasedCourses.add(course);
        }
    }
}
