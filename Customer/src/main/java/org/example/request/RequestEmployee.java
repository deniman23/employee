package org.example.request;


public class RequestEmployee {
    private int id;
    private String lastName;
    private String firstName;
    private String middleName;
    private int postId;


    public RequestEmployee(){

    }
//    public RequestEmployee(int id, String lastName, String firstName, String middleName, int postID) {
//        this.id = id;
//        this.middleName = middleName;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.postID = postID;
//    }


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

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
