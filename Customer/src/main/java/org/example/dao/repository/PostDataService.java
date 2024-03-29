package org.example.dao.repository;

import org.example.dao.model.Post;

import java.util.List;
import java.util.Optional;

public class PostDataService {
    private final List<Post> posts;

    public PostDataService(List<Post> posts) {
        this.posts = posts;
        initializePosts();
    }

    public Optional<Post> createPost(int id, String postName) {
        if (posts.stream().anyMatch(p -> p.getId() == id)) {
            throw new IllegalArgumentException("Post with ID " + id + " already exists.");
        }
        Post newPost = new Post(id, postName);
        posts.add(newPost);
        return Optional.of(newPost);
    }

    public void initializePosts() {
        posts.add(new Post(1, "dev"));
        posts.add(new Post(2, "ceo"));
        posts.add(new Post(3, "qa"));
    }

    public List<Post> getPosts() {
        return posts;
    }

    public Optional<Post> updatePost(int id, String postName) {
        Post existingPost = posts.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        if (existingPost == null) {
            return Optional.empty();
        }
        existingPost.setPostName(postName);
        return Optional.of(existingPost);
    }

    public Optional<Post> deletePost(int id) {
        Optional<Post> postToDelete = posts.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
        postToDelete.ifPresent(posts::remove);
        return postToDelete;
    }

    public Optional<Post> addPost(Post post) {
        if (posts.stream().anyMatch(p -> p.getId() == post.getId())) {
            return Optional.empty();
        }
        posts.add(post);
        return Optional.of(post);
    }
}