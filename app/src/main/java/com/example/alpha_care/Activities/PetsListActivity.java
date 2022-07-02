package com.example.alpha_care.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.example.alpha_care.Utils.MySignal.MessagesToUser;
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
        Log.d("tagg", "onCreate PetsList activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_list);
        findViews();
        initializeIntentBundleAndUserID();
        restartPetList();

        getPetIDAndUpdateUser();
        Repository.getMe().getUserPets(currentUser.getMyPets(),this);

        restartPetCardAdapter();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

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

    /**
     * Checks if there is a new pet for the user
     */
    private void getPetIDAndUpdateUser(){
        String petID = bundle.getString(EnumFinals.PET_ID.toString());
        if(petID != null && !currentUser.getMyPets().contains(petID)){
            currentUser.addPetIDToUserPetList(petID);
        }
    }

    private void restartPetCardAdapter(){
        petCardAdapter = new PetCardAdapter(this);
        petCardAdapter.setPetCardList(petList).setCallBack_PetCard(callBack_petCard);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(petCardAdapter);
    }

    /**
     * Function that add pet to the list for the recycleView every time the fireStore retrieve one
     * @param pet
     */
    @SuppressLint("NotifyDataSetChanged")
    public void addPetToList(Pet pet){
        petList.add(pet);
        petCardAdapter.notifyDataSetChanged();
    }

    private void moveToPageWithBundle(Class activity){
        intent = new Intent(this, activity);
        intent.putExtra(EnumFinals.BUNDLE.toString(),bundle);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("tagg", "onStart PetsList activity");

        //Log.d("tagg", "my pets: " + currentUser.toString());

    }



    @Override
    protected void onResume() {
        super.onResume();
        Log.d("tagg", "onResume PetsList activity");


    }

    private CallBack_PetCard callBack_petCard = new CallBack_PetCard() {
        @Override
        public void clicked(Pet pet) {
            bundle.putString(EnumFinals.PET_ID.toString(), pet.getPetID());
            moveToPageWithBundle(PetProfileActivity.class);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("tagg", "onPause PetsList activity");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("tagg", "onStop PetsList activity");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("tagg", "onDestroy PetsList activity");

    }

    public void restartPetList(){
        petList = new ArrayList<>();
    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            MessagesToUser.getMe().dialogToRemoveItemInRecycleView(PetsListActivity.this
                    ,viewHolder
                    ,petList
                    ,petCardAdapter);
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                // Get RecyclerView item from the ViewHolder
                View itemView = viewHolder.itemView;

                Paint p = new Paint();
                if (dX > 0) {
                    // Set your color for positive displacement //
                    p.setColor(Color.parseColor("#FF7A7D"));

                    // Draw Rect with varying right side, equal to displacement dX
                    c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                            (float) itemView.getBottom(), p);
                } else {
                    // Set your color for negative displacement //
                    p.setColor(Color.parseColor("#FF7A7D"));
                    // Draw Rect with varying left side, equal to the item's right side plus negative displacement dX
                    c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                            (float) itemView.getRight(), (float) itemView.getBottom(), p);
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

    };
}
