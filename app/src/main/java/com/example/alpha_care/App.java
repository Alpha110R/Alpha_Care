package com.example.alpha_care;

import android.app.Application;

import com.example.alpha_care.Model.Repository;
import com.example.alpha_care.Utils.MySignal.MessagesToUser;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MessagesToUser.initMessagesToUser(this);
        Repository.initRepository();
    }
}
