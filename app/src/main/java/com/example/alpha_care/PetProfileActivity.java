package com.example.alpha_care;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PetProfileActivity extends AppCompatActivity {
    private FloatingActionButton fab_walk, fab_food, fab_groom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        fab_walk.setOnClickListener(view -> {
            fab_walk.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#397D54")));
            //fab_walk.setImageResource(R.drawable.ic_dogwalking_full);
            fab_walk.setImageTintList(ColorStateList.valueOf(Color.parseColor("#73CD88")));
        });

    }

    private void findViews() {
        fab_walk = findViewById(R.id.fab_walk);
        fab_food = findViewById(R.id.fab_food);
        fab_groom = findViewById(R.id.fab_groom);
    }
}