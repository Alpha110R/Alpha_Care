package com.example.alpha_care.Objects;

public class Contact {
    private String name,
                    contactID,//equals to his user ID KEY
                   phoneNumber;
    public Contact (){}
    public Contact (String contactID){
        this.contactID = contactID;
    }

    public String getContactID(){return contactID;}

    public String getName() {
        return name;
    }

    public Contact setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Contact setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
