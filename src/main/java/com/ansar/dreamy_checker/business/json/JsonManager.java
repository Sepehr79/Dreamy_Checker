package com.ansar.dreamy_checker.business.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Type;

@Component
public class JsonManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public void createJson(Object input, String fileName, Type type) throws IOException {
        try(Writer writer = new FileWriter(fileName + ".json")) {
            GSON.toJson(input, type, writer);
        }
    }

    public <T> T readJson(String fileName, Class<T> type) throws IOException {
        try(Reader reader = new FileReader(fileName + ".json")) {
            return GSON.fromJson(reader, (Type) type);
        }
    }

}
