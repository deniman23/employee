package org.example.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.example.entity.Employee;
import org.example.entity.Post;

import java.util.*;

import static org.example.Main.posts;


public class PostAPI extends JSON {

    //Создание должности
    public static void createPost() {
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
    public static void changePost(Map<Integer, Post> map) {
        System.out.println("Enter json for existing post:");
        String data = scanner.nextLine();
        JsonObject jsonData = parseJson(data, gson);
        if (jsonData != null) {
            processJson(jsonData, true, map);
        }
    }

    //Удаление должности по ID
    public static void deletePost(Map<Integer, Post> map) {
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
    public static void outputAllPosts(Map<Integer, Post> posts) {
        if (!posts.isEmpty()) {
            posts.values().forEach(post -> System.out.println("{\"id\": " + post.getId() +
                    ", \"postName\": " + post.getPostName() + "}"));
        } else {
            System.out.println("Empty");
        }

    }

    //Вывод одной должности
    public static void outputPost(Map<Integer, Post> map) {
        System.out.println("Enter ID");
        int id = scanner.nextInt();
        scanner.nextLine(); // Считываем оставшийся символ новой строки

        if (map.containsKey(id)) {
            Post post = map.get(id);
            System.out.println("{\"id\": " + post.getId() +
                    ", \"postName\": \"" + post.getPostName() + "\"}");
        } else {
            System.out.println("Not found");
        }
    }

    //Вывод должностей с фамилиями сотрудников
    public static void outputPostsWithEmployees(Map<Integer, Post> posts, List<Employee> employees) {
        for (Map.Entry<Integer, Post> postEntry : posts.entrySet()) {
            JsonObject postObject = new JsonObject();
            postObject.addProperty("PostName", postEntry.getValue().getPostName());
            // Фильтруем и сортируем сотрудников по фамилии, принадлежащих к текущей должности
            JsonArray employeesLastNamesArray = new JsonArray();
            employees.stream()
                    .filter(employee -> employee.getPositionId() == postEntry.getKey())
                    .sorted(Comparator.comparing(Employee::getLastName))
                    .forEach(employee -> employeesLastNamesArray.add(employee.getLastName()));

            postObject.add("EmployeesLastNames", employeesLastNamesArray);

            // Выводим информацию о каждой должности в отдельной строке
            System.out.println(gson.toJson(postObject));
        }
    }
}
