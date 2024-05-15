package org.example.services.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.example.dao.model.Employee;

import java.time.LocalDate;

public class EmployeeDto {

    private int id;
    private final LocalDate creationDate;
    private final LocalDate modificationDate;
    private final String lastName;
    private final String firstName;
    private final String middleName;
    @JsonIgnore
    private PostDto post;
    private final int postID;
    private String postName;
    private final boolean isTerminated;


    public EmployeeDto(Employee employee) {
        this.id = employee.getId();
        this.creationDate = employee.getCreationDate();
        this.modificationDate = employee.getModificationDate();
        this.lastName = employee.getLastName();
        this.firstName = employee.getFirstName();
        this.middleName = employee.getMiddleName();
        this.isTerminated = employee.getTerminated();
        this.postID = employee.getPost().getId();
        this.postName = employee.getPost().getPostName();
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public PostDto getPost() {
        return post;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPost(PostDto post) {
        this.post = post;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isTerminated() {
        return isTerminated;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public int getPostID() {
        return postID;
    }
}