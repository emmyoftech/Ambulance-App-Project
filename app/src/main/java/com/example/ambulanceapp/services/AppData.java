package com.example.ambulanceapp.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppData {
    SharedPreferences preferences;
    SharedPreferences.Editor editPref;
    public AppData(Context con){
        preferences = PreferenceManager.getDefaultSharedPreferences(con);
        editPref = preferences.edit();
    }

    public String getUserType(){
        return preferences.getString("USER_TYPE", null);
    }

    public void setAppState(String state){
        editPref.putString("USER_TYPE", "neutral");
    }
}
