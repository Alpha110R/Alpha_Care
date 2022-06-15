package com.example.alpha_care.Objects;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.alpha_care.Enums.PetEventType;

import java.util.List;
import java.util.UUID;

public class Pet {
    private AppCompatImageView petImage;
    private List<Contact> myContacts;//The contacts that share this pet
    private List <PetEvent> petEvents;//Presents the events categories like: Walk, Food, Groom
    private String name, birthDayDate, UID;
    private int age;

    public Pet(){
        this.UID = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    public String getUID(){return UID;}

    public AppCompatImageView getPetImage() {
        return petImage;
    }

    public Pet setPetImage(AppCompatImageView petImage) {
        this.petImage = petImage;
        return this;
    }

    public List<Contact> getMyContacts() {
        return myContacts;
    }

    public Pet setMyContacts(List<Contact> myContacts) {
        this.myContacts = myContacts;
        return this;
    }

    public List<PetEvent> getPetEvents() {
        return petEvents;
    }

    public Pet setPetEvents(List<PetEvent> petEvents) {
        this.petEvents = petEvents;
        return this;
    }

    public String getName() {
        return name;
    }

    public Pet setName(String name) {
        this.name = name;
        return this;
    }

    public String getBirthDayDate() {
        return birthDayDate;
    }

    public Pet setBirthDayDate(String birthDayDate) {
        this.birthDayDate = birthDayDate;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Pet setAge(int age) {
        this.age = age;
        return this;
    }

    public List<PetEventCard> getPetEventCardByType(PetEventType type){
        for (PetEvent petEvent:
                petEvents) {
            if(petEvent.getPetEventType()==type)
                return petEvent.getEventCardList();
        }
        return null;
    }
}
