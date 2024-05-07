package org.example.services.console;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.services.service.PostService;
import org.example.services.dto.PostDto;
import org.example.services.dto.PostWithEmployeesDto;
import org.example.services.mapper.JsonMapper;
import org.example.services.response.ResponsePost;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.example.services.mapper.JsonMapper.scanner;

@Service
public class PostConsoleService {
    private final PostService postService;
    private final JsonMapper jsonMapper;


    public PostConsoleService(PostService postService, JsonMapper jsonMapper) {
        this.postService = postService;
        this.jsonMapper = jsonMapper;
    }

    public void createPostConsole() {
        System.out.println("Enter Name:");
        String postName = scanner.nextLine();

        ResponsePost responsePost = new ResponsePost(0, postName);
        PostDto createdPost = postService.createPost(responsePost);
        System.out.println("Success, id: " + createdPost.getId());
    }

    public void changePostConsole() {
        System.out.println("Enter ID:");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter new position name:");
        String postName = scanner.nextLine();

        ResponsePost responsePost = new ResponsePost(id, postName);
        postService.changePost(responsePost);
        System.out.println("Post updated successfully");
    }

    public void deletePostConsole() {
        System.out.println("Enter ID:");
        int id = scanner.nextInt();
        scanner.nextLine();

        postService.deletePost(id);
        System.out.println("Success");
    }

    public void outputAllPostsConsole() {
        List<PostDto> postDtos = postService.getAllPosts();
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

        Optional<PostDto> postDtoOptional = Optional.ofNullable(postService.getPostById(id));
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
        List<PostWithEmployeesDto> postsWithEmployees = postService.getPostsWithEmployees();
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
