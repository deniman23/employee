package org.example.entity;

import com.google.gson.Gson;

import java.time.LocalDate;

//класс сотрудника
public class Employee {
    private String id;
    private LocalDate creationDate;
    private LocalDate modificationDate;
    private String lastName;
    private String firstName;
    private String middleName;
    private Post position;
    private boolean isTerminated;

    public Employee(String id, String lastName, String firstName, String middleName, Post position) {
        this.id = id;
        this.creationDate = LocalDate.now();
        this.modificationDate = LocalDate.now();
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.position = position;
        this.isTerminated = false;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }

    public void setTerminated(boolean terminated) {
        isTerminated = terminated;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Post getPosition() {
        return position;
    }

    public void setPosition(Post position) {
        this.position = position;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }


    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
    public String toJson() {
        return new Gson().toJson(this);
    }
}
