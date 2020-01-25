package ru.javawebinar.basejava.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.javawebinar.basejava.model.Section;

import java.io.Reader;
import java.io.Writer;

public class JsonParser {
    private static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Section.class, new JsonSectionAdapter())
            .create();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }

    public static <T> T read(String section_value, Class<T> sectionClass) {
        return GSON.fromJson(section_value, sectionClass);
    }

    public static <T> String write(T section_value, Class<T> sectionClass) {
        return GSON.toJson(section_value, sectionClass);
    }
}
