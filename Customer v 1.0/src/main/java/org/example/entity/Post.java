package org.example.entity;

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

    public void setId(String id) {
        this.id = id;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }
}
