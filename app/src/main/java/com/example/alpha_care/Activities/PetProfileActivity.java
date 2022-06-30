package com.example.alpha_care.Activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alpha_care.AdaptersToRecycleView.EventCardAdapter;
import com.example.alpha_care.Enums.EnumFinals;
import com.example.alpha_care.Enums.EnumPetEventType;
import com.example.alpha_care.Objects.Pet;
import com.example.alpha_care.Objects.PetEvent;
import com.example.alpha_care.Objects.User;
import com.example.alpha_care.R;
import com.example.alpha_care.Repository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

public class PetProfileActivity extends AppCompatActivity {
    private Intent intent;
    private Bundle bundle;
    private FloatingActionButton petProfile_FAB_walk, petProfile_FAB_food, petProfile_FAB_groom, petProfile_FAB_addEventCard;
    private MaterialTextView petProfile_LBL_amountGroomming, petProfile_LBL_amountFood, petProfile_LBL_amountWalk;
    private MaterialButton petProfile_BTN_close;
    private RecyclerView recyclerView;
    private EventCardAdapter eventCardAdapter;
    private EnumPetEventType enumPetEventTypeFlag;
    private Pet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petprofile);
        initializeIntentBundle();

        findViews();

        petProfile_FAB_walk.setOnClickListener(view -> {
            enumPetEventTypeFlag = EnumPetEventType.WALK;
            petProfile_FAB_walk.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#397D54")));
            petProfile_FAB_walk.setImageTintList(ColorStateList.valueOf(Color.parseColor("#73CD88")));
            eventCardAdapter.setPetEventCardList(pet.getPetEventCardByType(EnumPetEventType.WALK));
            recyclerView.getAdapter().notifyDataSetChanged();

        });
        petProfile_FAB_food.setOnClickListener(view -> {
            enumPetEventTypeFlag = EnumPetEventType.FOOD;
            petProfile_FAB_food.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#397D54")));
            petProfile_FAB_food.setImageTintList(ColorStateList.valueOf(Color.parseColor("#73CD88")));
            eventCardAdapter.setPetEventCardList(pet.getPetEventCardByType(EnumPetEventType.FOOD));
            recyclerView.getAdapter().notifyDataSetChanged();

        });
        petProfile_FAB_groom.setOnClickListener(view -> {
            enumPetEventTypeFlag = EnumPetEventType.GROOM;
            petProfile_FAB_groom.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#397D54")));
            petProfile_FAB_groom.setImageTintList(ColorStateList.valueOf(Color.parseColor("#73CD88")));
            eventCardAdapter.setPetEventCardList(pet.getPetEventCardByType(EnumPetEventType.GROOM));
            recyclerView.getAdapter().notifyDataSetChanged();

        });

        petProfile_BTN_close.setOnClickListener(view -> {
            moveToPageWithBundle(PetsListActivity.class);
        });

        petProfile_FAB_addEventCard.setOnClickListener(view -> {
            pet.addEventCard(enumPetEventTypeFlag, "ALON");
            Repository.getMe().updatePetByNewEventCard(this, pet);

        });

    }

    private void initializeIntentBundle(){
        intent = getIntent();
        bundle = intent.getBundleExtra(EnumFinals.BUNDLE.toString());
        Repository.getMe().getPetByID(this, bundle.getString(EnumFinals.PET_ID.toString()));//Activate initializePet()
    }

    public void initializePet(Pet pet){
        this.pet = pet;
        Log.d("tagg", "initializePet: " + pet.toString());
        initializeAmountsOfEvents();
        restartPetEventCardAdapterToListView();

    }

    private void moveToPageWithBundle(Class activity){
        intent = new Intent(this, activity);
        intent.putExtra(EnumFinals.BUNDLE.toString(),bundle);
        startActivity(intent);
        finish();
    }

    private void initializeAmountsOfEvents() {
        for (PetEvent event:
                pet.getPetEvents().values()) {
            switch (event.getEnumPetEventType()) {
                case WALK:
                    petProfile_LBL_amountWalk.setText("0/" + event.getAmount());
                    break;
                case FOOD:
                    petProfile_LBL_amountFood.setText("0/" + event.getAmount());
                    break;
                case GROOM:
                    petProfile_LBL_amountGroomming.setText("0/" + event.getAmount());
                    break;
            }
        }
    }

    private void findViews() {
        petProfile_FAB_walk = findViewById(R.id.petProfile_FAB_walk);
        petProfile_FAB_food = findViewById(R.id.petProfile_FAB_food);
        petProfile_FAB_groom = findViewById(R.id.petProfile_FAB_groom);
        recyclerView = findViewById(R.id.petProfile_LST_events);
        petProfile_LBL_amountGroomming = findViewById(R.id.petProfile_LBL_amountGroomming);
        petProfile_LBL_amountFood = findViewById(R.id.petProfile_LBL_amountFood);
        petProfile_LBL_amountWalk = findViewById(R.id.petProfile_LBL_amountWalk);
        petProfile_BTN_close = findViewById(R.id.petProfile_BTN_close);
        petProfile_FAB_addEventCard = findViewById(R.id.petProfile_FAB_addEventCard);
    }

    private void restartPetEventCardAdapterToListView(){
        eventCardAdapter = new EventCardAdapter();
        enumPetEventTypeFlag = EnumPetEventType.WALK;
        eventCardAdapter.setPetEventCardList(pet.getPetEventCardByType(EnumPetEventType.WALK));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(eventCardAdapter);
    }

    public void notifyDataChangeRecycleView(){
        eventCardAdapter.notifyDataSetChanged();
        Log.d("tagg", ""+ pet.getPetEventCardByType(EnumPetEventType.FOOD).get(0).getDateExecution());

    }
}
