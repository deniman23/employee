package org.example.service.api;

import org.example.dao.model.Employee;
import org.example.dao.model.Post;
import org.example.EmployeeRepository;
import org.example.PostRepository;
import org.example.error.ResourceNotFoundException;
import org.example.service.dto.PostDto;
import org.example.service.dto.PostWithEmployeesDto;
import org.example.service.responce.RequestPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;



@Service
public class PostAPI {
    private final PostRepository postRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public PostAPI(PostRepository postRepository, EmployeeRepository employeeRepository) {
        this.postRepository = postRepository;
        this.employeeRepository = employeeRepository;
    }

    // Создание должности
    public PostDto createPost(RequestPost requestPost) {
        if (requestPost.getId() > 0 && postRepository.existsById(requestPost.getId())) {
            throw new ResourceNotFoundException("Post with ID " + requestPost.getId() + " already exists.");
        }
        Post post = new Post();
        post.setPostName(requestPost.getPostName());
        post = postRepository.save(post);
        return new PostDto(post);
    }

    // Изменение должности
    public PostDto changePost(RequestPost requestPost) {
        Post post = postRepository.findById(requestPost.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID " + requestPost.getId()));
        post.setPostName(requestPost.getPostName());
        post = postRepository.save(post);
        return new PostDto(post);
    }

    // Удаление должности по ID
    public boolean deletePost(int id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID " + id));
        postRepository.delete(post);
        return true;
    }

    // Вывод всех должностей
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    // Получение должности по ID
    public PostDto getPostById(int id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID " + id));
        return new PostDto(post);
    }

    // Вывод должностей с фамилиями сотрудников
    public List<PostWithEmployeesDto> getPostsWithEmployees() {
        List<Post> posts = postRepository.findAll();
        if (posts.isEmpty()) {
            return Collections.emptyList();
        } else {
            return posts.stream().map(post -> {
                List<String> employeesLastNames = employeeRepository.findAllByPostId(post.getId()).stream()
                        .sorted(Comparator.comparing(Employee::getLastName))
                        .map(Employee::getLastName)
                        .collect(Collectors.toList());
                return new PostWithEmployeesDto(new PostDto(post), employeesLastNames);
            }).collect(Collectors.toList());
        }
    }
}