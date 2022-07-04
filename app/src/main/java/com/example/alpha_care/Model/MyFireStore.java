package com.example.alpha_care.Model;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.alpha_care.Activities.AddContactToPetActivity;
import com.example.alpha_care.Activities.AddPetToUserActivity;
import com.example.alpha_care.Activities.LogInActivity;
import com.example.alpha_care.Activities.PetProfileActivity;
import com.example.alpha_care.Activities.PetsListActivity;
import com.example.alpha_care.Activities.SetUserNameActivity;
import com.example.alpha_care.Enums.EnumPetEventType;
import com.example.alpha_care.Objects.Pet;
import com.example.alpha_care.Objects.PetEventCard;
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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MyFireStore {
    private CollectionReference usersByID, usersByUserName, petsByID;
    private User returnUser;
    private Pet pet;
    private User user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MyFireStore(){
        usersByID = db.collection("usersByID");//All the users in the application
        usersByUserName = db.collection("usersByUserName");//All the users in the application
        petsByID = db.collection("petsByID");//All the pets in the application
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

    public void addNewUser(User userToAdd){
        usersByID.document(userToAdd.getUID()).set(userToAdd).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                addNewUserName(userToAdd);
            }
        });
    }

    public void getUserByID(Activity activity){
        usersByID.document(getCurrentUserID())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        //Log.d("tagg", "getUserByID DocumentSnapshot data: " + documentSnapshot.getData());
                        returnUser = documentSnapshot.toObject(User.class);
                        if(activity instanceof LogInActivity){
                            ((LogInActivity)activity).createNewUserIfNotExist(returnUser);
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
                        else{
                            if(activity instanceof AddContactToPetActivity){
                                ((AddContactToPetActivity) activity).initializeCurrentPet(pet);
                            }
                        }
                    }
                });
    }

    public void getUserByUserName(String userName, Activity activity){
        usersByUserName.document(userName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("tagg", "getUserByUserName DocumentSnapshot data: " + document.getData());
                                if(activity instanceof SetUserNameActivity)
                                    ((SetUserNameActivity)activity).userNameExist();
                            } else {
                                Log.d("tagg", "No such document getUserByUserName: userName:" + userName);
                                if(activity instanceof SetUserNameActivity) {
                                    ((SetUserNameActivity) activity).createUser();//initialize the username activate addNewUser()
                                }
                            }
                        } else {
                            Log.d("tagg", "getUserByUserName get FAILED with ", task.getException());
                        }
                    }
                });
    }

    private void addNewUserName(User newUser){
        usersByUserName.document(newUser.getUserName())
                            .set(newUser)
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

    /**
     * Add contact to the contacts list in the pet -> to show all the contacts in this pet
     * @param petID
     * @param userName
     */
    public void addContactToPet(Activity activity, String petID, String userName){
        petsByID.document(petID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot document) {
                       pet = document.toObject(Pet.class);
                        assert pet != null;
                        pet.addContactUserNameToList(userName);
                        petsByID.document(petID).set(pet).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("tagg", "Contact added to pet");
                            }
                        });
                        if(activity instanceof AddContactToPetActivity)
                            ((AddContactToPetActivity)activity).moveToPageWithBundle(PetProfileActivity.class);
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
     * Update user for a new petID by another user that added him to a pet
     * @param activity
     * @param userID
     * @param petID
     */
    public void updateContactUserToPet(Activity activity, String userID, String petID){
        usersByID.document(userID)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot document) {
                user = document.toObject(User.class);
                assert user != null;
                user.addPetIDToUserPetList(petID);
                updateUserToDataBase(user);
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

    public void getAllUserName(Activity activity){
        usersByUserName.get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (DocumentSnapshot documentSnapshot :
                                        queryDocumentSnapshots.getDocuments()) {
                                    user = documentSnapshot.toObject(User.class);
                                    if(activity instanceof AddContactToPetActivity){
                                        ((AddContactToPetActivity)activity).addUserNameToList(user.getUserName(), user.getUID());
                                    }
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
                                 petsByID.document(petID)//Remove the user from the pet's contacts list
                                         .get()
                                         .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                             @Override
                                             public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                 pet = documentSnapshot.toObject(Pet.class);
                                                 assert pet != null;
                                                 List<String> petContacts = pet.getMyContactsUserName();
                                                 if(petContacts.contains(user.getUserName())) {
                                                     petContacts.remove(user.getUserName());
                                                     updatePetByNewEventCard(activity, pet);
                                                 }
                                             }
                                         });
                             } else {
                                 Log.d("tagg", "No such document deletePetAtCurrentUser");
                             }
                         } else {
                             Log.d("tagg", "deletePetAtCurrentUser get FAILED with ", task.getException());
                         }
                     }
                 });
    }

    public void deleteEventCardFromPet(Activity activity, String petID, EnumPetEventType enumPetEventType, int position){
        petsByID.document(petID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        pet = documentSnapshot.toObject(Pet.class);
                        assert pet != null;
                        pet.deleteEventCard(enumPetEventType, position);
                        petsByID.document(petID).set(pet).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("tagg", "eventCard deleted from pet");
                            }
                        });
                        if(activity instanceof PetProfileActivity)
                            ((PetProfileActivity)activity).notifyDataChangeRecycleView();
                    }
                });
    }

}
