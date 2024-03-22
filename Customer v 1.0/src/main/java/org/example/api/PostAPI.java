package org.example.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.example.entity.Post;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PostAPI {
    public static Map<String, Post> posts = new HashMap<>();
    //Метод приводит входящий String в json
    private static JsonObject parseJson(String data, Gson gson) {
        try {
            return gson.fromJson(data, JsonObject.class);
        } catch (Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
            return null;
        }
    }

    private static void processJson(JsonObject jsonData, boolean isChange, Map<String, Post> map) {
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

    //Вывод одной должности
    public static void outputPost(Scanner scanner, Map<String, Post> map) {
        System.out.println("Enter Name");
        String name = scanner.nextLine();
        map.values().stream()
                .filter(post -> post.getPostName().equals(name))
                .forEach(post -> System.out.println(post.getId() + ", " + post.getPostName()));
    }

    //Создание должности
    public static void createPost(Scanner scanner, Gson gson, Map<String, Post> map) {
        System.out.println("Enter json for new post:");
        String data = scanner.nextLine();
        JsonObject jsonData = parseJson(data, gson);
        if (jsonData != null) {
            processJson(jsonData, false, map);
        }
    }

    //Изменение json
    public static void changePost(Scanner scanner, Gson gson, Map<String, Post> map) {
        System.out.println("Enter json for existing post:");
        String data = scanner.nextLine();
        JsonObject jsonData = parseJson(data, gson);
        if (jsonData != null) {
            processJson(jsonData, true, map);
        }
    }

    //Удаление должности по ID
    public static void deletePost(Scanner scanner, Map<String, Post> map) {
        System.out.println("Enter ID");
        String id = scanner.nextLine();
        if (map.containsKey(id)) {
            map.remove(id);
            System.out.println("Success");
        } else
            System.out.println("Error");
    }

    //Вывод всех должностей с индексом
    public static void outputAllPosts(Map<String, Post> map) {
        map.values().forEach(post -> System.out.println(post.getId() + ", " + post.getPostName()));
    }
}
