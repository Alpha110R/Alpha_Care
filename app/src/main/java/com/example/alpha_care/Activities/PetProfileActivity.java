package com.example.alpha_care.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.alpha_care.DataManager;
import com.example.alpha_care.AdaptersToRecycleView.EventCardAdapter;
import com.example.alpha_care.Objects.Pet;
import com.example.alpha_care.Objects.PetEvent;
import com.example.alpha_care.Enums.PetEventType;
import com.example.alpha_care.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

public class PetProfileActivity extends AppCompatActivity {
    private FloatingActionButton petProfile_FAB_walk, petProfile_FAB_food, petProfile_FAB_groom;
    private MaterialTextView petProfile_LBL_amountGroomming, petProfile_LBL_amountFood, petProfile_LBL_amountWalk;
    private RecyclerView recyclerView;
    private EventCardAdapter eventCardAdapter;
    private BottomNavigationView petProfile_bottom_navigation;
    private Pet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petprofile);
        findViews();
        pet = DataManager.generatePet();
        initializeAmountsOfEvents();
        restartPetEventCardAdapterToListView();

        petProfile_FAB_walk.setOnClickListener(view -> {
            petProfile_FAB_walk.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#397D54")));
            petProfile_FAB_walk.setImageTintList(ColorStateList.valueOf(Color.parseColor("#73CD88")));
            eventCardAdapter.setPetEventCardList(pet.getPetEventCardByType(PetEventType.WALK));
            recyclerView.getAdapter().notifyDataSetChanged();

        });
        petProfile_FAB_food.setOnClickListener(view -> {
            petProfile_FAB_food.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#397D54")));
            petProfile_FAB_food.setImageTintList(ColorStateList.valueOf(Color.parseColor("#73CD88")));
            eventCardAdapter.setPetEventCardList(pet.getPetEventCardByType(PetEventType.FOOD));
            recyclerView.getAdapter().notifyDataSetChanged();

        });
        petProfile_FAB_groom.setOnClickListener(view -> {
            petProfile_FAB_groom.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#397D54")));
            petProfile_FAB_groom.setImageTintList(ColorStateList.valueOf(Color.parseColor("#73CD88")));
            eventCardAdapter.setPetEventCardList(pet.getPetEventCardByType(PetEventType.GROOM));
            recyclerView.getAdapter().notifyDataSetChanged();

        });
        petProfile_bottom_navigation.setOnItemSelectedListener(item -> {
            bottomNavigationClickItem(item);
            return true;
        });
    }

    private void initializeAmountsOfEvents() {
        for (PetEvent event:
             pet.getPetEvents()) {
            switch (event.getPetEventType()) {
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
        petProfile_bottom_navigation = findViewById(R.id.petProfile_bottom_navigation);
        petProfile_LBL_amountGroomming = findViewById(R.id.petProfile_LBL_amountGroomming);
        petProfile_LBL_amountFood = findViewById(R.id.petProfile_LBL_amountFood);
        petProfile_LBL_amountWalk = findViewById(R.id.petProfile_LBL_amountWalk);

    }
    private void bottomNavigationClickItem(MenuItem item){
        switch (item.getItemId()) {
            case R.id.home_page:

            case R.id.health_page:

            case R.id.petProfile_page:
                Log.d("tagg", item.toString());
                break;
        }
    }

    public void restartPetEventCardAdapterToListView(){
        eventCardAdapter = new EventCardAdapter();
        eventCardAdapter.setPetEventCardList(pet.getPetEventCardByType(PetEventType.FOOD));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(eventCardAdapter);
    }

}