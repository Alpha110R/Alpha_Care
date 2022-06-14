package com.example.alpha_care;

import java.util.Date;

public class PetEventCard {
    private Contact madeEventContact;
    private Date dateExecution;

    public PetEventCard(){}

    public Contact getMadeEventContact() {
        return madeEventContact;
    }

    public PetEventCard setMadeEventContact(Contact madeEventContact) {
        this.madeEventContact = madeEventContact;
        return this;
    }

    public Date getDateExecution() {
        return dateExecution;
    }

    public PetEventCard setDateExecution(Date dateExecution) {
        this.dateExecution = dateExecution;
        return this;
    }
}
