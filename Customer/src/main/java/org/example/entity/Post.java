package org.example.entity;

import com.google.gson.Gson;

//класс должности
public class Post {
    private int id;
    private String postName;


    public Post(int id, String postName) {
        this.id = id;
        this.postName = postName;
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


    // Convert Post details to JSON
    public String toJson() {
        return new Gson().toJson(this);
    }
}
