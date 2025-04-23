package dev.creoii.coursebrowser.api;

import dev.creoii.coursebrowser.backend.course.Course;

public class CourseManager {
    private static final CourseList COURSES = new CourseList();
    private static final CourseList CART = new CourseList();

    public static void registerCourse(Course course) {
        COURSES.add(course);
    }

    public static CourseList getCourses() {
        return COURSES;
    }

    public static CourseList getCart() {
        return CART;
    }

    public static Course getCourse(String name) {
        for (Course course : COURSES) {
            if (course.name().toLowerCase().equals(name.toLowerCase()))
                return course;
        }
        return null;
    }
}
