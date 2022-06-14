package com.example.alpha_care;

import java.util.List;

public class User {
    private List<Pet> myPets;

    public User(){}

    public List<Pet> getMyPets() {
        return myPets;
    }

    public User setMyPets(List<Pet> myPets) {
        this.myPets = myPets;
        return this;
    }
}
