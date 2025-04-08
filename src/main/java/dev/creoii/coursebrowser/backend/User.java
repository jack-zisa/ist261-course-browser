package dev.creoii.coursebrowser.backend;

import dev.creoii.coursebrowser.backend.course.Course;
import dev.creoii.coursebrowser.backend.course.CourseList;

import java.util.UUID;

public class User {
    private final UUID uuid;
    private final CourseList purchasedCourses;

    public User() {
        this.uuid = UUID.randomUUID();
        this.purchasedCourses = new CourseList();
    }

    public UUID getUuid() {
        return uuid;
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
