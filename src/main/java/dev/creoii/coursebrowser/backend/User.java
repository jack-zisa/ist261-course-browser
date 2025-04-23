package dev.creoii.coursebrowser.backend;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.creoii.coursebrowser.backend.course.Course;
import dev.creoii.coursebrowser.api.CourseList;

import java.util.UUID;

public class User {
    private final UUID uuid;
    private final CourseList purchasedCourses;

    public User(UUID uuid) {
        this.uuid = uuid;
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

    public JsonObject write() {
        JsonObject object = new JsonObject();
        object.addProperty("uuid", uuid.toString());

        JsonArray purchasedCourses = new JsonArray();
        this.purchasedCourses.forEach(course -> purchasedCourses.add(course.name()));

        object.add("purchased_courses", purchasedCourses);
        return object;
    }
}
