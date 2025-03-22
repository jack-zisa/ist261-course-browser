package dev.creoii.coursebrowser.backend;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dev.creoii.coursebrowser.backend.course.Course;
import dev.creoii.coursebrowser.backend.course.Courses;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DataLoader {
    private static final Gson GSON = new Gson();

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
                    Course course = Course.fromJson(element);
                    Courses.registerCourse(course);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
