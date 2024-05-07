package org.example.spring.controllers;


import org.example.services.service.PostService;
import org.example.services.dto.PostDto;
import org.example.services.dto.PostWithEmployeesDto;
import org.example.services.response.ResponsePost;
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

    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@RequestBody ResponsePost responsePost) {
        PostDto createdPost = postService.createPost(responsePost);
        return ResponseEntity.ok(createdPost);
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<PostDto> changePost(@PathVariable int id, @RequestBody ResponsePost responsePost) {
        responsePost.setId(id);
        PostDto updatedPost = postService.changePost(responsePost);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> terminatePost(@PathVariable int id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Position deleted");
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> allPosts = postService.getAllPosts();
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
