package com.example.alpha_care;

import android.app.Activity;
import android.util.Log;

import com.example.alpha_care.CallBacks.CallBack_getFromDB;
import com.example.alpha_care.CallBacks.CallBack_getFromRepository;
import com.example.alpha_care.Objects.Pet;
import com.example.alpha_care.Objects.User;

import java.util.List;

public class Repository {
    public static Repository me;
    private MyFireStore myFireStore;
    private User user;
    private Pet pet;
    private CallBack_getFromRepository callBack_getFromRepository;

    public void setCallBack_getFromRepository(CallBack_getFromRepository callBack_getFromRepository) {
        this.callBack_getFromRepository = callBack_getFromRepository;
    }

    public Repository(){
        myFireStore = new MyFireStore();
        myFireStore.setCallBack_getFromDB(callBack_getFromDB);
    }

    public static void initRepository(){
        if(me == null)
            me = new Repository();
    }
    public static Repository getMe(){return me;}

    public void getUserByID(String userID){
        Log.d("tagg", "userID repository: " + userID);
        myFireStore.getUserByID(userID);
    }

    public void getPetByID(Activity activity, String petID){
        myFireStore.getPetByID(activity, petID);
    }

    public void getUserByPhoneNumber(String phoneNumber) {
        myFireStore.getUserByPhoneNumber(phoneNumber);
    }

    public void insertToDataBase(Object obj){
        if(obj instanceof Pet)
            myFireStore.addPetToDataBaseUpdateUser((Pet)obj);
        else{
            if(obj instanceof User)
                myFireStore.upsertUserToDataBase((User)obj);
        }
    }

    public void updateToDataBase(Object obj){
        if(obj instanceof Pet)
            myFireStore.upsertPetToDataBase((Pet)obj);
        else{
            if(obj instanceof User)
                myFireStore.upsertUserToDataBase((User)obj);
        }
    }

    public void updatePetByNewEventCard(Activity activity, Pet pet){
        myFireStore.updatePetByNewEventCard(activity, pet);
    }

    /**
     * Func to display the pets of the current user.
     * @param petsListID
     * @param activity
     */
    public void getUserPets(List<String> petsListID, Activity activity){
        for (String petID :
                petsListID) {
            myFireStore.convertPetIDToPetAndAddPetToListOfPetsForTheCurrentUser(petID, activity);
        }
    }

    /**
     * Add PetID to the user petsID list and show the update in the recycleView
     * @param activity
     * @param pet
     */
    /*public void upsertPetToUserList(Activity activity, Pet pet){
        myFireStore.upsertPetToUserDataBase(activity, pet);
    }*/



    private CallBack_getFromDB callBack_getFromDB = new CallBack_getFromDB() {
        @Override
        public void getPet(Pet returnPet) {
            callBack_getFromRepository.getPet(returnPet);
        }

        @Override
        public void getUser(User returnUser) {
            callBack_getFromRepository.getUser(returnUser);
        }
    };

}
