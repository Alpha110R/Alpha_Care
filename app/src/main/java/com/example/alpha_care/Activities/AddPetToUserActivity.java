package com.example.alpha_care.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alpha_care.Enums.EnumFinals;
import com.example.alpha_care.Objects.Pet;
import com.example.alpha_care.R;
import com.example.alpha_care.Repository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddPetToUserActivity extends AppCompatActivity {
    private Intent intent;
    private Bundle bundle;//Has userID
    private MaterialButton addPetToUser_BTN_submit;
    private TextInputEditText addPetToUser_EDT_petAge, addPetToUser_EDT_petName;
    private Pet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpettouser);
        intent = getIntent();
        bundle = intent.getBundleExtra(EnumFinals.BUNDLE.toString());
        findViews();
        pet = new Pet();
        Log.d("tagg", "Add Pet for new Pet: " + pet.toString());

        addPetToUser_BTN_submit.setOnClickListener(view -> {
            pet.setName(addPetToUser_EDT_petName.getText().toString());
            pet.setAge(Integer.parseInt(addPetToUser_EDT_petAge.getText().toString()));
            Repository.getMe().insertToDataBase(pet);
            bundle.putString(EnumFinals.PET_ID.toString(), pet.getPetID());
            moveToPageWithBundle(PetsListActivity.class);
        });

    }

    private void findViews() {
        addPetToUser_BTN_submit = findViewById(R.id.addPetToUser_BTN_submit);
        addPetToUser_EDT_petAge = findViewById(R.id.addPetToUser_EDT_petAge);
        addPetToUser_EDT_petName = findViewById(R.id.addPetToUser_EDT_petName);
    }

    /**
     * Called after the DB updated by the DB
     * @param activity
     */
    public void moveToPageWithBundle(Class activity){
        intent = new Intent(this, activity);
        intent.putExtra(EnumFinals.BUNDLE.toString(),bundle);
        startActivity(intent);
        finish();
    }

}
