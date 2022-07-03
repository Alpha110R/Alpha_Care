package com.example.alpha_care.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alpha_care.AdaptersToRecycleView.AddContactCardAdapter;
import com.example.alpha_care.CallBacks.CallBack_UserNameContactCard;
import com.example.alpha_care.Enums.EnumFinals;
import com.example.alpha_care.R;
import com.example.alpha_care.Repository;
import com.example.alpha_care.Utils.UserNameComparator;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddContactToPetActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AddContactCardAdapter addContactCardAdapter;
    private List<String> userNameListFromDB;
    private TextInputEditText addContactToPet_EDT_userName;
    private Intent intent;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontacttopet);
        findViews();
        userNameListFromDB = new ArrayList<>();
        Repository.getMe().getAllUserName(this);
        restartPetCardAdapter();
        initializeIntentBundle();
        addContactToPet_EDT_userName.addTextChangedListener(new TextWatcher() {

            @SuppressLint("NotifyDataSetChanged")
            public void afterTextChanged(Editable s) {
                if(s.length() == 0){
                    Collections.sort(userNameListFromDB, new UserNameComparator());
                    addContactCardAdapter.setUserNameList(userNameListFromDB);
                    addContactCardAdapter.notifyDataSetChanged();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @SuppressLint("NotifyDataSetChanged")
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                addContactCardAdapter.setUserNameList(getListContactBySearch(s.toString()));
                addContactCardAdapter.notifyDataSetChanged();
            }
        });


    }

    private void findViews() {
        addContactToPet_EDT_userName = findViewById(R.id.addContactToPet_EDT_userName);
        recyclerView = findViewById(R.id.addContactToPet_LST_userNames);
    }

    private void restartPetCardAdapter(){
        addContactCardAdapter = new AddContactCardAdapter(this);
        addContactCardAdapter.setUserNameList(userNameListFromDB).setCallBack_UserNameContactCard(callBack_userNameContactCard);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(addContactCardAdapter);
    }

    private void initializeIntentBundle() {
        intent = getIntent();
        bundle = intent.getBundleExtra(EnumFinals.BUNDLE.toString());
    }

    public List<String> getListContactBySearch(String search){
        List<String> newUserNameList = new ArrayList<>();
        for(int i=0 ; i< userNameListFromDB.size(); i++){
            if(userNameListFromDB.get(i).startsWith(search))
                newUserNameList.add(userNameListFromDB.get(i));
        }
        return newUserNameList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addUserNameToList(String userName){
        userNameListFromDB.add(userName);
        addContactCardAdapter.notifyDataSetChanged();
    }


    CallBack_UserNameContactCard callBack_userNameContactCard = new CallBack_UserNameContactCard() {
        @Override
        public void clicked(String userName) {
            Log.d("tagg", "user name clicked: " + userName);
        }
    };
    private void moveToPageWithBundle(Class activity){
        intent = new Intent(this, activity);
        intent.putExtra(EnumFinals.BUNDLE.toString(),bundle);
        startActivity(intent);
        finish();
    }
}
