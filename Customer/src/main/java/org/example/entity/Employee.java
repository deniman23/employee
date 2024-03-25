package org.example.entity;

import com.google.gson.Gson;

import java.time.LocalDate;
import java.util.UUID;

//класс сотрудника
public class Employee {
    private final String id = UUID.randomUUID().toString();
    private final LocalDate creationDate;
    private LocalDate modificationDate;
    private String lastName;
    private String firstName;
    private String middleName;
    private int positionId;
    private Post position;
    private boolean isTerminated;

    public Employee(String lastName, String firstName, String middleName, int positionId) {
        this.creationDate = LocalDate.now();
        this.modificationDate = LocalDate.now();
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.positionId = positionId;
        this.isTerminated = false;
    }

    public Employee(Employee employee) {
        creationDate = employee.getCreationDate();
        modificationDate = employee.getModificationDate();
        lastName = employee.getLastName();
        firstName = employee.getFirstName();
        middleName = employee.getMiddleName();
        positionId = employee.getPositionId();
    }


    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public int getPositionId() {
        return positionId;
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
    public boolean getTerminated(){return isTerminated;}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getId() {
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
