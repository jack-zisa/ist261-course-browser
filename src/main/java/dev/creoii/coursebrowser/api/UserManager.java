package dev.creoii.coursebrowser.api;

import dev.creoii.coursebrowser.backend.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {
    private static final Map<UUID, User> USERS = new HashMap<>();

    public static void registerUser(User user) {
        USERS.put(user.getUuid(), user);
    }

    public static User getUser(UUID uuid) {
        return USERS.get(uuid);
    }
}
