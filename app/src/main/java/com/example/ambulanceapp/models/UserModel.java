package com.example.ambulanceapp.models;

public class UserModel {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String userName;
    private String password;
    private String userType;

    public UserModel (String firstName, String lastName, String phoneNumber, String userName, String password, String userType){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    public UserModel(){}

    public void setId(String id){
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
