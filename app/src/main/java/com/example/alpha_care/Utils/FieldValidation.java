package com.example.alpha_care.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldValidation {

    /**
     * [A-Za-z]+([ '-][a-zA-Z])
     * @param name String
     * @return Boolean
     */
    public static Boolean isValidName(String name){
        return name.matches( "[A-Za-z]+([ '-][a-zA-Z]+)*" );
    }

    public static boolean iaAgeValid(int age){
        return age >= 0 && age < 25;
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\"
                + ".[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\" + ".)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public boolean isValidPassword(String pass){
        if(pass.length() <= 18 && pass.length() >= 5)
            return true;
        return false;
    }

    public boolean isValidPhoneNumber(String phoneNumber){
        Pattern p = Pattern.compile("^\\d{10}$");
        Matcher m = p.matcher(phoneNumber);
        return (m.matches());
    }
}
