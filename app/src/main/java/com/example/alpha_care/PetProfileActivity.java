package com.example.alpha_care;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PetProfileActivity extends AppCompatActivity {
    private FloatingActionButton fab_walk, fab_food, fab_groom;
    private RecyclerView recyclerView;
    private EventCardAdapter eventCardAdapter;
    private BottomNavigationView bottom_navigation;
    private Pet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        pet = DataManager.generatePet();
        Log.d("ptt",pet.getPetEvents().toString());
        restartPetEventCardAdapterToListView();

        fab_walk.setOnClickListener(view -> {
            fab_walk.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#397D54")));
            fab_walk.setImageTintList(ColorStateList.valueOf(Color.parseColor("#73CD88")));
        });
        bottom_navigation.setOnItemSelectedListener(item -> {
            bottomNavigationClickItem(item);
            return true;
        });

    }

    private void findViews() {
        fab_walk = findViewById(R.id.fab_walk);
        fab_food = findViewById(R.id.fab_food);
        fab_groom = findViewById(R.id.fab_groom);
        recyclerView = findViewById(R.id.petProfile_LST_events);
        bottom_navigation = findViewById(R.id.bottom_navigation);

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