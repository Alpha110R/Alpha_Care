package com.example.alpha_care.CallBacks;

import com.example.alpha_care.Objects.Pet;
import com.example.alpha_care.Objects.User;

public interface CallBack_getFromRepository {
    void getUser(User returnUser);
    void getPet(Pet returnPet);
}
