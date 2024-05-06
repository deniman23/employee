package org.example.services.service;

import org.example.dao.model.Employee;
import org.example.dao.model.Post;
import org.example.error.ResourceNotFoundException;
import org.example.services.dto.PostDto;
import org.example.services.dto.PostWithEmployeesDto;
import org.example.services.response.ResponsePost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostServiceDao postServiceDao;
    private final EmployeeServiceDao employeeServiceDao;

    @Autowired
    public PostService(PostServiceDao postServiceDao, EmployeeServiceDao employeeServiceDao) {
        this.postServiceDao = postServiceDao;
        this.employeeServiceDao = employeeServiceDao;
    }

    // Создание должности
    public PostDto createPost(ResponsePost responsePost) {
        if (responsePost.getId() > 0 && postServiceDao.findById(responsePost.getId()).isPresent()) {
            throw new ResourceNotFoundException("Post with ID " + responsePost.getId() + " already exists.");
        }
        Post post = new Post();
        post.setPostName(responsePost.getPostName());
        post = postServiceDao.savePost(post);
        return new PostDto(post);
    }

    public PostDto changePost(ResponsePost responsePost) {
        Post post = postServiceDao.findById(responsePost.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID " + responsePost.getId()));
        post.setPostName(responsePost.getPostName());
        post = postServiceDao.savePost(post);
        return new PostDto(post);
    }

    // Удаление должности по ID
    public void deletePost(int id) {
        postServiceDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID " + id));
        postServiceDao.deletePost(id);
    }

    // Вывод всех должностей
    public List<PostDto> getAllPosts() {
        return postServiceDao.findAllPosts().stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

    // Получение должности по ID
    public PostDto getPostById(int id) {
        Post post = postServiceDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID " + id));
        return new PostDto(post);
    }
    // Вывод должностей с фамилиями сотрудников
    public List<PostWithEmployeesDto> getPostsWithEmployees() {
        List<Post> posts = postServiceDao.findAllPosts();
        if (posts.isEmpty()) {
            return Collections.emptyList();
        } else {
            return posts.stream().map(post -> {
                List<Employee> employees = employeeServiceDao.findAllEmployeesByPost(post.getId());
                List<String> employeesLastNames = employees.stream()
                        .sorted(Comparator.comparing(Employee::getLastName))
                        .map(Employee::getLastName)
                        .collect(Collectors.toList());
                return new PostWithEmployeesDto(new PostDto(post), employeesLastNames);
            }).collect(Collectors.toList());
        }
    }
}