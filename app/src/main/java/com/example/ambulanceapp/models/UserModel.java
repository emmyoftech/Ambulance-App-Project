package com.example.ambulanceapp.models;

public class UserModel {
    private int id;
    private String userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String userName;
    private String password;
    private String userType;

    public UserModel (int id, String userId, String firstName, String lastName, String phoneNumber, String userName, String password, String userType){
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    public UserModel (String firstName, String lastName, String phoneNumber, String userName, String password, String userType){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
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
}
