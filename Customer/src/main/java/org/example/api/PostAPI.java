package org.example.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.example.entity.Employee;
import org.example.entity.Post;

import java.util.*;


public class PostAPI extends JSON {
    private Map<Integer, Post> posts;

    public PostAPI(Map<Integer, Post> posts) {
        this.posts = posts;
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
    public void changePost(Map<Integer, Post> map) {
        System.out.println("Enter json for existing post:");
        String data = scanner.nextLine();
        JsonObject jsonData = parseJson(data, gson);
        if (jsonData != null) {
            processJson(jsonData, true, map);
        }
    }

    //Удаление должности по ID
    public void deletePost(Map<Integer, Post> map) {
        outputAllPosts(posts);
        System.out.println("Enter ID");
        int id = scanner.nextInt();


        if (map.containsKey(id)) {
            map.remove(id);
            System.out.println("Success");
        } else
            System.out.println("Error");
    }

    //Вывод всех должностей с индексом
    public void outputAllPosts(Map<Integer, Post> posts) {
        if (!posts.isEmpty()) {
            posts.values().forEach(post -> System.out.println("{\"id\": " + post.getId() +
                    ", \"postName\": " + post.getPostName() + "}"));
        } else {
            System.out.println("Empty");
        }

    }

    //Вывод одной должности
    public void outputPost(Map<Integer, Post> map) {
        System.out.println("Enter ID");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (map.containsKey(id)) {
            Post post = map.get(id);
            System.out.println("{\"id\": " + post.getId() +
                    ", \"postName\": \"" + post.getPostName() + "\"}");
        } else {
            System.out.println("Not found");
        }
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
