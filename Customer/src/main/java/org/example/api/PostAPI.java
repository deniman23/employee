package org.example.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.entity.Employee;
import org.example.entity.Post;

import java.io.IOException;
import java.util.*;

import static org.example.api.JSON.scanner;


public class PostAPI {
    private final Map<Integer, Post> posts;
    private final List<Employee> employees;
    private final JSON json;
    public PostAPI(Map<Integer, Post> posts, List<Employee> employees, JSON json) {
        this.posts = posts;
        this.employees = employees;
        this.json = json;
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
    public void changePost() {
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

    //Удаление должности по ID
    public void deletePost() {
        outputAllPosts();
        System.out.println("Enter ID");
        int id = scanner.nextInt();

        if (posts.containsKey(id)) {
            posts.remove(id);
            System.out.println("Success");
        } else
            System.out.println("Error");
    }

    //Вывод всех должностей с индексом
    public void outputAllPosts() {
        if (posts.isEmpty()) {
            System.out.println("Empty");
        } else {
            posts.values().stream()
                    //.map(json::convertPostToJson)
                    .map(p ->{
                        try {
                            return json.convertPostToJson(p);
                        }catch (IOException ex){
                            throw new RuntimeException(ex);
                        }
                    })
                    .forEach(System.out::println);
        }
    }

    //Вывод одной должности
    public void outputPost() throws JsonProcessingException {
        System.out.println("Enter ID");
        int id = scanner.nextInt();
        scanner.nextLine();
        Post post = posts.get(id);
        String postJson = json.convertPostToJson(post);
        System.out.println(postJson);
    }

    //Вывод должностей с фамилиями сотрудников
    public void outputPostsWithEmployees() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        for (Map.Entry<Integer, Post> postEntry : posts.entrySet()) {
            String postJsonString = json.convertPostToJson(postEntry.getValue());
            try {
                ObjectNode postObject = (ObjectNode) mapper.readTree(postJsonString);
                ArrayNode employeesLastNamesArray = mapper.createArrayNode();
                employees.stream()
                        .filter(employee -> employee.getPositionId() == postEntry.getKey())
                        .sorted(Comparator.comparing(Employee::getLastName))
                        .forEach(employee -> employeesLastNamesArray.add(employee.getLastName()));
                postObject.set("lastName", employeesLastNamesArray);
                System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(postObject));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
