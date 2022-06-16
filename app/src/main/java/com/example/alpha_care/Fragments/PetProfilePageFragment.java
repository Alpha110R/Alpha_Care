package com.example.alpha_care.Fragments;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alpha_care.AdaptersToRecycleView.EventCardAdapter;
import com.example.alpha_care.DataManager;
import com.example.alpha_care.Enums.PetEventType;
import com.example.alpha_care.Enums.finals;
import com.example.alpha_care.Objects.Pet;
import com.example.alpha_care.Objects.PetEvent;
import com.example.alpha_care.R;
import com.example.alpha_care.Utils.RequestContactReadPermission;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

public class PetProfilePageFragment extends Fragment {

    private FloatingActionButton petProfile_FAB_walk, petProfile_FAB_food, petProfile_FAB_groom;
    private MaterialTextView petProfile_LBL_amountGroomming, petProfile_LBL_amountFood, petProfile_LBL_amountWalk;
    private RecyclerView recyclerView;
    private EventCardAdapter eventCardAdapter;
    private Pet pet;
    private Activity activity;
    public PetProfilePageFragment (Activity activity){
        this.activity = activity;
    }

    public PetProfilePageFragment setPetToShow(Pet pet){
        this.pet = pet;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.activity_petprofile, container, false);

        findViews(fragmentView);
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


        return fragmentView;
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

    private void findViews(View fragmentView) {
        petProfile_FAB_walk = fragmentView.findViewById(R.id.petProfile_FAB_walk);
        petProfile_FAB_food = fragmentView.findViewById(R.id.petProfile_FAB_food);
        petProfile_FAB_groom = fragmentView.findViewById(R.id.petProfile_FAB_groom);
        recyclerView = fragmentView.findViewById(R.id.petProfile_LST_events);
        petProfile_LBL_amountGroomming = fragmentView.findViewById(R.id.petProfile_LBL_amountGroomming);
        petProfile_LBL_amountFood = fragmentView.findViewById(R.id.petProfile_LBL_amountFood);
        petProfile_LBL_amountWalk = fragmentView.findViewById(R.id.petProfile_LBL_amountWalk);

    }

    public void restartPetEventCardAdapterToListView(){
        eventCardAdapter = new EventCardAdapter();
        eventCardAdapter.setPetEventCardList(pet.getPetEventCardByType(PetEventType.FOOD));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(eventCardAdapter);
    }

}
