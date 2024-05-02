//package org.example.dao.repository;
//
//import org.example.dao.model.Post;
//import org.example.service.dto.PostDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Component
//public class PostDataService {
//    private final PostRepository postRepository;
//
//    @Autowired
//    public PostDataService(PostRepository postRepository) {
//        this.postRepository = postRepository;
//    }
//
//    public Post createPost(Post post) {
//        return postRepository.save(post);
//    }
//
//    public List<PostDto> getAllPosts() {
//        return postRepository.findAll().stream()
//                .map(PostDto::new)
//                .collect(Collectors.toList());
//    }
//
//    public Optional<Post> findById(int id) {
//        return postRepository.findById(id);
//    }
//
//    public Optional<Post> updatePost(int id, String postName) {
//        Optional<Post> existingPost = findById(id);
//        if (existingPost.isPresent()) {
//            Post post = existingPost.get();
//            post.setPostName(postName);
//            postRepository.save(post);
//            return Optional.of(post);
//        }
//        return Optional.empty();
//    }
//
//    public Optional<Post> deletePost(int id) {
//        Optional<Post> postToDelete = findById(id);
//        postToDelete.ifPresent(postRepository::delete);
//        return postToDelete;
//    }
//
//    public List<PostDto> positionToDto() {
//        return postRepository.findAll().stream()
//                .map(PostDto::new)
//                .collect(Collectors.toList());
//    }
//}