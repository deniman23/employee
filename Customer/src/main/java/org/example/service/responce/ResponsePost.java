package org.example.service.responce;

public class ResponsePost {
    private final int id;
    private final String postName;

    public ResponsePost(int id, String postName) {
        this.id = id;
        this.postName = postName;
    }

    public int getId() {
        return id;
    }

    public String getPostName() {
        return postName;
    }
}