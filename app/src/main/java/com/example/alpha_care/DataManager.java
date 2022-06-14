package com.example.alpha_care;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataManager {

    public static Pet generatePet(){
        Pet pet = new Pet();
        List<PetEvent> events = new ArrayList<>();
        List<PetEventCard> eventCards = new ArrayList<>();

        eventCards.add(new PetEventCard().setDateExecution(new Date()).
                                                setMadeEventContact(new Contact().
                                                                    setFirstName("Alon").
                                                                    setLastName("Ronder")));
        eventCards.add(new PetEventCard().setDateExecution(new Date()).
                setMadeEventContact(new Contact().
                        setFirstName("Alpha").
                        setLastName("Ron")));
        eventCards.add(new PetEventCard().setDateExecution(new Date()).
                setMadeEventContact(new Contact().
                        setFirstName("Inbar").
                        setLastName("Klang")));
        eventCards.add(new PetEventCard().setDateExecution(new Date()).
                setMadeEventContact(new Contact().
                        setFirstName("Alonnnn").
                        setLastName("Ronder")));
        events.add(new PetEvent().setPetEventType(PetEventType.FOOD).setEventCardList(eventCards));
        pet.setPetEvents(events);
        return pet;

    }
}
