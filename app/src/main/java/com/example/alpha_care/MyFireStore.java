package com.example.alpha_care;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alpha_care.Activities.AddPetToUserActivity;
import com.example.alpha_care.Activities.PetProfileActivity;
import com.example.alpha_care.Activities.PetsListActivity;
import com.example.alpha_care.CallBacks.CallBack_getFromDB;
import com.example.alpha_care.Enums.EnumFinals;
import com.example.alpha_care.Fragments.HomePageFragment;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyFireStore {
    private CollectionReference usersByID, usersByPhoneNumber, petsByID, petsByUserID;
    private User returnUser;
    private Pet pet;
    private CallBack_getFromDB callBack_getFromDB;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();



    public MyFireStore(){
        usersByID = db.collection("usersByID");//All the users in the application
        usersByPhoneNumber = db.collection("usersByPhoneNumber");//All the users in the application
        petsByID = db.collection("petsByID");//All the pets in the application
        petsByUserID = db.collection("petsByUserID");

        final DocumentReference docRef = petsByID.document("petsByID");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("tagg", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d("tagg", "Current data: " + snapshot.getData());
                } else {
                    Log.d("tagg", "Current data: null");
                }
            }
        });

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

    public void upsertUserToDataBase(User user){
        usersByID.document(user.getUID()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("tagg", "addUserToDataBase succeed");
            }
        });
    }

    public void getUserByID(String userID){
        DocumentReference docRef = usersByID.document(userID);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d("tagg", "getUserByID DocumentSnapshot data: " + documentSnapshot.getData());
                        returnUser = documentSnapshot.toObject(User.class);
                        callBack_getFromDB.getUser(returnUser);
                    }
                });
    }

    public void getPetByID(Activity activity, String petID){
        petsByID.document(petID)
                 .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d("tagg", "getPetByID DocumentSnapshot data: " + documentSnapshot.getData());
                        pet = documentSnapshot.toObject(Pet.class);
                        if(activity instanceof PetProfileActivity){
                            ((PetProfileActivity) activity).initializePet(pet);
                        }
                    }
                });
    }

    public void getUserByPhoneNumber(String phoneNumber){
        DocumentReference docRef = usersByPhoneNumber.document(phoneNumber);
        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("tagg", "getUserByPhoneNumber DocumentSnapshot data: " + document.getData());
                                returnUser = document.toObject(User.class);
                                callBack_getFromDB.getUser(returnUser);
                            } else {
                                Log.d("tagg", "No such document getUserByPhoneNumber: phone:" + phoneNumber);
                            }
                        } else {
                            Log.d("tagg", "getUserByPhoneNumber get FAILED with ", task.getException());
                        }
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
                                User user = document.toObject(User.class);
                                assert user != null;
                                user.addPetIDToUserPetList(pet.getPetID());
                                upsertUserToDataBase(user);
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
                    ((PetProfileActivity) activity).setListToRecycleView();
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
                                Log.d("tagg", "getPetByID DocumentSnapshot data: " + document.getData());
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

    private void addPetToUser(Pet pet){
        usersByID.document(getCurrentUserID())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot document) {
                        User user = document.toObject(User.class);
                        assert user != null;
                        user.addPetIDToUserPetList(pet.getPetID());
                        upsertUserToDataBase(user);
                    }
                });
    }

}
