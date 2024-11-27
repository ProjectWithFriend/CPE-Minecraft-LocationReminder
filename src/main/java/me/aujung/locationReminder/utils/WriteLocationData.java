package me.aujung.locationReminder.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteLocationData {
    public static File locationFile = null;
    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private WriteLocationData() { }

    public static void setLocationData(File locationFile) {
        WriteLocationData.locationFile = locationFile;
    }

    public static void write(JsonObject jsonData) throws IOException {
        try (FileWriter writer = new FileWriter(locationFile)) {
            gson.toJson(jsonData, writer);
        }
    }
}