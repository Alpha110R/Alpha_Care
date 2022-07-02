package com.example.alpha_care.Activities;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alpha_care.CallBacks.CallBack_getFromRepository;
import com.example.alpha_care.Enums.EnumFinals;
import com.example.alpha_care.Objects.Pet;
import com.example.alpha_care.Objects.User;
import com.example.alpha_care.R;
import com.example.alpha_care.Repository;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class LogInActivity extends AppCompatActivity {
    private MaterialButton authentication_BTN_signIn;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Bundle bundle;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        intent = new Intent(LogInActivity.this, PetsListActivity.class);
        bundle = new Bundle();
        Repository.getMe().setCallBack_getFromRepository(callBack_getFromRepository);

        authentication_BTN_signIn.setOnClickListener(view -> {
            SignIn();
        });
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
                .setLogo(R.drawable.ic_icon_popup_contact_permission)
                .build();
        signInLauncher.launch(signInIntent);
    }

    @Override
    protected void onStart () {
        super.onStart();
    }

    @Override
    protected void onResume () {
        super.onResume();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if(user!=null){
            Repository.getMe().getUserByID(this);
        }
    }

    private CallBack_getFromRepository callBack_getFromRepository = new CallBack_getFromRepository() {
        @Override
        public void getUser( User returnUser) {
            if(returnUser!=null) {
                bundle.putString(EnumFinals.USER.toString(), new Gson().toJson(returnUser));
                Log.d("tagg", "LogIn NOT NULL user: " + returnUser.getUID() + " phone: " + returnUser.getPhoneNumber());
            }
            else{
                User newUser = new User(user.getUid()).setPhoneNumber(user.getPhoneNumber());
                Repository.getMe().insertToDataBase(newUser);
                bundle.putString(EnumFinals.USER.toString(), new Gson().toJson(newUser));
                Log.d("tagg", "LogIn NULL user: " + returnUser.getUID() + " phone: " + returnUser.getPhoneNumber());
            }
            intent.putExtra("LOGIN", 1);
            intent.putExtra(EnumFinals.BUNDLE.toString(), bundle);
            startActivity(intent);
            finish();
        }

        @Override
        public void getPet(Pet returnPet) {

        }
    };
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
                            Toast.makeText(LogInActivity.this, "Authentication failed.",
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