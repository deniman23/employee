package org.example.dao.model;

import jakarta.persistence.*;
import org.example.services.response.ResponseEmployee;

import java.time.LocalDate;

// Сотрудник
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDate creationDate = LocalDate.now();

    @Column(name = "modification_date")
    private LocalDate modificationDate = LocalDate.now();

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @Column(name = "terminated", nullable = false)
    private boolean terminated;

    public Employee(int id, String lastName, String firstName, String middleName, int postID) {
        this.id = id;
        this.creationDate = LocalDate.now();
        this.modificationDate = LocalDate.now();
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.terminated = false;
    }

    public Employee(ResponseEmployee responseEmployee) {
        this.id = responseEmployee.getId();
        this.creationDate = LocalDate.now();
        this.modificationDate = LocalDate.now();
        this.lastName = responseEmployee.getLastName();
        this.firstName = responseEmployee.getFirstName();
        this.middleName = responseEmployee.getMiddleName();
        this.terminated = false;
    }

    public Employee() {

    }


    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }

    public boolean getTerminated() {
        return terminated;
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

    public void setId(int id) {
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

    public LocalDate getCreationDate() {
        return creationDate;
    }


}
