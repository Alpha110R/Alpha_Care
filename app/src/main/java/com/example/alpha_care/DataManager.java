package com.example.alpha_care;

import com.example.alpha_care.Enums.PetEventType;
import com.example.alpha_care.Objects.Contact;
import com.example.alpha_care.Objects.Pet;
import com.example.alpha_care.Objects.PetEvent;
import com.example.alpha_care.Objects.PetEventCard;
import com.example.alpha_care.Objects.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {

    public static Pet generatePet(){
        Pet pet = new Pet();
        List<PetEvent> events = new ArrayList<>();
        List<PetEventCard> eventCards = new ArrayList<>();

        eventCards.add(new PetEventCard().
                setEventCardCreatorContact(new Contact("").
                                                                    setFirstName("Alon").
                                                                    setLastName("Ronder")));
        eventCards.add(new PetEventCard().
                setEventCardCreatorContact(new Contact("").
                        setFirstName("אלון").
                        setLastName("רונדר")));
        eventCards.add(new PetEventCard().
                setEventCardCreatorContact(new Contact("").
                        setFirstName("Inbar").
                        setLastName("Klang")));
        eventCards.add(new PetEventCard().
                setEventCardCreatorContact(new Contact("").
                        setFirstName("Alonnnn").
                        setLastName("Ronder")));
        events.add(new PetEvent().setPetEventType(PetEventType.FOOD).setEventCardList(eventCards));
        pet.setPetEvents(events);
        pet.setAge(3);
        pet.setName("Alpha");
        return pet;

    }
    public static User generateUser(){
        Map<String,Pet> map = new HashMap<>();
        map.put("a",generatePet());
        return new User("").setMyPets(map);
    }
}
