package com.example.clarify;

public class UserHelper {
    // All the data required about the user
    String name, age, hospitalID, phoneNumber, gender;

    // Empty constructor for Firebase
    public UserHelper() {
    }

    // Parameterised constructor
    public UserHelper(String name, String age, String hospitalID, String phoneNumber, String gender) {
        this.name = name;
        this.age = age;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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
