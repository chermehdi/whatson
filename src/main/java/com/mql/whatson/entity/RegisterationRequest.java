package com.mql.whatson.entity;

/**
 * @author Mehdi Maick
 */
public class RegisterationRequest {

    private int type = -1;
    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String picture;

    private String userName;

    public RegisterationRequest(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public RegisterationRequest(String userName, String firstName, String lastName, String email, String password, String picture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.picture = picture;
    }
    public RegisterationRequest(String firstName, String lastName, String email, String picture, int type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.picture = picture;
        this.type = type;
    }
    public RegisterationRequest(String username, String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.userName = userName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
