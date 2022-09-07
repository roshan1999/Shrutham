package com.example.klarify;

public class UserHelper {
    // All the data required about the user
    String name, dob, hospitalID, phoneNumber, gender;

    // Empty constructor for Firebase
    public UserHelper() {
    }

    // Parameterised constructor
    public UserHelper(String name, String dob, String hospitalID, String phoneNumber, String gender) {
        this.name = name;
        this.dob = dob;
        this.hospitalID = hospitalID;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    // Setters and getters

    public String getName() {
        return name;
    }

        public void setName(String name) {
            this.name = name;
        }

    public String getDOB() {
        return dob;
    }

    public void setDOB(String dob) {
        this.dob = dob;
    }

    public String getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
