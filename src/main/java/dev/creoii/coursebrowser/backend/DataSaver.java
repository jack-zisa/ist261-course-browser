package dev.creoii.coursebrowser.backend;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataSaver {
    public static void save(User user) {
        try {
            Path dataPath = getResourcePath("data/users");
            Path userPath = dataPath.resolve(user.getUuid().toString() + ".json");
            Files.createFile(userPath);
            Files.write(userPath, "{}".getBytes());
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private static Path getResourcePath(String resource) throws URISyntaxException, IOException {
        return Paths.get(DataSaver.class.getClassLoader().getResource(resource).toURI());
    }
}
