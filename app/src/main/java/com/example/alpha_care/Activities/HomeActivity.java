package com.example.alpha_care.Activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alpha_care.DataManager;
import com.example.alpha_care.AdaptersToRecycleView.PetCardAdapter;
import com.example.alpha_care.Enums.finals;
import com.example.alpha_care.R;
import com.example.alpha_care.Objects.User;
import com.example.alpha_care.Utils.MySignal.MessagesToUser;
import com.example.alpha_care.Utils.RequestContactReadPermission;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private PetCardAdapter petCardAdapter;
    private RecyclerView recyclerView;
    private BottomNavigationView homPage_bottom_navigation;
    private User user;
    private Intent intent;
    private Bundle bundle;//contains the userID of the current user
    private RequestContactReadPermission requestContactReadPermission;
    private Map<String, String> contacts;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        requestContactReadPermission = new RequestContactReadPermission(this);
        intent = getIntent();
        bundle = intent.getBundleExtra(finals.BUNDLE.toString());
        findViews();
        user = DataManager.generateUser();
        restartPetCardAdapterToListView();
        getPermissionToReadContactsFromPhone();

        homPage_bottom_navigation.setOnItemSelectedListener(item -> {
            bottomNavigationClickItem(item);
            return true;
        });
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

    @SuppressLint("NonConstantResourceId")
    private void bottomNavigationClickItem(MenuItem item){
        switch (item.getItemId()) {
            case R.id.home_page:
                MessagesToUser.getMe().makeToastMessage("You are at Home");
                break;
            case R.id.health_page:
                Log.d("tagg", item.toString());
                break;
            case R.id.petProfile_page:
                moveToPageWithBundle(PetProfileActivity.class);
                break;
        }
    }

    public void moveToPageWithBundle(Class activity){
        intent = new Intent(HomeActivity.this, activity);
        intent.putExtra(finals.BUNDLE.toString(), bundle);
        startActivity(intent);
        finish();
    }

    public void getPermissionToReadContactsFromPhone(){
        if(!requestContactReadPermission.checkPermission(requestContactReadPermission.getWantPermission()))
            setPopUpValidation().show();
        else {
            if (contacts == null) {
                contacts = requestContactReadPermission.readContacts();
                Log.d("tagg", contacts.get("+972526883505") + "");
                Log.d("tagg", contacts.get("+972504082919") + "");
                Log.d("tagg", contacts.get("+089736211") + "");
            }
        }
    }


    //Functions for the contact read permission
    public MaterialAlertDialogBuilder setPopUpValidation(){
        MaterialAlertDialogBuilder selectGameScreen = new MaterialAlertDialogBuilder(this)
                .setTitle("Contacts Permission")
                .setIcon(R.drawable.ic_icon_popup_contact_permission)
                .setMessage("HI!\nWe need your permission to read your contacts.\nWe would like to connect you with your pet's partners." +
                        "\nEnjoy!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestContactReadPermission.requestPermission(requestContactReadPermission.getWantPermission());
                    }
                });
        return selectGameScreen;
    }

    //What makes the permission massage responsive and jump again after click no
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    contacts = requestContactReadPermission.readContacts();
                } else {
                    requestContactReadPermission.requestPermission(requestContactReadPermission.getWantPermission());
                }
                break;
        }
    }
}
