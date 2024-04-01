package org.example.dao.model;


// Должность
public class Post {
    private final int id;
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
}
