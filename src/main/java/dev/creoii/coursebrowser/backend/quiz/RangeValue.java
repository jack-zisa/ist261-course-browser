package dev.creoii.coursebrowser.backend.quiz;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.concurrent.ThreadLocalRandom;

public record RangeValue(int min, int max) {
    public static RangeValue fromJson(JsonElement element) {
        JsonArray array = element.getAsJsonArray();
        return new RangeValue(array.get(0).getAsInt(), array.get(1).getAsInt());
    }

    public int getRandom() {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * When a Question is built, both min & max will be the same, so we just return one of those.
     */
    public int getBuilt() {
        return min;
    }
}