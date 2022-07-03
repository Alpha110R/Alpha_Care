package com.example.alpha_care.Objects;

import com.example.alpha_care.Enums.EnumPetEventType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Pet {
    private String petImageUrl;
    private List<String> myContactsUserName;//<userID, phoneNumber (of userID)> List of userID that represent the partners. show the names of the contact with this userID, if not exist -> show from the collectionByPhoneNumber
    private Map<String,PetEvent> petEvents;//<EnumPetEventType.tostring(), PetEvent> Presents the events categories like: Walk, Food, Groom
    private String name, birthDayDate, petID;
    private int age;

    public Pet(){
        this.petID = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        petEvents = new HashMap<>();
        petEvents.put(EnumPetEventType.WALK.toString(), new PetEvent().setEnumPetEventType(EnumPetEventType.WALK));
        petEvents.put(EnumPetEventType.FOOD.toString(), new PetEvent().setEnumPetEventType(EnumPetEventType.FOOD));
        petEvents.put(EnumPetEventType.GROOM.toString(), new PetEvent().setEnumPetEventType(EnumPetEventType.GROOM));
    }

    public String getPetID(){return petID;}

    public String getPetImageUrl() {
        return petImageUrl;
    }

    public Pet setPetImageUrl(String petImageUrl) {
        this.petImageUrl = petImageUrl;
        return this;
    }

    public List<String> getMyContactsUserName() {
        return myContactsUserName;
    }

    public Pet setMyContactsUserName(List<String> myContactsUserName) {
        this.myContactsUserName = myContactsUserName;
        return this;
    }

    public Map<String,PetEvent> getPetEvents() {
        return petEvents;
    }

    public Pet setPetEvents(Map<String,PetEvent> petEvents) {
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

    public List<PetEventCard> getPetEventCardByType(EnumPetEventType type){
        if(petEvents != null) {
            for (PetEvent petEvent :
                    petEvents.values()) {
                if (petEvent.getEnumPetEventType() == type)
                    return (List) petEvent.getPetEventCardList();
            }
        }
        return null;
    }

    public void addEventCard(EnumPetEventType enumPetEventType, String contactName){
        PetEvent petEvent = petEvents.get(enumPetEventType.toString());
        petEvent.addEventCard(new PetEventCard().setEventCardCreatorContact(new Contact("").setName("ALON")));
        petEvents.put(enumPetEventType.toString(), petEvent);
    }

    @Override
    public String toString() {
        return "Pet:\n" +
                "PetID: '" + petID + '\n' +
                "Age: " + getAge() +
                "\nmyContacts: " + printMyContacts() +
                "\nEvents: " + printEvents();
    }

    private String printMyContacts(){
        if(myContactsUserName !=null) {
            StringBuilder s = new StringBuilder();
            for (String c :
                    myContactsUserName) {
                s.append("contact phone number: ").append(c).append("\n");
            }
            return s.toString();
        }
        return "null";
    }

    private String printEvents(){
        if(petEvents != null){
            StringBuilder s = new StringBuilder();
            for (PetEvent c :
                    petEvents.values()) {
                s.append("Event: ")
                 .append(c.getEnumPetEventType())
                 .append(" amount: ")
                 .append(c.getAmount())
                        .append(" events card: ")
                        .append(c.getPetEventCardList().toString())
                 .append("\n");
            }
            return s.toString();
        }
        return null;
    }
}
