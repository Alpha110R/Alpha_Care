package com.example.alpha_care;

import com.example.alpha_care.Enums.EnumPetEventType;
import com.example.alpha_care.Objects.Contact;
import com.example.alpha_care.Objects.Pet;
import com.example.alpha_care.Objects.PetEvent;
import com.example.alpha_care.Objects.PetEventCard;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DataManager {

    /*public static void data(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference cities = db.collection("cities");
        CollectionReference users = db.collection("users");
    }

    public static Pet generatePet(){
        Pet pet = new Pet();
        Map<String,PetEvent> petEvents = new HashMap<>();
        Map<String,PetEventCard> petEventCardMap = new HashMap<>();

        PetEventCard petEventCard1 = new PetEventCard();
        PetEventCard petEventCard2 = new PetEventCard();
        PetEventCard petEventCard3 = new PetEventCard();
        PetEventCard petEventCard4 = new PetEventCard();

        petEventCardMap.put(petEventCard1.getPetEventCardID(), petEventCard1.setEventCardCreatorContact(new Contact("a").setName("Alon")));
        petEventCardMap.put(petEventCard2.getPetEventCardID(), petEventCard2.setEventCardCreatorContact(new Contact("s").setName("Inbar")));
        petEventCardMap.put(petEventCard3.getPetEventCardID(), petEventCard3.setEventCardCreatorContact(new Contact("d").setName("alpha")));
        petEventCardMap.put(petEventCard4.getPetEventCardID(), petEventCard4.setEventCardCreatorContact(new Contact("f").setName("Alon")));

        PetEvent petEvent1 = new PetEvent().setPetEventCardList(petEventCardMap).setPetEventType(EnumPetEventType.FOOD);
        PetEvent petEvent2 = new PetEvent().setPetEventCardList(petEventCardMap).setPetEventType(EnumPetEventType.GROOM);

        petEvents.put(petEvent1.getPetEventType().toString(), petEvent1);
        petEvents.put(petEvent2.getPetEventType().toString(), petEvent2);
        pet.setPetEvents(petEvents);
        pet.setAge(3);
        pet.setName("Alpha");
        return pet;

    }
    /*public static User generateUser(){
        List<Pet> list = new ArrayList<>();
        Pet pet = generatePet();
        Pet pet1 = generatePet();
        list.add(pet);
        list.add(pet1);
        return new User("1").setMyPets(list).setPhoneNumber("0548192255");
    }*/
}
