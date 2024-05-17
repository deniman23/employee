package org.example.request;

public class RequestPost {
    private Integer id;
    private final String postName;

    public RequestPost(Integer id, String postName) {
        this.id = id;
        this.postName = postName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getPostName() {
        return postName;
    }
}