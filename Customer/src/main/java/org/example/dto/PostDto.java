package org.example.dto;

import org.example.dao.model.Post;

public class PostDto {

    private Integer id;
    private String postName;

    public PostDto() {
    }


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
