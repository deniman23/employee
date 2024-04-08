package org.example.service.api;

import org.example.dao.model.Employee;
import org.example.dao.model.Post;
import org.example.dao.repository.EmployeeDataService;
import org.example.dao.repository.PostDataService;
import org.example.service.dto.PostDto;
import org.example.service.dto.PostWithEmployeesDto;
import org.example.service.responce.ResponsePost;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class PostAPI {
    private final PostDataService postDataService;
    private final EmployeeDataService employeeDataService;


    public PostAPI(PostDataService postDataService, EmployeeDataService employeeDataService) {
        this.postDataService = postDataService;
        this.employeeDataService = employeeDataService;
    }


    // Создание должности
    public Optional<Post> createPost(ResponsePost responsePost) {
        return postDataService.createPost(postDataService.getPosts().size() + 1, responsePost.getPostName());
    }

    // Изменение должности
    public Optional<Post> changePost(ResponsePost responsePost) {
        return postDataService.updatePost(responsePost.getId(), responsePost.getPostName());
    }

    // Удаление должности по ID
    public boolean deletePost(int id) {
        Optional<Post> deletedPost = postDataService.deletePost(id);
        return deletedPost.isPresent();
    }

    // Вывод всех должностей
    public List<PostDto> getAllPosts() {
        return postDataService.getPosts().stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    public Optional<PostDto> getPostById(int id) {
        return postDataService.getPosts().stream()
                .filter(p -> p.getId() == id)
                .map(PostDto::new)
                .findFirst();
    }

    // Вывод должностей с фамилиями сотрудников
    public List<PostWithEmployeesDto> getPostsWithEmployees() {
        List<PostDto> postDtos = postDataService.positionToDto();
        if (postDtos.isEmpty()) {
            return Collections.emptyList();
        } else {
            return postDtos.stream().map(postDto -> {
                List<String> employeesLastNames = employeeDataService.getEmployees().stream()
                        .filter(employee -> employee.getPostID() == postDto.getId())
                        .sorted(Comparator.comparing(Employee::getLastName))
                        .map(Employee::getLastName)
                        .collect(Collectors.toList());
                return new PostWithEmployeesDto(postDto, employeesLastNames);
            }).collect(Collectors.toList());
        }
    }
}