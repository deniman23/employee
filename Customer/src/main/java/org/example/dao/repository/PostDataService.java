package org.example.dao.repository;

import org.example.dao.model.Post;
import org.example.service.dto.PostDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PostDataService {
    private final List<Post> posts;


    public PostDataService(List<Post> posts) {
        this.posts = posts;
        addPost(new Post(1, "dev"));
        addPost(new Post(2, "ceo"));
        addPost(new Post(3, "qa"));
    }

    public Post createPost(Post post) {
        post.setId(posts.size() + 1);
        posts.add(post);
        return post;
    }


    public List<PostDto> getAllPosts() {
        return findAll().stream()
                .map(post -> new PostDto(post.getId(), post.getPostName()))
                .collect(Collectors.toList());
    }

    public List<Post> getPosts() {
        return posts;
    }

    public Optional<Post> findById(int id) {
        return posts.stream()
                .filter(p -> Integer.valueOf(p.getId()).equals(id))
                .findFirst();
    }

    public Optional<Post> updatePost(int id, String postName) {
        Optional<Post> existingPost = findById(id);
        if (existingPost.isEmpty()) {
            return Optional.empty();
        }
        existingPost.get().setPostName(postName);
        return existingPost;
    }

    public Optional<Post> deletePost(int id) {
        Optional<Post> postToDelete = findById(id);
        postToDelete.ifPresent(posts::remove);
        return postToDelete;
    }

    public void addPost(Post post) {
        if (findById(post.getId()).isPresent()) {
            return;
        }
        posts.add(post);
    }

    public List<Post> findAll() {
        return new ArrayList<>(posts);
    }

    public List<PostDto> positionToDto() {
        return this.posts.stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }

}
