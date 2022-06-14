package com.example.alpha_care;

import android.widget.ImageView;

import java.util.List;

public class Pet {
    private ImageView petImage;
    private List<Contact> myContacts;
    private List <PetEvent> petEvents;

    public Pet(){}

    public ImageView getPetImage() {
        return petImage;
    }

    public Pet setPetImage(ImageView petImage) {
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

    public List<PetEventCard> getPetEventCardByType(PetEventType type){
        for (PetEvent petEvent:
                petEvents) {
            if(petEvent.getPetEventType()==type)
                return petEvent.getEventCardList();
        }
        return null;
    }
}
