package org.example.dao.model;

import org.example.service.dto.PostDto;

import java.time.LocalDate;

// Сотрудник
public class Employee {

    private final int id;
    private final LocalDate creationDate;
    private LocalDate modificationDate;
    private String lastName;
    private String firstName;
    private String middleName;
    private PostDto post;
    private int postID;
    private boolean isTerminated;

    public Employee(int id, String lastName, String firstName, String middleName, int postID) {
        this.id = id;
        this.creationDate = LocalDate.now();
        this.modificationDate = LocalDate.now();
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.postID = postID;
        this.isTerminated = false;
    }

    public PostDto getPost() {
        return post;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getPostID() {
        return postID;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }

    public void setTerminated(boolean terminated) {
        isTerminated = terminated;
    }

    public boolean getTerminated() {
        return isTerminated;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }


}
