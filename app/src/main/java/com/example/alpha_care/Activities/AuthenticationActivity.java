package com.example.alpha_care.Activities;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alpha_care.CallBacks.CallBack_ContactPermission;
import com.example.alpha_care.Objects.User;
import com.example.alpha_care.R;
import com.example.alpha_care.Utils.RequestContactReadPermission;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AuthenticationActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;

    private MaterialButton authentication_BTN_signIn, authentication_BTN_user;
    private MaterialTextView authentication_LBL_info;
    private User appUser;
    private FirebaseAuth mAuth;
    private RequestContactReadPermission requestContactReadPermission;
    private Map<String, String> contacts = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        findViews();
        requestContactReadPermission = new RequestContactReadPermission(this);
        mAuth = FirebaseAuth.getInstance();
// Initialize Firebase Auth
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference cities = db.collection("cities");
        CollectionReference users = db.collection("users");

        authentication_BTN_signIn.setOnClickListener(view -> {
            FirebaseUser user = mAuth.getCurrentUser();
            String str="";
            if(user !=null){
                appUser = new User(user.getUid()).setPhoneNumber(user.getPhoneNumber());
                users.document(appUser.getPhoneNumber()).set(appUser);
                str += "\n" + user.getUid();
                str += "\n" + user.getDisplayName();
                str += "\n" + user.getPhoneNumber();
            }else{
                SignIn();
            }
            authentication_LBL_info.setText(str);
        });
        authentication_BTN_user.setOnClickListener(view -> {
            updateUI();
            Log.d("tagg", contacts.get("+972526883505") +"");
        });


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

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        String message = result.toString();
        Log.d("tagg", result.getIdpResponse().getProviderType() + "\n" + result.getIdpResponse().getPhoneNumber());
    }

    private void findViews() {
        authentication_BTN_signIn = findViewById(R.id.authentication_BTN_signIn);
        authentication_LBL_info = findViewById(R.id.authentication_LBL_info);
        authentication_BTN_user = findViewById(R.id.authentication_BTN_user);
    }

    private void SignIn(){
    // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build());

    // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTosAndPrivacyPolicyUrls("https://firebase.google.com/docs/auth/android/firebaseui","")
                .build();
        signInLauncher.launch(signInIntent);
    }

    private void updateUI() {
        FirebaseUser user = mAuth.getCurrentUser();
        String str = "User\n";
        if(user !=null){
            str += "\n" + user.getUid();
            str += "\n" + user.getDisplayName();
            str += "\n" + user.getPhoneNumber();
        }
        authentication_LBL_info.setText(str);
    }

    public MaterialAlertDialogBuilder setPopUpValidation(){
        MaterialAlertDialogBuilder selectGameScreen = new MaterialAlertDialogBuilder(this)
                .setTitle("Contacts Permission")
                .setIcon(R.drawable.ic_icon_popup_contact_permission)
                .setMessage("HI\nWe need your Contact Permission to connect you with your partners." +
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





 /*private void signUp() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("tagg", "createUserWithEmail:success");
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("tagg", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(AuthenticationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI();
                        }
                    }


                });
    }*/

/*
Map<String, Object> data1 = new HashMap<>();
        data1.put("name", "San Francisco");
        data1.put("state", "CA");
        data1.put("country", "USA");
        data1.put("capital", false);
        data1.put("population", 860000);
        data1.put("regions", Arrays.asList("west_coast", "norcal"));
        cities.document("SF").set(data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("name", "Los Angeles");
        data2.put("state", "CA");
        data2.put("country", "USA");
        data2.put("capital", false);
        data2.put("population", 3900000);
        data2.put("regions", Arrays.asList("west_coast", "socal"));
        cities.document("LA").set(data2);

        Map<String, Object> data3 = new HashMap<>();
        data3.put("name", "Washington D.C.");
        data3.put("state", null);
        data3.put("country", "USA");
        data3.put("capital", true);
        data3.put("population", 680000);
        data3.put("regions", Arrays.asList("east_coast"));
        cities.document("DC").set(data3);

        Map<String, Object> data4 = new HashMap<>();
        data4.put("name", "Tokyo");
        data4.put("state", null);
        data4.put("country", "Japan");
        data4.put("capital", true);
        data4.put("population", 9000000);
        data4.put("regions", Arrays.asList("kanto", "honshu"));
        cities.document("TOK").set(data4);

        Map<String, Object> data5 = new HashMap<>();
        data5.put("name", "Beijing");
        data5.put("state", null);
        data5.put("country", "China");
        data5.put("capital", true);
        data5.put("population", 21500000);
        data5.put("regions", Arrays.asList("jingjinji", "hebei"));
        cities.document("BJ").set(data5);
 */