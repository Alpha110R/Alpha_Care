package com.example.alpha_care;

import android.app.Activity;
import android.net.Uri;
import android.widget.ProgressBar;

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

    public Repository(){
        myFireStore = new MyFireStore();
        myFireStorage = new MyFireStorage();
    }

    public static void initRepository(){
        if(me == null)
            me = new Repository();
    }
    public static Repository getMe(){return me;}

    public void getUserByID(Activity activity){
        myFireStore.getUserByID(activity);
    }

    public void getPetByID(Activity activity, String petID){
        myFireStore.getPetByID(activity, petID);
    }

    public void insertToDataBase(Object obj){
        if(obj instanceof Pet)
            myFireStore.addPetToDataBaseUpdateUser((Pet)obj);
        else{
            if(obj instanceof User)
                myFireStore.addNewUser((User)obj);
        }
    }
    public void updatePetByNewEventCard(Activity activity, Pet pet){
        myFireStore.updatePetByNewEventCard(activity, pet);
    }

    public void getUserByUserName(String userName, Activity activity){
        myFireStore.getUserByUserName(userName, activity);
    }

    public void getAllUserName(Activity activity){
        myFireStore.getAllUserName(activity);
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

    public void deletePetAtCurrentUser(Activity activity, String petID){
        myFireStore.deletePetAtCurrentUser(activity, petID);
    }

}
