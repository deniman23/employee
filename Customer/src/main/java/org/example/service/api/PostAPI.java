package org.example.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.dao.model.Employee;
import org.example.dao.model.Post;
import org.example.dao.repository.EmployeeDataService;
import org.example.dao.repository.PostDataService;
import org.example.service.dto.PostDto;
import org.example.service.dto.PostWithEmployeesDto;
import org.example.service.mapper.JsonMapper;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
        List<PostDto> postDtos = postDataService.getPosts().stream()
                .map(PostDto::new)
                .collect(Collectors.toList());

        if (postDtos.isEmpty()) {
            System.out.println("The list of positions is empty.");
        } else {
            postDtos.forEach(postDto -> {
                try {
                    System.out.println(jsonMapper.convertPostToJson(postDto));
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

        Optional<PostDto> postDtoOptional = postDataService.getPosts().stream()
                .filter(p -> p.getId() == id)
                .map(PostDto::new)
                .findFirst();

        if (postDtoOptional.isPresent()) {
            try {
                String postJson = jsonMapper.convertPostToJson(postDtoOptional.get());
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
        List<PostDto> postDtos = postDataService.positionToDto();

        if (postDtos.isEmpty()) {
            System.out.println("The list of positions is empty.");
        } else {
            postDtos.forEach(postDto -> {
                List<String> employeesLastNames = employeeDataService.getEmployees().stream()
                        .filter(employee -> employee.getPostID() == postDto.getId())
                        .sorted(Comparator.comparing(Employee::getLastName))
                        .map(Employee::getLastName)
                        .collect(Collectors.toList());
                PostWithEmployeesDto postWithEmployeesDto = new PostWithEmployeesDto(postDto, employeesLastNames);
                try {
                    System.out.println(jsonMapper.convertPostWithEmployeesToJson(postWithEmployeesDto));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}