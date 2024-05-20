package org.example.dao.service;

import org.example.dao.model.Post;
import org.example.dao.repository.PostRepository;
import org.example.error.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;



@Service
public class PostServiceDao {
    private final PostRepository postRepository;

    @Autowired
    public PostServiceDao(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // Сохранение должности
    public Post save(Post post) {
        return postRepository.save(post);
    }

    // Удаление должности по ID
    public void delete(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID " + id));
        postRepository.delete(post);
    }

    // Вывод всех должностей
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    // Получение должности по ID
    public Optional<Post> findById(Integer id) {
        return postRepository.findById(id);
    }
}