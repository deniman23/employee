package org.example.api;

import com.google.gson.*;
import org.example.entity.Post;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Map;

public class JSON implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    static Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new JSON())
            .setPrettyPrinting()
            .create();
    static JsonObject postObject = new JsonObject();

    //Метод приводит входящий String в json
    public static JsonObject parseJson(String data, Gson gson) {
        try {
            return gson.fromJson(data, JsonObject.class);
        } catch (Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
            return null;
        }
    }

    public static void processJson(JsonObject jsonData, boolean isChange, Map<Integer, Post> map) {
        Integer id = Integer.valueOf(jsonData.get("id").getAsString());
        String postName = jsonData.get("postName").getAsString();
        if (isChange) {
            if (map.containsKey(id)) {
                map.get(id).setPostName(postName);
                System.out.println("Post updated successfully.");
            } else {
                System.out.println("Post with given ID does not exist.");
            }
        } else {
            map.put(id, new Post(id, postName));
            System.out.println("Post created successfully.");
        }
    }

    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString()); // "yyyy-mm-dd"
    }

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDate.parse(json.getAsString());
    }
}
