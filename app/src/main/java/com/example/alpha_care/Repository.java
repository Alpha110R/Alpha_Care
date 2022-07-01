package com.example.alpha_care;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.alpha_care.CallBacks.CallBack_getFromDB;
import com.example.alpha_care.CallBacks.CallBack_getFromRepository;
import com.example.alpha_care.Objects.Pet;
import com.example.alpha_care.Objects.User;
import com.example.alpha_care.Utils.MyFireStorage;

import java.util.List;

public class Repository {
    public static Repository me;
    private MyFireStore myFireStore;
    private MyFireStorage myFireStorage;
    private User user;
    private Pet pet;
    private CallBack_getFromRepository callBack_getFromRepository;

    public void setCallBack_getFromRepository(CallBack_getFromRepository callBack_getFromRepository) {
        this.callBack_getFromRepository = callBack_getFromRepository;
    }

    public Repository(){
        myFireStore = new MyFireStore();
        myFireStore.setCallBack_getFromDB(callBack_getFromDB);
        myFireStorage = new MyFireStorage();
    }

    public static void initRepository(){
        if(me == null)
            me = new Repository();
    }
    public static Repository getMe(){return me;}

    public void getUserByID(String userID){
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

    public void uploadImageToStorage(Activity activity, Uri imageUri, ProgressBar progressBar){
        myFireStorage.uploadFile(activity, imageUri, progressBar);
    }


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
