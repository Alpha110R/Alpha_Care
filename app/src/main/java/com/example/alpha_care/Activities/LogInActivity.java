package com.example.alpha_care.Activities;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alpha_care.Enums.EnumFinals;
import com.example.alpha_care.Objects.User;
import com.example.alpha_care.R;
import com.example.alpha_care.Model.Repository;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        bundle = new Bundle();

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

    public void createNewUserIfNotExist(User returnUser){
        Log.d("tagg", "return user in login: " + returnUser);
        if(returnUser==null) {
            intent = new Intent(LogInActivity.this, SetUserNameActivity.class);

            //Log.d("tagg", "LogIn NULL user: " + returnUser.getUID() + " phone: " + returnUser.getPhoneNumber());
        }
        else
            intent = new Intent(LogInActivity.this, PetsListActivity.class);
        intent.putExtra(EnumFinals.BUNDLE.toString(), bundle);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onResume(){
        super.onResume();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if(user!=null){
            Repository.getMe().getUserByID(this);//Checks if the current user exist or need to create new
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
                            Toast.makeText(LogInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI();
                        }
                    }


                });
    }*/
