package com.example.alpha_care.Utils.MySignal;

import android.content.Context;
import android.widget.Toast;

public class MessagesToUser {
    private Context appContext;
    private static MessagesToUser me;
    public MessagesToUser(){}
    private MessagesToUser(Context appContext){
        this.appContext = appContext;
    }
    public static void initMessagesToUser(Context appContext){
        if(me == null)
            me = new MessagesToUser(appContext.getApplicationContext());
    }
    public static MessagesToUser getMe(){return me;}

    public void makeToastMessage(String message) {
        Toast.makeText(appContext, message, Toast.LENGTH_LONG).show();
    }
}
