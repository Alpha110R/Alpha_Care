package com.example.alpha_care.Objects;

import com.example.alpha_care.Enums.PetEventType;

import java.util.List;

public class PetEvent {
    private PetEventType petEventType;//WALK, FOOD, GROOM -> KEY
    private int amount;
    private List<PetEventCard> eventCardList;//The events that the contacts made in a day.

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

    private void setAmount(int amount) {
        this.amount = amount;
    }

    public void addAmount(int amount){
        this.amount += amount;
    }

    public List<PetEventCard> getEventCardList() {
        return eventCardList;
    }

    public PetEvent setEventCardList(List<PetEventCard> eventCardList) {
        this.eventCardList = eventCardList;
        setAmount(eventCardList.size());
        return this;
    }
}
