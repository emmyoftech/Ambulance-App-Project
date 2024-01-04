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

//    GETTERS
    public String getUserType(){
        return preferences.getString("USER_TYPE", null);
    }
    public boolean getifloggedInBefore (){return preferences.getBoolean("USER_LOOGED_IN_BEFORE", false);}
    public String getFirstName(){return preferences.getString("USER_FIRST_NAME", null);};
    public String getUserId(){return preferences.getString("USER_ID", null);}
//    SETTERS
    public void setLoggedInBefore(boolean islogged){
        editPref.putBoolean("USER_LOOGED_IN_BEFORE", islogged);
        editPref.apply();
    }
    public void setUserType(String state){
        editPref.putString("USER_TYPE", state);
        editPref.apply();
    }
    public void setUserFirstName (String name){
        editPref.putString("USER_FIRST_NAME", name);
        editPref.apply();
    }
    public void setUserId (String id){
        editPref.putString("USER_ID", id);
        editPref.apply();
    }

    public void resetLocalData(){
        setUserId(null);
        setLoggedInBefore(false);
        setUserType(null);
        setUserFirstName(null);
    }
}
