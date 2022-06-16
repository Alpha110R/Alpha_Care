package com.example.alpha_care.Activities;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alpha_care.CallBacks.CallBack_ContactPermission;
import com.example.alpha_care.CallBacks.CallBack_PetCard;
import com.example.alpha_care.CallBacks.CallBack_ReplaceFragment;
import com.example.alpha_care.Fragments.HomePageFragment;
import com.example.alpha_care.Fragments.PetProfilePageFragment;
import com.example.alpha_care.Objects.Pet;
import com.example.alpha_care.Objects.User;
import com.example.alpha_care.R;
import com.example.alpha_care.Utils.MySignal.MessagesToUser;
import com.example.alpha_care.Utils.RequestContactReadPermission;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Map;

public class BasePage extends AppCompatActivity {
    private FrameLayout basePage_FRM_mainFrame;
    private BottomNavigationView basePage_bottom_navigation;
    private HomePageFragment homePageFragment;
    private PetProfilePageFragment petProfilePageFragment;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private RequestContactReadPermission requestContactReadPermission;
    private Map<String, String> contacts;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basepage);
        findViews();
        requestContactReadPermission = new RequestContactReadPermission(this);
        homePageFragment = new HomePageFragment(this).setCallBack_ContactPermission(callBack_contactPermission)
                                                            .setCallBack_ReplaceFragment(callBack_replaceFragment);
        petProfilePageFragment = new PetProfilePageFragment(this).setPetToShow(new Pet());
        reloadHomeFragment();

        basePage_bottom_navigation.setOnItemSelectedListener(item -> {
            bottomNavigationClickItem(item);
            return true;
        });
    }

    private void findViews() {
        basePage_FRM_mainFrame = findViewById(R.id.basePage_FRM_mainFrame);
        basePage_bottom_navigation = findViewById(R.id.basePage_bottom_navigation);
    }

    private void bottomNavigationClickItem(MenuItem item){
        switch (item.getItemId()) {
            case R.id.home_page:
                reloadHomeFragment();
                break;
            case R.id.health_page:
                Log.d("tagg", item.toString());
                break;
            case R.id.petProfile_page:
                reloadPetProfileFragment();
                break;
        }
    }

    private CallBack_ContactPermission callBack_contactPermission = new CallBack_ContactPermission() {
        @Override
        public void request() {
            getPermissionToReadContactsFromPhone();
        }
    };
    private CallBack_ReplaceFragment callBack_replaceFragment = new CallBack_ReplaceFragment() {
        @Override
        public void replaceToPetFragment(Pet pet) {
            petProfilePageFragment.setPetToShow(pet);
            reloadPetProfileFragment();
        }
    };

    private void reloadPetProfileFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.basePage_FRM_mainFrame, petProfilePageFragment)
                .commit();
    }

    private void reloadHomeFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.basePage_FRM_mainFrame, homePageFragment)
                .commit();
    }





    //Contact Read Permission///////////
    public void getPermissionToReadContactsFromPhone(){
        if(!requestContactReadPermission.checkPermission(requestContactReadPermission.getWantPermission()))
            setPopUpValidation().show();
        else {
            if (contacts == null) {//TODO: check in DB if in the user there is contacts list
                MessagesToUser.getMe().makeToastMessage("Read contacts");
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
