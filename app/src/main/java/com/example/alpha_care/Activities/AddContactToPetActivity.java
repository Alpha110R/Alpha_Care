package com.example.alpha_care.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alpha_care.AdaptersToRecycleView.AddContactCardAdapter;
import com.example.alpha_care.CallBacks.CallBack_UserNameContactCard;
import com.example.alpha_care.Enums.EnumFinals;
import com.example.alpha_care.Objects.Pet;
import com.example.alpha_care.R;
import com.example.alpha_care.Model.Repository;
import com.example.alpha_care.Utils.UserNameComparator;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddContactToPetActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AddContactCardAdapter addContactCardAdapter;
    private List<String> userNameListFromDB;
    private Map<String, String> userIDByUserNameMap;//<userName, userID> To get the userID faster when i want to add to it the current pet
    private TextInputEditText addContactToPet_EDT_userName;
    private Intent intent;
    private Bundle bundle;
    private String petID, userID;
    private Pet currentPet;
    private List <String> currentContactInPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontacttopet);
        findViews();
        userNameListFromDB = new ArrayList<>();
        userIDByUserNameMap = new HashMap<>();
        restartPetCardAdapter();
        initializeIntentBundle();
        petID = bundle.getString(EnumFinals.PET_ID.toString());
        Repository.getMe().getPetByID(this, petID);

        addContactToPet_EDT_userName.addTextChangedListener(new TextWatcher() {

            @SuppressLint("NotifyDataSetChanged")
            public void afterTextChanged(Editable s) {
                if(s.length() == 0){
                    Collections.sort(userNameListFromDB, new UserNameComparator());
                    addContactCardAdapter.setUserNameList(userNameListFromDB);
                    addContactCardAdapter.notifyDataSetChanged();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @SuppressLint("NotifyDataSetChanged")
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                addContactCardAdapter.setUserNameList(getListContactBySearch(s.toString()));
                addContactCardAdapter.notifyDataSetChanged();
            }
        });


    }

    private void findViews() {
        addContactToPet_EDT_userName = findViewById(R.id.addContactToPet_EDT_userName);
        recyclerView = findViewById(R.id.addContactToPet_LST_userNames);
    }

    private void restartPetCardAdapter(){
        addContactCardAdapter = new AddContactCardAdapter(this);
        addContactCardAdapter.setUserNameList(userNameListFromDB).setCallBack_UserNameContactCard(callBack_userNameContactCard);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(addContactCardAdapter);
    }

    private void initializeIntentBundle() {
        intent = getIntent();
        bundle = intent.getBundleExtra(EnumFinals.BUNDLE.toString());
    }

    /**
     * Called by the fireStore when it retrieve the current pet bt petID
     * @param pet
     */
    public void initializeCurrentPet(Pet pet){
        this.currentPet = pet;
        currentContactInPet = pet.getMyContactsUserName();
        Repository.getMe().getAllUserName(this);
    }

    /**
     * Function to find all the usenames that start with the input of search
     * @param search
     * @return
     */
    public List<String> getListContactBySearch(String search){
        List<String> newUserNameList = new ArrayList<>();
        for(int i=0 ; i< userNameListFromDB.size(); i++){
            if(userNameListFromDB.get(i).startsWith(search))
                newUserNameList.add(userNameListFromDB.get(i));
        }
        return newUserNameList;
    }

    /**
     * Called by the fireStore when retrieving all the usernames in the DB
     * @param userName
     */
    @SuppressLint("NotifyDataSetChanged")
    public void addUserNameToList(String userName, String userID){
        if(!currentContactInPet.contains(userName)) {//Checks if the userName is already exist in the contacts -> if exist it wont show this userName
            userNameListFromDB.add(userName);
            addContactCardAdapter.notifyDataSetChanged();
            userIDByUserNameMap.put(userName, userID);
        }
    }


    CallBack_UserNameContactCard callBack_userNameContactCard = new CallBack_UserNameContactCard() {
        @Override
        public void clicked(String userName) {//Need to add to the contacts list of the pet the user name
                                                //+ To add to the user of this username the current petID
            userID = userIDByUserNameMap.get(userName);
            Repository.getMe().updateContactUserToPet(AddContactToPetActivity.this, userID, petID);
            Repository.getMe().addContactToPet(AddContactToPetActivity.this, petID, userName);
        }
    };

    /**
     * Will called with PetProfileActivity.Class by FireStore after DB will be updated
     * @param activity
     */
    public void moveToPageWithBundle(Class activity){
        intent = new Intent(this, activity);
        intent.putExtra(EnumFinals.BUNDLE.toString(),bundle);
        startActivity(intent);
        finish();
    }
}
