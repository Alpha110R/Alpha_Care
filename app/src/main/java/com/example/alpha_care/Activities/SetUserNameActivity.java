package com.example.alpha_care.Activities;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.example.alpha_care.Enums.EnumFinals;
import com.example.alpha_care.Objects.User;
import com.example.alpha_care.R;
import com.example.alpha_care.Model.Repository;
import com.example.alpha_care.Utils.MySignal.MessagesToUser;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SetUserNameActivity extends AppCompatActivity {
    private MaterialButton setUserName_BTN_submit;
    private TextInputEditText setUserName_EDT_userName;
    private FirebaseUser user;
    private String userName;
    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setusername);
        findViews();
        initializeIntentBundle();

        setUserName_BTN_submit.setOnClickListener(view -> {
            setUserName_BTN_submit.setClickable(false);
            setUserName_BTN_submit.setAlpha(0.4f);
            userName = setUserName_EDT_userName.getText().toString();
            Repository.getMe().getUserByUserName(userName ,this);
        });
    }

    private void findViews() {
        setUserName_BTN_submit = findViewById(R.id.setUserName_BTN_submit);
        setUserName_EDT_userName = findViewById(R.id.setUserName_EDT_userName);
    }

    private void initializeIntentBundle(){
        intent = getIntent();
        bundle = intent.getBundleExtra(EnumFinals.BUNDLE.toString());
        //currentUser = new Gson().fromJson(bundle.getString(EnumFinals.USER.toString()), User.class);
    }

    public void userNameExist(){
        setUserName_EDT_userName.setText("");
        MessagesToUser.getMe().makeToastMessage("This User Name already exist");
        setUserName_BTN_submit.setClickable(true);
        setUserName_BTN_submit.setAlpha(1);
    }

    public void createUser(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        User newUser = new User(user.getUid()).setPhoneNumber(user.getPhoneNumber()).setUserName(userName);
        Repository.getMe().insertToDataBase(newUser);
        MessagesToUser.getMe().makeToastMessage("Great User Name");
        moveToPageWithBundle(PetsListActivity.class);
    }

    public void moveToPageWithBundle(Class activity){
        intent = new Intent(this, activity);
        intent.putExtra(EnumFinals.BUNDLE.toString(),bundle);
        startActivity(intent);
        finish();
    }
}
