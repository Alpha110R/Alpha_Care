package com.example.alpha_care.Objects;

import com.example.alpha_care.Enums.EnumPetEventType;

import java.util.ArrayList;
import java.util.List;

public class PetEvent {
    private EnumPetEventType enumPetEventType;//WALK, FOOD, GROOM -> KEY
    private int amount;
    //private Map<String,PetEventCard> petEventCardMap;//<eventCardID, petEventCard> The events that the contacts made in a day.
    private List<PetEventCard> petEventCardList;//<eventCardID, petEventCard> The events that the contacts made in a day.

    public PetEvent (){
        amount =0;
        petEventCardList = new ArrayList<>();
    }

    public EnumPetEventType getEnumPetEventType() {
        return enumPetEventType;
    }

    public PetEvent setEnumPetEventType(EnumPetEventType enumPetEventType) {
        this.enumPetEventType = enumPetEventType;
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

    public List<PetEventCard> getPetEventCardList() {
        return petEventCardList;
    }

    public PetEvent setPetEventCardList(List<PetEventCard> petEventCardMap) {
        this.petEventCardList = petEventCardMap;
        setAmount(petEventCardMap.size());
        return this;
    }

    public void addEventCard(PetEventCard petEventCard){
        petEventCardList.add(petEventCard);
    }
}
