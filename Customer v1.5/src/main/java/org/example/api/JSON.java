package org.example.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.example.entity.Post;

import java.util.Map;

public class JSON {
    static Gson gson = new Gson();
    //Метод приводит входящий String в json
    public static JsonObject parseJson(String data, Gson gson) {
        try {
            return gson.fromJson(data, JsonObject.class);
        } catch (Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
            return null;
        }
    }

    public static void processJson(JsonObject jsonData, boolean isChange, Map<String, Post> map) {
        String id = jsonData.get("id").getAsString();
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
}