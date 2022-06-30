package com.example.alpha_care.Objects;

import com.example.alpha_care.Enums.EnumUserType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User {
    private List<String> myPets;
    private Map <String, Contact> myContacts;//<phoneNumber, Contact> -> mainly for the names in the pet's profile
    private String userID, phoneNumber;
    private EnumUserType enumUserType;
    public User(){
        this.myPets = new ArrayList<>();
    }

    public User(String userID){
        this.myPets = new ArrayList<>();
        this.userID = userID;
    }

    public List<String> getMyPets() {
        return this.myPets;
    }

    public User setMyPets(List<String> myPetsUID) {
        this.myPets = myPetsUID;
        return this;
    }

    public void addPetIDToUserPetList(String petID){
        this.myPets.add(petID);
    }

    public String getUID(){
        return userID;
    }

    public void setUID(String userID){this.userID = userID;}

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public String toString() {
        return "User:\n" +
                "UserID: '" + userID + '\n' +
                "PhoneNumber: '" + phoneNumber + '\n' +
                "myPetsID: " + printPetsId() +
                "\nmyContacts: " + printMyContacts() +
                "\nenumUserType: " + enumUserType ;
    }
    private String printPetsId(){
        if(myPets !=null) {
            StringBuilder s = new StringBuilder();
            for (String m :
                    myPets) {
                s.append(m).append("\n");
            }
            return s.toString();
        }
        return "null";
    }
    private String printMyContacts(){
        if(myContacts !=null) {
            StringBuilder s = new StringBuilder();
            for (Contact c :
                    myContacts.values()) {
                s.append("contact name: ").append(c.getName()).append(" contact phone: ").append(c.getPhoneNumber()).append("\n");
            }
            return s.toString();
        }
        return "null";
    }
}
