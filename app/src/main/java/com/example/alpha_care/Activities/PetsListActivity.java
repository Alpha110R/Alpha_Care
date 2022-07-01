package com.example.alpha_care.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alpha_care.AdaptersToRecycleView.PetCardAdapter;
import com.example.alpha_care.CallBacks.CallBack_PetCard;
import com.example.alpha_care.Enums.EnumFinals;
import com.example.alpha_care.MyFireStore;
import com.example.alpha_care.Objects.Pet;
import com.example.alpha_care.Objects.User;
import com.example.alpha_care.R;
import com.example.alpha_care.Repository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PetsListActivity extends AppCompatActivity {
    private PetCardAdapter petCardAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton PetsListActivity_FAB_addPet;
    private Intent intent;
    private Bundle bundle;
    private User currentUser;
    private List<Pet> petList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("tagg", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_list);
        findViews();
        initializeIntentBundleAndUserID();
        restartPetList();

        intent = getIntent();
        bundle = intent.getBundleExtra(EnumFinals.BUNDLE.toString());
        String petID = bundle.getString(EnumFinals.PET_ID.toString());
        if(petID != null && !currentUser.getMyPets().contains(petID)){
            currentUser.addPetIDToUserPetList(petID);
        }
        Repository.getMe().getUserPets(currentUser.getMyPets(),this);


        restartPetCardAdapter();

        PetsListActivity_FAB_addPet.setOnClickListener(view -> {
            moveToPageWithBundle(AddPetToUserActivity.class);
        });

    }

    private void findViews() {
        recyclerView = findViewById(R.id.PetsListActivity_LST_pets);
        PetsListActivity_FAB_addPet = findViewById(R.id.PetsListActivity_FAB_addPet);
    }

    private void initializeIntentBundleAndUserID(){
        intent = getIntent();
        bundle = intent.getBundleExtra(EnumFinals.BUNDLE.toString());
        currentUser = new Gson().fromJson(bundle.getString(EnumFinals.USER.toString()), User.class);
    }

    private void restartPetCardAdapter(){
        petCardAdapter = new PetCardAdapter();
        petCardAdapter.setPetCardList(petList).setCallBack_PetCard(callBack_petCard);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(petCardAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addPetToList(Pet pet){
        petList.add(pet);
        petCardAdapter.notifyDataSetChanged();
    }

    private void moveToPageWithBundle(Class activity){
        intent = new Intent(this, activity);
        intent.putExtra(EnumFinals.BUNDLE.toString(),bundle);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("tagg", "onStart");

        Log.d("tagg", "my pets: " + currentUser.toString());

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("tagg", "onResume");

    }

    private CallBack_PetCard callBack_petCard = new CallBack_PetCard() {
        @Override
        public void clicked(Pet pet) {
            bundle.putString(EnumFinals.PET_ID.toString(), pet.getPetID());
            moveToPageWithBundle(PetProfileActivity.class);
        }
    };

    public void restartPetList(){
        petList = new ArrayList<>();
    }

}
