package dev.creoii.coursebrowser.backend.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public final class JsonUtils {
    public static String getString(JsonObject object, String key) {
        if (object.has(key)) {
            return object.get(key).getAsString();
        }
        throw new JsonParseException("No key '" + key + "' found in object.");
    }

    public static String getString(JsonObject object, String key, String fallback) {
        if (object.has(key)) {
            return object.get(key).getAsString();
        }
        return fallback;
    }
}
