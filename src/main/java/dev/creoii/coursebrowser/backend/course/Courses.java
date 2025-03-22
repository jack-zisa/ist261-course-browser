package dev.creoii.coursebrowser.backend.course;

import java.util.ArrayList;
import java.util.List;

public class Courses {
    private static final List<Course> courses = new ArrayList<>();

    public static final void registerCourse(Course course) {
        courses.add(course);
    }

    public static final List<Course> getCourses() {
        return courses;
    }
}
