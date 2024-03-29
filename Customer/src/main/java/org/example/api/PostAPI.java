package org.example.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.entity.Employee;
import org.example.entity.Post;
import org.example.service.DataService;

import java.io.IOException;
import java.util.*;

import static org.example.api.JSON.scanner;


public class PostAPI {
    private DataService dataService;
    private final JSON json;

    public PostAPI(DataService dataService, JSON json) {
        this.dataService = dataService;
        this.json = json;
    }


    // Создание должности
    public void createPost() {
        int postID = dataService.getPosts().size() + 1;
        System.out.println("Enter Name");
        String postName = scanner.nextLine();

        if (dataService.getPosts().containsKey(postID)) {
            System.out.println("Invalid id");

        } else if (dataService.getPosts().values().stream().anyMatch(post -> post.getPostName().equalsIgnoreCase(postName))) {
            System.out.println("Invalid name");

        } else {
            dataService.getPosts().put(postID, new Post(postID, postName.toLowerCase()));
            System.out.println("Success, id: " + postID);
        }
    }

    // Изменение должности вводить в формате json
    public void changePost() {
        System.out.println("Enter ID:");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter new position name:");
        String postName = scanner.nextLine();

        Post post = dataService.getPosts().get(id);
        if (post != null) {
            post.setPostName(postName);
            System.out.println("Post updated successfully");
        } else {
            System.out.println("Post not found");
        }
    }

    // Удаление должности по ID
    public void deletePost() {
        outputAllPosts();
        System.out.println("Enter ID");
        int id = scanner.nextInt();

        if (dataService.getPosts().containsKey(id)) {
            dataService.getPosts().remove(id);
            System.out.println("Success");
        } else
            System.out.println("Error");
    }

    // Вывод всех должностей с индексом
    public void outputAllPosts() {
        if (dataService.getPosts().isEmpty()) {
            System.out.println("Empty");
        } else {
            dataService.getPosts().values().stream()
                    .map(p -> {
                        try {
                            return json.convertPostToJson(p);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    })
                    .forEach(System.out::println);
        }
    }

    // Вывод одной должности
    public void outputPost() throws JsonProcessingException {
        System.out.println("Enter ID");
        int id = scanner.nextInt();
        scanner.nextLine();
        Post post = dataService.getPosts().get(id);
        String postJson = json.convertPostToJson(post);
        System.out.println(postJson);
    }

    // Вывод должностей с фамилиями сотрудников
    public void outputPostsWithEmployees() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        for (Map.Entry<Integer, Post> postEntry : dataService.getPosts().entrySet()) {
            String postJsonString = json.convertPostToJson(postEntry.getValue());
            try {
                ObjectNode postObject = (ObjectNode) mapper.readTree(postJsonString);
                ArrayNode employeesLastNamesArray = mapper.createArrayNode();
                dataService.getEmployees().stream()
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
