package org.example.controllers;


import org.example.services.PostService;
import org.example.dto.PostDto;
import org.example.dto.PostWithEmployeesDto;
import org.example.request.RequestPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody RequestPost requestPost) {
        PostDto createdPost = postService.create(requestPost);
        return ResponseEntity.ok(createdPost);
    }

    @PutMapping
    public ResponseEntity<PostDto> changePost( @RequestBody RequestPost requestPost) {
        PostDto updatedPost = postService.change(requestPost);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> terminatePost(@PathVariable int id) {
        postService.delete(id);
        return ResponseEntity.ok("Position deleted");
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> allPosts = postService.findAll();
        return allPosts.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(allPosts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable int id) {
        PostDto postDto = postService.getPostById(id);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/with_employees")
    public ResponseEntity<List<PostWithEmployeesDto>> getPostsWithEmployees() {
        List<PostWithEmployeesDto> postsWithEmployees = postService.getPostsWithEmployees();
        return postsWithEmployees.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(postsWithEmployees);
    }
}
