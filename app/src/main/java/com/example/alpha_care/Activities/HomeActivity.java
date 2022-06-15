package com.example.alpha_care.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alpha_care.DataManager;
import com.example.alpha_care.AdaptersToRecycleView.PetCardAdapter;
import com.example.alpha_care.R;
import com.example.alpha_care.Objects.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private PetCardAdapter petCardAdapter;
    private RecyclerView recyclerView;
    private BottomNavigationView homPage_bottom_navigation;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViews();
        user = DataManager.generateUser();
        restartPetCardAdapterToListView();
    }

    private void restartPetCardAdapterToListView() {
        petCardAdapter = new PetCardAdapter().setPetCardList(user.getMyPets());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(petCardAdapter);
    }

    private void findViews() {
        recyclerView = findViewById(R.id.homePage_LST_pets);
        homPage_bottom_navigation = findViewById(R.id.homPage_bottom_navigation);
    }

}
