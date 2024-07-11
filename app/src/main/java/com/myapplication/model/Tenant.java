package com.myapplication.model;

public class Tenant {

    private int TnID;
    private String TnFirstName;
    private String TnLastName;
    private String TnEmail;
    private String TnPhone;
    private String TnDNI;
    private String TnStatus;
    private String TnType;
    private String TnGender;

    public Tenant(){

    }

    public Tenant(int TnID, String TnFirstName, String TnLastName, String TnEmail, String TnPhone, String TnDNI, String TnStatus, String TnType, String TnGender) {
        this.TnID = TnID;
        this.TnFirstName = TnFirstName;
        this.TnLastName = TnLastName;
        this.TnEmail = TnEmail;
        this.TnPhone = TnPhone;
        this.TnDNI = TnDNI;
        this.TnStatus = TnStatus;
        this.TnType = TnType;
        this.TnGender = TnGender;
    }

    public int getTnID() {
        return TnID;
    }

    public void setTnID(int TnID) {
        this.TnID = TnID;
    }

    public String getTnFirstName() {
        return TnFirstName;
    }

    public void setTnFirstName(String TnFirstName) {
        this.TnFirstName = TnFirstName;
    }

    public String getTnLastName() {
        return TnLastName;
    }

    public void setTnLastName(String TnLastName) {
        this.TnLastName = TnLastName;
    }

    public String getTnEmail() {
        return TnEmail;
    }

    public void setTnEmail(String TnEmail) {
        this.TnEmail = TnEmail;
    }

    public String getTnPhone() {
        return TnPhone;
    }

    public void setTnPhone(String TnPhone) {
        this.TnPhone = TnPhone;
    }

    public String getTnDNI() {
        return TnDNI;
    }

    public void setTnDNI(String TnDNI) {
        this.TnDNI = TnDNI;
    }

    public String getTnStatus() {
        return TnStatus;
    }

    public void setTnStatus(String TnStatus) {
        this.TnStatus = TnStatus;
    }

    public String getTnType() {
        return TnType;
    }

    public void setTnType(String TnType) {
        this.TnType = TnType;
    }

    public String getTnGender() {
        return TnGender;
    }

    public void setTnGender(String TnGender) {
        this.TnGender = TnGender;
    }

}
