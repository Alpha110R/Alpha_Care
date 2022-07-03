package com.example.alpha_care.Utils;

import java.util.Comparator;

public class UserNameComparator implements Comparator<String> {
    @Override
    public int compare(String t1, String t2) {
        return t1.compareTo(t2);
    }
}
