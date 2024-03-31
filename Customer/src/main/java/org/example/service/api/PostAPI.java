package org.example.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.dao.model.Employee;
import org.example.dao.model.Post;
import org.example.dao.repository.EmployeeDataService;
import org.example.dao.repository.PostDataService;
import org.example.service.mapper.JsonMapper;

import java.io.IOException;
import java.util.*;

import static org.example.service.mapper.JsonMapper.scanner;


public class PostAPI {
    private final PostDataService postDataService;
    private final EmployeeDataService employeeDataService;
    private final JsonMapper jsonMapper;

    public PostAPI(PostDataService postDataService, EmployeeDataService employeeDataService, JsonMapper jsonMapper) {
        this.postDataService = postDataService;
        this.employeeDataService = employeeDataService;
        this.jsonMapper = jsonMapper;
    }


    // Создание должности
    public void createPost() {
        System.out.println("Enter Name");
        String postName = scanner.nextLine();

        Optional<Post> createdPost = postDataService.createPost(postDataService.getPosts().size() + 1, postName.toLowerCase());
        if (createdPost.isPresent()) {
            System.out.println("Success, id: " + createdPost.get().getId());
        } else {
            System.out.println("Invalid name or ID already exists");
        }
    }

    // Изменение должности
    public void changePost() {
        System.out.println("Enter ID:");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter new position name:");
        String postName = scanner.nextLine();

        Optional<Post> updatedPost = postDataService.updatePost(id, postName);
        if (updatedPost.isPresent()) {
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

        Optional<Post> deletedPost = postDataService.deletePost(id);
        if (deletedPost.isPresent()) {
            System.out.println("Success");
        } else {
            System.out.println("Error: Post not found");
        }
    }

    // Вывод всех должностей
    public void outputAllPosts() {
        List<Post> posts = postDataService.getPosts();
        if (posts.isEmpty()) {
            System.out.println("The list of positions is empty.");
        } else {
            posts.forEach(post -> {
                try {
                    System.out.println(jsonMapper.convertPostToJson(post));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }

    // Вывод одной должности
    public void outputPost() {
        System.out.println("Enter ID");
        int id = scanner.nextInt();
        scanner.nextLine();

        Post post = postDataService.getPosts().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        if (post != null) {
            try {
                String postJson = jsonMapper.convertPostToJson(post);
                System.out.println(postJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Post not found");
        }
    }

    // Вывод должностей с фамилиями сотрудников
    public void outputPostsWithEmployees() {
        if (postDataService.getPosts().isEmpty()) {
            System.out.println("The list of positions is empty.");
        } else {
            ObjectMapper mapper = new ObjectMapper();
            postDataService.getPosts().forEach(post -> {
                try {
                    String postJsonString = jsonMapper.convertPostToJson(post);
                    ObjectNode postObject = (ObjectNode) mapper.readTree(postJsonString);
                    ArrayNode employeesLastNamesArray = mapper.createArrayNode();
                    employeeDataService.getEmployees().stream()
                            .filter(employee -> employee.getPostID() == post.getId())
                            .sorted(Comparator.comparing(Employee::getLastName))
                            .forEach(employee -> employeesLastNamesArray.add(employee.getLastName()));
                    postObject.set("lastName", employeesLastNamesArray);
                    System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(postObject));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}