package com.example.alpha_care;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.alpha_care.Activities.AddPetToUserActivity;
import com.example.alpha_care.Activities.LogInActivity;
import com.example.alpha_care.Activities.PetProfileActivity;
import com.example.alpha_care.Activities.PetsListActivity;
import com.example.alpha_care.CallBacks.CallBack_getFromDB;
import com.example.alpha_care.Objects.Pet;
import com.example.alpha_care.Objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyFireStore {
    private CollectionReference usersByID, usersByPhoneNumber, petsByID, petsByUserID;
    private User returnUser;
    private Pet pet;
    private User user;
    private CallBack_getFromDB callBack_getFromDB;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();



    public MyFireStore(){
        usersByID = db.collection("usersByID");//All the users in the application
        usersByPhoneNumber = db.collection("usersByPhoneNumber");//All the users in the application
        petsByID = db.collection("petsByID");//All the pets in the application
        petsByUserID = db.collection("petsByUserID");

    }
    public void setCallBack_getFromDB(CallBack_getFromDB callBack_getFromDB){
        this.callBack_getFromDB = callBack_getFromDB;
    }


    private String getCurrentUserID(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        String currentUserID = "";
        if (currentUser != null) {
            currentUserID = currentUser.getUid();
        }

        return currentUserID;
    }

    public void updateUserToDataBase(User user){
        usersByID.document(user.getUID()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("tagg", "addUserToDataBase succeed");
            }
        });
    }

    public void addNewUser(User user){
        usersByID.document(user.getUID()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("tagg", "addUserToDataBase succeed");
                addNewPhoneNumberUser(user.getPhoneNumber());
            }
        });
    }

    public void getUserByID(Activity activity){
        DocumentReference docRef = usersByID.document(getCurrentUserID());
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        //Log.d("tagg", "getUserByID DocumentSnapshot data: " + documentSnapshot.getData());
                        returnUser = documentSnapshot.toObject(User.class);
                        if(activity instanceof LogInActivity){

                            callBack_getFromDB.getUser(returnUser);
                        }
                        if(activity instanceof PetsListActivity){
                            ((PetsListActivity)activity).setCurrentUser(returnUser);
                        }
                    }
                });
    }

    public void getPetByID(Activity activity, String petID){
        petsByID.document(petID)
                 .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        //Log.d("tagg", "getPetByID DocumentSnapshot data: " + documentSnapshot.getData());
                        pet = documentSnapshot.toObject(Pet.class);
                        if(activity instanceof PetProfileActivity){
                            ((PetProfileActivity) activity).initializePet(pet);
                        }
                    }
                });
    }

    public void getUserByPhoneNumber(String phoneNumber, Activity activity){
        usersByPhoneNumber.document(phoneNumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("tagg", "getUserByPhoneNumber DocumentSnapshot data: " + document.getData());
                                if(activity instanceof PetsListActivity)
                                    ((PetsListActivity)activity).addContactPhoneNumberToUserContacts(phoneNumber);
                            } else {
                                //Log.d("tagg", "No such document getUserByPhoneNumber: phone:" + phoneNumber);
                            }
                        } else {
                            Log.d("tagg", "getUserByPhoneNumber get FAILED with ", task.getException());
                        }
                    }
                });
    }

    public void addNewPhoneNumberUser(String phoneNumber){
        usersByPhoneNumber.document(phoneNumber)
                            .set(getCurrentUserID())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            });
    }

    /**
     * Add by pet ID pet to all the pets in the application
     * @param pet
     */
    public void addPetToDataBaseUpdateUser(Pet pet){
        petsByID.document(pet.getPetID()).set(pet).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                usersByID.document(getCurrentUserID())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot document) {
                                user = document.toObject(User.class);
                                assert user != null;
                                user.addPetIDToUserPetList(pet.getPetID());
                                updateUserToDataBase(user);
                            }
                        });
                Log.d("tagg", "addPetToDataBase succeed");
            }
        });
    }


    public void upsertPetToDataBase(Pet pet){
        petsByID.document(pet.getPetID()).set(pet).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("tagg", "updatePetToDataBase succeed");

            }
        });
    }

    public void updatePetByNewEventCard(Activity activity, Pet pet){
        petsByID.document(pet.getPetID()).set(pet).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                if(activity instanceof PetProfileActivity){
                    ((PetProfileActivity) activity).notifyDataChangeRecycleView();
                }
            }
        });
    }

    /**
     * Adding pet to user
     * @param pet
     */
    public void upsertPetToUserDataBase(Activity activity, Pet pet){
        usersByID.document(getCurrentUserID())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot document) {

                if(activity instanceof PetsListActivity){
                    ((PetsListActivity) activity).addPetToList(pet);
                }
                if(activity instanceof AddPetToUserActivity){
                    petsByID.document(pet.getPetID()).set(pet).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("tagg", "addPetToDataBase succeed");
                        }
                    });
                    ((AddPetToUserActivity) activity).moveToPageWithBundle(PetsListActivity.class);

                }

                Log.d("tagg", "upsertPetToUserDataBase: " + document.getData());

            }
        });
    }

    /**
     * Convert the petID to Pet from the DB and add it to the list in the recycleView to show it
     * @param petID
     * @param activity
     */
    public void convertPetIDToPetAndAddPetToListOfPetsForTheCurrentUser(String petID, Activity activity){
        DocumentReference docRef = petsByID.document(petID);
        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                //Log.d("tagg", "getPetByID DocumentSnapshot data: " + document.getData());
                                pet = document.toObject(Pet.class);
                                if(activity instanceof PetsListActivity){
                                    ((PetsListActivity) activity).addPetToList(pet);
                                }
                            } else {
                                Log.d("tagg", "No such document getPetByID: petID:" + petID);
                            }
                        } else {
                            Log.d("tagg", "getPetByID get FAILED with ", task.getException());
                        }
                    }
                });
    }

    /**
     * Deleting petID from the pets list in the current user.
     * Not deleting the pet from the DB
     * @param activity
     * @param petID
     */
    public void deletePetAtCurrentUser(Activity activity, String petID){
        usersByID.document(getCurrentUserID())
                 .get()
                 .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                         if (task.isSuccessful()) {
                             DocumentSnapshot document = task.getResult();
                             if (document.exists()) {
                                 user = document.toObject(User.class);
                                 List <String> petList = user.getMyPets();
                                 if(petList.contains(petID)){
                                     petList.remove(petID);
                                 }
                                 user.setMyPets(petList);
                                 updateUserToDataBase(user);
                                 if(activity instanceof PetsListActivity){
                                     ((PetsListActivity)activity).deletePetSucceed();
                                 }
                             } else {
                                 Log.d("tagg", "No such document deletePetAtCurrentUser");
                             }
                         } else {
                             Log.d("tagg", "deletePetAtCurrentUser get FAILED with ", task.getException());
                         }
                     }
                 });
    }

}
