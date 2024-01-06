package com.example.ambulanceapp.models;

public class AmbulanceCompanyModel {
    private String id;
    private String companyName;
    private String description;
    private String phoneNumber;
    private String longtitude;
    private String latitude;
    private String address;

    public AmbulanceCompanyModel (String companyName, String description, String phoneNumber, String longtitude, String latitude, String address) {
        this.companyName = companyName;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.address = address;
    }
    public AmbulanceCompanyModel(){}

    public boolean ifInputsAreValidForAuth (){
        boolean ready =  (companyName != null  && !companyName.equals("")) &&
                (description != null && !description.equals("")) &&
                (phoneNumber != null && !phoneNumber.equals("")) &&
                (longtitude != null && !longtitude.equals("")) &&
                (latitude != null && !latitude.equals("")) &&
                (address != null && !address.equals(""));

        return ready;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
