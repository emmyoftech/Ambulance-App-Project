package com.example.ambulanceapp.models;

public class LandmarkModel {
    private String id;
    private String companyId;
    private String companyName;
    private String landmark;

    public LandmarkModel(String landmark) {
        this.landmark = landmark;
    }
    public LandmarkModel () {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }
}
