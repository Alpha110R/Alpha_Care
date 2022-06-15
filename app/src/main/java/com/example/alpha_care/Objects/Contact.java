package com.example.alpha_care.Objects;

public class Contact {
    private String firstName,
                   lastName,
                    UID;//equals to his user ID
    public Contact (String UID){
        this.UID = UID;
    }

    public String getUID(){return UID;}

    public String getFirstName() {
        return firstName;
    }

    public Contact setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Contact setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

}
