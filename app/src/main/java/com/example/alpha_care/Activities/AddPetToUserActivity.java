package com.example.alpha_care.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
    private MaterialButton addPetToUser_BTN_submit, addPetToUser_BTN_petImage;
    private TextInputEditText addPetToUser_EDT_petAge, addPetToUser_EDT_petName;
    private ProgressBar addPetToUser_progress_bar;
    private Pet pet;
    private static final int PICK_FROM_GALLERY = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpettouser);
        intent = getIntent();
        bundle = intent.getBundleExtra(EnumFinals.BUNDLE.toString());
        findViews();
        pet = new Pet();

        addPetToUser_BTN_petImage.setOnClickListener(view -> {
            /*try {
                if (ActivityCompat.checkSelfPermission(AddPetToUserActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddPetToUserActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                } else {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    mGetContent.launch("image/*");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            addPetToUser_BTN_submit.setClickable(false);
            addPetToUser_BTN_submit.setAlpha(0.4f);
            mGetContent.launch("image/*");

        });

        addPetToUser_BTN_submit.setOnClickListener(view -> {
            pet.setName(addPetToUser_EDT_petName.getText().toString());
            pet.setAge(Integer.parseInt(addPetToUser_EDT_petAge.getText().toString()));
            Repository.getMe().insertToDataBase(pet);
            bundle.putString(EnumFinals.PET_ID.toString(), pet.getPetID());
            moveToPageWithBundle(PetsListActivity.class);
        });

    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                    Log.d("tagg", "enter the Activity Result");
                    Repository.getMe().uploadImageToStorage(AddPetToUserActivity.this, uri, addPetToUser_progress_bar);
                }
            });

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    mGetContent.launch("image/*");
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
        }
    }*/

    private void findViews() {
        addPetToUser_BTN_submit = findViewById(R.id.addPetToUser_BTN_submit);
        addPetToUser_BTN_petImage = findViewById(R.id.addPetToUser_BTN_petImage);
        addPetToUser_EDT_petAge = findViewById(R.id.addPetToUser_EDT_petAge);
        addPetToUser_EDT_petName = findViewById(R.id.addPetToUser_EDT_petName);
        addPetToUser_progress_bar = findViewById(R.id.addPetToUser_progress_bar);
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

    public void setPetImageUrl(String imageUrl){
        pet.setPetImageUrl(imageUrl);
    }

    /**
     * After the upload of image is done it will turn it on
     */
    public void setSubmitButtonOn(){
        addPetToUser_BTN_submit.setClickable(true);
        addPetToUser_BTN_submit.setAlpha(1);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("tagg", "onStop AddPetToUser activity");
    }
}
