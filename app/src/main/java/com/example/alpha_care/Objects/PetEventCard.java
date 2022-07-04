package com.example.alpha_care.Objects;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

public class PetEventCard {
    private String eventCardCreatorContact;
    private String dateExecution, petEventCardID;

    public PetEventCard(){
        this.dateExecution = new SimpleDateFormat("HH:mm", Locale.US).format(System.currentTimeMillis());
        this.petEventCardID = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    public String getEventCardCreatorContact() {
        return eventCardCreatorContact;
    }

    public PetEventCard setEventCardCreatorContact(String eventCardCreatorContact) {
        this.eventCardCreatorContact = eventCardCreatorContact;
        return this;
    }

    public String getDateExecution() {
        return dateExecution;
    }

    public String getPetEventCardID() {
        return petEventCardID;
    }
}
