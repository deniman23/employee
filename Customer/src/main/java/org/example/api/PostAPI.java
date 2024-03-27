package org.example.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.entity.Employee;
import org.example.entity.Post;

import java.util.*;


public class PostAPI extends JSON {
    private Map<Integer, Post> posts;

    public PostAPI(Map<Integer, Post> posts) {
        this.posts = posts;
    }

    private String convertPostToJson(Post post) {
        JsonObject postJson = new JsonObject();
        if (post != null) {
            postJson.addProperty("id", post.getId());
            postJson.addProperty("postName", post.getPostName());
        } else {
            postJson.addProperty("id", "Not found");
            postJson.addProperty("postName", "Not found");
        }
        return gson.toJson(postJson);
    }

    //Создание должности
    public void createPost() {
        int postID = posts.size() + 1;
        System.out.println("Enter Name");
        String postName = scanner.nextLine();

        if (posts.containsKey(postID)) {
            System.out.println("Invalid id");

        } else if (posts.values().stream().anyMatch(post -> post.getPostName().equalsIgnoreCase(postName))) {
            System.out.println("Invalid name");

        } else {
            posts.put(postID, new Post(postID, postName.toLowerCase()));
            System.out.println("Success, id: " + postID);
        }
    }

    //Изменение должности вводить в формате json
    public void changePost(Map<Integer, Post> posts) {
        System.out.println("Enter ID:");
        int id = Integer.parseInt(scanner.nextLine()); // Используйте Integer.parseInt для чтения числа

        System.out.println("Enter new position name:");
        String postName = scanner.nextLine();

        Post post = posts.get(id);
        if (post != null) {
            post.setPostName(postName);
            System.out.println("Post updated successfully");
        } else {
            System.out.println("Post not found");
        }
    }
//        System.out.println("Enter json for existing post:");
//        String data = scanner.nextLine();
//        JsonObject jsonData = parseJson(data, gson);
//        if (jsonData != null) {
//            processJson(jsonData, true, map);
//        }

    //Удаление должности по ID
    public void deletePost(Map<Integer, Post> posts) {
        outputAllPosts(posts);
        System.out.println("Enter ID");
        int id = scanner.nextInt();

        if (posts.containsKey(id)) {
            posts.remove(id);
            System.out.println("Success");
        } else
            System.out.println("Error");
    }

    //Вывод всех должностей с индексом
    public void outputAllPosts(Map<Integer, Post> posts) {
        if (posts.isEmpty()) {
            System.out.println("Empty");
        } else {
            posts.values().stream()
                    .map(this::convertPostToJson)
                    .forEach(System.out::println);
        }
    }

    //Вывод одной должности
    public void outputPost(Map<Integer, Post> map) {
        System.out.println("Enter ID");
        int id = scanner.nextInt();
        scanner.nextLine();
        Post post = map.get(id);
        String postJson = convertPostToJson(post);
        System.out.println(postJson);
    }

    //Вывод должностей с фамилиями сотрудников
    public void outputPostsWithEmployees(Map<Integer, Post> posts, List<Employee> employees) {
        for (Map.Entry<Integer, Post> postEntry : posts.entrySet()) {
            JsonObject postObject = new JsonObject();
            postObject.addProperty("PostName", postEntry.getValue().getPostName());

            JsonArray employeesLastNamesArray = new JsonArray();
            employees.stream()
                    .filter(employee -> employee.getPositionId() == postEntry.getKey())
                    .sorted(Comparator.comparing(Employee::getLastName))
                    .forEach(employee -> employeesLastNamesArray.add(employee.getLastName()));

            postObject.add("EmployeesLastNames", employeesLastNamesArray);

            System.out.println(gson.toJson(postObject));
        }
    }
}
