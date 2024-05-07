package org.example.dao.model;


import jakarta.persistence.*;

// Должность
@Entity
@Table(name = "posts", schema = "public")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)
    private String postName;

    public Post(int id, String postName) {
        this.id = id;
        this.postName = postName;
    }

    public Post() {

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
