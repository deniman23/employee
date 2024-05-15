package org.example.services.dto;

import org.example.dao.model.Post;

public class PostDto {

    private final int id;
    private String postName;


    public PostDto(Post post){
        this.id = post.getId();
        this.postName = post.getPostName();
    }

    public Integer getId() {
        return id;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }
}
