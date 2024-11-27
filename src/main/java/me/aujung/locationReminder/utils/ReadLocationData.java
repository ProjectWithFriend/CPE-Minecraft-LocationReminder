package me.aujung.locationReminder.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public  class ReadLocationData {
    public static File locationFile = null;
    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private ReadLocationData() { }

    public static void setLocationData(File locationFile) {
        ReadLocationData.locationFile = locationFile;
    }

    public static JsonObject read() throws IOException {
        if (!locationFile.exists() || locationFile.length() == 0) {
            return new JsonObject(); // Return an empty JSON object if the file doesn't exist or is empty
        }

        try (FileReader reader = new FileReader(locationFile)) {
            return gson.fromJson(reader, JsonObject.class);
        }
    }
}
