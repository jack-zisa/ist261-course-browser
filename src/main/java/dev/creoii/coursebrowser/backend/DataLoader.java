package dev.creoii.coursebrowser.backend;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.creoii.coursebrowser.Main;
import dev.creoii.coursebrowser.api.CourseManager;
import dev.creoii.coursebrowser.backend.course.Course;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DataLoader {
    public static void load() {
        try {
            Path dataPath = getResourcePath("data");

            try (Stream<Path> paths = Files.walk(dataPath)) {
                paths.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".json")).forEach(DataLoader::processFile);
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private static Path getResourcePath(String resource) throws URISyntaxException, IOException {
        return Paths.get(DataLoader.class.getClassLoader().getResource(resource).toURI());
    }

    public static String getDataType(String filePath) {
        Path path = Paths.get(filePath);

        for (int i = 0; i < path.getNameCount(); i++) {
            if (path.getName(i).toString().equals("data") && i + 1 < path.getNameCount()) {
                return path.getName(i + 1).toString();
            }
        }
        return null;
    }

    private static void processFile(Path path) {
        try {
            String content = Files.readString(path, StandardCharsets.UTF_8);
            JsonElement element = JsonParser.parseString(content);

            String dataType = getDataType(path.toString());

            switch (dataType) {
                case "courses" -> {
                    CourseManager.registerCourse(Course.fromJson(element));
                }
                case "users" -> {
                    if (element.isJsonObject()) {
                        JsonObject object = (JsonObject) element;
                        String uuid = object.get("uuid").getAsString();

                        if (uuid.equals(Main.USER.getUuid().toString())) {
                            JsonArray purchasedCourses = object.getAsJsonArray("purchased_courses");
                            for (JsonElement courseElement : purchasedCourses) {
                                Main.USER.purchase(CourseManager.getCourse(courseElement.getAsString()));
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
