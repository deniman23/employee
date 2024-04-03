package org.example.service.console;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.dao.model.Post;
import org.example.service.api.PostAPI;
import org.example.service.dto.PostDto;
import org.example.service.dto.PostWithEmployeesDto;
import org.example.service.mapper.JsonMapper;
import org.example.service.responce.ResponsePost;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.example.service.mapper.JsonMapper.scanner;

public class PostConsoleService {
    private final PostAPI postAPI;
    private final JsonMapper jsonMapper;


    public PostConsoleService(PostAPI postAPI, JsonMapper jsonMapper) {
        this.postAPI = postAPI;
        this.jsonMapper = jsonMapper;
    }

    public void createPostConsole() {
        System.out.println("Enter Name");
        String postName = scanner.nextLine();

        ResponsePost responsePost = new ResponsePost(0, postName);
        Optional<Post> createdPost = postAPI.createPost(responsePost);
        if (createdPost.isPresent()) {
            System.out.println("Success, id: " + createdPost.get().getId());
        } else {
            System.out.println("Invalid name or ID already exists");
        }
    }

    public void changePostConsole(){
        System.out.println("Enter ID:");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter new position name:");
        String postName = scanner.nextLine();

        ResponsePost responsePost = new ResponsePost(id, postName);
        Optional<Post> changedPost = postAPI.changePost(responsePost);
        if (changedPost.isPresent()) {
            System.out.println("Post updated successfully");
        } else {
            System.out.println("Post not found");
        }
    }

    public void deletePostConsole() {
        System.out.println("Enter ID:");
        int id = scanner.nextInt();
        scanner.nextLine();

        boolean isDeleted = postAPI.deletePost(id);
        if (isDeleted) {
            System.out.println("Success");
        } else {
            System.out.println("Error: Post not found");
        }
    }

    public void outputAllPostsConsole() {
        List<PostDto> postDtos = postAPI.getAllPosts();
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

    public void outputPostConsole() {
        System.out.println("Enter ID:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Optional<PostDto> postDtoOptional = postAPI.getPostById(id);
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

    public void outputPostsWithEmployeesConsole() {
        List<PostWithEmployeesDto> postsWithEmployees = postAPI.getPostsWithEmployees();
        if (postsWithEmployees.isEmpty()) {
            System.out.println("The list of positions is empty.");
        } else {
            postsWithEmployees.forEach(postWithEmployeesDto -> {
                try {
                    System.out.println(jsonMapper.convertPostWithEmployeesToJson(postWithEmployeesDto));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}