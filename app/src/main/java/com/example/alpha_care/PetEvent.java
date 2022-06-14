package com.example.alpha_care;

import java.util.List;

enum PetEventType {
    WALK,
    FOOD,
    GROOM
}

public class PetEvent {
    private PetEventType petEventType;
    private int amount;
    private List<PetEventCard> eventCardList;

    public PetEvent (){}

    public PetEventType getPetEventType() {
        return petEventType;
    }

    public PetEvent setPetEventType(PetEventType petEventType) {
        this.petEventType = petEventType;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public PetEvent setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public List<PetEventCard> getEventCardList() {
        return eventCardList;
    }

    public PetEvent setEventCardList(List<PetEventCard> eventCardList) {
        this.eventCardList = eventCardList;
        return this;
    }
}
