package org.example.dao.model;


// Должность
public class Post {
    private int id;
    private String postName;

    public Post(int id, String postName) {
        this.id = id;
        this.postName = postName;
    }

    public void setId(int id) {
        this.id = id;
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
