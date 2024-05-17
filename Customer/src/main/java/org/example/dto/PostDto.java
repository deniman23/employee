package org.example.dto;

import org.example.dao.model.Post;

public class PostDto {

    private Integer id;
    private String postName;

    public PostDto() {
    }

//    public PostDto(Post post){
//        this.id = post.getId();
//        this.postName = post.getPostName();
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }
}
