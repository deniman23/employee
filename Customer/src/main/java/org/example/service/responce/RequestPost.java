package org.example.service.responce;

public class RequestPost {
    private int id;
    private final String postName;

    public RequestPost(int id, String postName) {
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
}