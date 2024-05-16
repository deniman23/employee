package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.example.dao.model.Employee;

import java.time.LocalDate;

public class EmployeeDto {

    private int id;
    private LocalDate creationDate;
    private LocalDate modificationDate;
    private String lastName;
    private String firstName;
    private String middleName;
    private PostDto post;
//    private Integer postID;
//    private String postName;
    private boolean isTerminated;

    public EmployeeDto() {
    }

    public EmployeeDto(Employee employee) {
        this.id = employee.getId();
        this.creationDate = employee.getCreationDate();
        this.modificationDate = employee.getModificationDate();
        this.lastName = employee.getLastName();
        this.firstName = employee.getFirstName();
        this.middleName = employee.getMiddleName();
        this.isTerminated = employee.getTerminated();
//        this.postID = employee.getPostId();
//        this.post = employee.getPost();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public PostDto getPost() {
        return post;
    }

    public void setPost(PostDto post) {
        this.post = post;
    }

//    public Integer getPostID() {
//        return postID;
//    }
//
//    public void setPostID(Integer postID) {
//        this.postID = postID;
//    }
//
//    public String getPostName() {
//        return postName;
//    }
//
//    public void setPostName(String postName) {
//        this.postName = postName;
//    }

    public boolean isTerminated() {
        return isTerminated;
    }

    public void setTerminated(boolean terminated) {
        isTerminated = terminated;
    }
}