package org.example.entity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

//класс должности
public class Post {
    private String id;
    private String postName;


    public Post(String id, String postName) {
        this.id = id;
        this.postName = postName;
    }


    public String getId() {
        return id;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }


    // Convert Post details to JSON
    public String toJson() {
        return new Gson().toJson(this);
    }
}
