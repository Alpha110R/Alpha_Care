package com.example.alpha_care.Objects;

import com.example.alpha_care.Enums.UserType;

import java.util.List;
import java.util.Map;

public class User {
    private Map<String, Pet> myPets;
    private String UID, phoneNumber;
    private UserType userType;

    public User(String UID){
        this.UID = UID;
    }

    public Map<String, Pet> getMyPets() {
        return myPets;
    }

    public User setMyPets(Map<String, Pet> myPets) {
        this.myPets = myPets;
        return this;
    }

    public String getUID(){
        return UID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
