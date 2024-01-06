package com.example.ambulanceapp.models;

public class VechicleModel {
    private String id;
    private String companyId;
    private String companyName;
    private String vechicleName;
    private String vechicleType;

    public VechicleModel (){};
    public VechicleModel(String vechicleName, String vechicleType) {
        this.vechicleName = vechicleName;
        this.vechicleType = vechicleType;
    }
    public boolean ifReadyForAuyth (){
               return  (vechicleName != null && !vechicleName.equals("")) &&
                       (vechicleType != null && !vechicleType.equals(""));
    }

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

    public String getVechicleName() {
        return vechicleName;
    }

    public void setVechicleName(String vechicleName) {
        this.vechicleName = vechicleName;
    }

    public String getVechicleType() {
        return vechicleType;
    }

    public void setVechicleType(String vechicleType) {
        this.vechicleType = vechicleType;
    }
}
