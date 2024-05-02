package org.example.spring.controllers;


import org.example.dao.model.Post;
import org.example.service.api.PostAPI;
import org.example.service.dto.PostDto;
import org.example.service.dto.PostWithEmployeesDto;
import org.example.service.responce.RequestPost;
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
    public ResponseEntity<PostDto> createPost(@RequestBody RequestPost requestPost) {
        PostDto createdPost = postAPI.createPost(requestPost);
        return ResponseEntity.ok(createdPost);
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<PostDto> changePost(@PathVariable int id, @RequestBody RequestPost requestPost) {
        requestPost.setId(id);
        PostDto updatedPost = postAPI.changePost(requestPost);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> terminatePost(@PathVariable int id) {
        postAPI.deletePost(id);
        return ResponseEntity.ok("Position deleted");
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> allPosts = postAPI.getAllPosts();
        return allPosts.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(allPosts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable int id) {
        PostDto postDto = postAPI.getPostById(id);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/with_employees")
    public ResponseEntity<List<PostWithEmployeesDto>> getPostsWithEmployees() {
        List<PostWithEmployeesDto> postsWithEmployees = postAPI.getPostsWithEmployees();
        return postsWithEmployees.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(postsWithEmployees);
    }
}
