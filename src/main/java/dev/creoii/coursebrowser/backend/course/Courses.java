package dev.creoii.coursebrowser.backend.course;

import java.util.ArrayList;
import java.util.List;

public class Courses {
    private static final List<Course> courses = new ArrayList<>();

    public static void registerCourse(Course course) {
        courses.add(course);
    }

    public static List<Course> getCourses() {
        return courses;
    }

    public static Course getCourse(String name) {
        for (Course course : courses) {
            if (course.getName().toLowerCase().equals(name.toLowerCase()))
                return course;
        }
        return null;
    }
}
