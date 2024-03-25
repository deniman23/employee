package org.example.entity;

import com.google.gson.Gson;

import java.util.Random;

//класс должности
public class Post {
    Random random = new Random();
    private final int id = random.nextInt();
    private String postName;


    public Post(String postName) {
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
