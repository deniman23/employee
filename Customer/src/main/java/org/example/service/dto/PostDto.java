package org.example.service.dto;

import org.example.dao.model.Post;

public class PostDto {

    private final int id;
    private String postName;

    public PostDto(int id, String postName){
        this.id = id;
        this.postName = postName;
    }

    public PostDto(Post post){
        this.id = post.getId();
        this.postName = post.getPostName();
    }

    public int getId() {
        return id;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }
}
