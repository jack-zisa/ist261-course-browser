package dev.creoii.coursebrowser.api;

import dev.creoii.coursebrowser.backend.course.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseManager {
    private static final List<Course> COURSES = new ArrayList<>();

    public static void registerCourse(Course course) {
        COURSES.add(course);
    }

    public static List<Course> getCourses() {
        return COURSES;
    }

    public static Course getCourse(String name) {
        for (Course course : COURSES) {
            if (course.getName().toLowerCase().equals(name.toLowerCase()))
                return course;
        }
        return null;
    }
}
