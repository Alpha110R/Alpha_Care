package com.example.alpha_care.Objects;

import com.example.alpha_care.Enums.EnumUserType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User {
    private List<String> myPets;
    //private Map <String, Contact> myContacts;//<phoneNumber, Contact> -> mainly for the names in the pet's profile
    private String userID, phoneNumber, userName;
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

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    @Override
    public String toString() {
        return "User:\n" +
                "UserID: '" + userID + '\n' +
                "PhoneNumber: '" + phoneNumber + '\n' +
                "User Name: " + userName +
                "\nmyPetsID: " + printPetsId() +
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
}
