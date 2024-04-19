package org.example.service.responce;


public class RequestEmployee {
    private int id;
    private final String lastName;
    private final String firstName;
    private final String middleName;
    private final int postID;

    public RequestEmployee(int id, String lastName, String firstName, String middleName, int postID) {
        this.id = id;
        this.middleName = middleName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postID = postID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getPostID() {
        return postID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

}