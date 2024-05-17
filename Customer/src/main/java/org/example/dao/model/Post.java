package org.example.dao.model;


import jakarta.persistence.*;

import java.util.List;

// Должность
@Entity
@Table(name = "posts", schema = "public")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)
    private String postName;

    @OneToMany(mappedBy = "post")
    private List<Employee> employees;

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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
