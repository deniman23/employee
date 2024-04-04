package org.example.spring.controllers;


import org.example.dao.model.Post;
import org.example.service.api.PostAPI;
import org.example.service.dto.PostDto;
import org.example.service.dto.PostWithEmployeesDto;
import org.example.service.responce.ResponsePost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostAPI postAPI;

    @Autowired
    public PostController(PostAPI postAPI){
        this.postAPI = postAPI;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createPost(@RequestBody ResponsePost responsePost) {
        Optional<Post> createdPost = postAPI.createPost(responsePost);
        return createdPost
                .map(post -> ResponseEntity.ok().body((Object)post))
                .orElseGet(() -> ResponseEntity.badRequest().body("Unable to create post"));
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<?> changePost(@PathVariable int id, @RequestBody ResponsePost responsePost) {
        responsePost.setId(id);
        Optional<Post> updatedPost = postAPI.changePost(responsePost);
        return updatedPost
                .map(employee -> ResponseEntity.ok().body(employee))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> terminatePost(@PathVariable int id) {
        postAPI.deletePost(id);
        return ResponseEntity.ok().body("Position delete");
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> allPosts = postAPI.getAllPosts();
        if (allPosts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(allPosts);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable int id) {
        Optional<PostDto> postDto = postAPI.getPostById(id);
        return postDto
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/with_employees")
    public ResponseEntity<List<PostWithEmployeesDto>> getPostsWithEmployees() {
        List<PostWithEmployeesDto> postsWithEmployees = postAPI.getPostsWithEmployees();
        if (postsWithEmployees.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(postsWithEmployees);
        }
    }
}
