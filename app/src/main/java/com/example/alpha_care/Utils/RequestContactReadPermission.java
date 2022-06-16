package com.example.alpha_care.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.alpha_care.CallBacks.CallBack_ContactPermission;

import java.util.HashMap;
import java.util.Map;

public class RequestContactReadPermission {
    private Activity activity;
    private String TAG = "contacts";
    private final String wantPermission = Manifest.permission.READ_CONTACTS;
    private Cursor crContacts;
    private static final int PERMISSION_REQUEST_CODE = 1;


    public RequestContactReadPermission(Activity activity){
        this.activity = activity;
    }
    public String getWantPermission(){
        return wantPermission;
    }

    @SuppressLint("Range")
    public Map<String, String> readContacts() {
        Map<String, String> contacts = new HashMap<>();
        String id, name;
        String order = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
        ContentResolver cr = activity.getContentResolver();
        crContacts = activity.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null,null, null, order);

        while (crContacts.moveToNext()) {
            id = crContacts.getString(crContacts.getColumnIndex(ContactsContract.Contacts._ID));
            name = crContacts.getString(crContacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            if (Integer.parseInt(crContacts.getString(crContacts.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                Cursor crPhones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                + " = ?", new String[]{id}, null);

                Log.d(TAG, "NAME: " + name);

                while (crPhones.moveToNext()) {
                    String phone = crPhones.getString(crPhones
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.d(TAG, "\tPHONE: " + phone);
                    contacts.put(phone, name);
                }
                crPhones.close();
            }
        }
        crContacts.close();
        return contacts;
    }

    private String getPhone() {
        TelephonyManager phoneMgr = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, wantPermission) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        return phoneMgr.getLine1Number();
    }

    public void requestPermission(String permission){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)){
            //Toast.makeText(activity, "Read contacts permission allows us to read your contacts. Please allow it for additional functionality.", Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(activity, new String[]{permission},PERMISSION_REQUEST_CODE);
    }

    public boolean checkPermission(String permission){
        if (Build.VERSION.SDK_INT >= 23) {
            int result = ContextCompat.checkSelfPermission(activity, permission);
            if (result == PackageManager.PERMISSION_GRANTED){
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}
