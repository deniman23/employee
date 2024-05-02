package org.example.service.console;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.dao.model.Post;
import org.example.service.api.PostAPI;
import org.example.service.dto.PostDto;
import org.example.service.dto.PostWithEmployeesDto;
import org.example.service.mapper.JsonMapper;
import org.example.service.responce.RequestPost;

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
        System.out.println("Enter Name:");
        String postName = scanner.nextLine();

        RequestPost requestPost = new RequestPost(0, postName);
        PostDto createdPost = postAPI.createPost(requestPost);
        System.out.println("Success, id: " + createdPost.getId());
    }

    public void changePostConsole() {
        System.out.println("Enter ID:");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter new position name:");
        String postName = scanner.nextLine();

        RequestPost requestPost = new RequestPost(id, postName);
        PostDto changedPost = postAPI.changePost(requestPost);
        System.out.println("Post updated successfully");
    }

    public void deletePostConsole() {
        System.out.println("Enter ID:");
        int id = scanner.nextInt();
        scanner.nextLine();

        postAPI.deletePost(id);
        System.out.println("Success");
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

        Optional<PostDto> postDtoOptional = Optional.ofNullable(postAPI.getPostById(id));
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
