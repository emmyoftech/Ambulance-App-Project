package com.example.ambulanceapp.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

import java.util.Map;
import java.util.Set;

public class AppData {
    SharedPreferences preferences;
    SharedPreferences.Editor editPref;
    public AppData(Context con){
        preferences = con.getSharedPreferences("AppStore", Context.MODE_PRIVATE);
        editPref = preferences.edit();
    }

    public String getUserType(){
        return preferences.getString("USER_TYPE", null);
    }
    public boolean ifloggedInBefore (){
        return preferences.getBoolean("USER_LOOGED_IN_BEFORE", false);
    }
    public void setLoggedInBefore(boolean islogged){
        editPref.putBoolean("USER_LOOGED_IN_BEFORE", islogged);
        editPref.commit();
    }
    public void setUserType(String state){
        editPref.putString("USER_TYPE", state);
        editPref.apply();
    }
}
