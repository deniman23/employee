package org.example.service.dto;

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
    @JsonIgnore
    private int postID;
    private boolean isTerminated;

    public EmployeeDto(int id, LocalDate creationDate, LocalDate modificationDate, String lastName, String firstName, String middleName, PostDto post, int postID, boolean isTerminated) {
        this.id = id;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.post = post;
        this.postID = postID;
        this.isTerminated = isTerminated;
    }

    public EmployeeDto(Employee employee){
        this.id = employee.getId();
        this.creationDate = employee.getCreationDate();
        this.modificationDate = employee.getModificationDate();
        this.lastName = employee.getLastName();
        this.firstName = employee.getFirstName();
        this.middleName = employee.getMiddleName();
        this.post = employee.getPost();
        this.postID = employee.getPostID();
        this.isTerminated = employee.getTerminated();    }


    public LocalDate getModificationDate() {
        return modificationDate;
    }


    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public PostDto getPost() {
        return post;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setPost(PostDto post) {
        this.post = post;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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

    public void setTerminated(boolean terminated) {
        isTerminated = terminated;
    }

    public boolean isTerminated() {
        return isTerminated;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getPostID() {
        return postID;
    }

    public int getId() {
        return id;
    }
}
