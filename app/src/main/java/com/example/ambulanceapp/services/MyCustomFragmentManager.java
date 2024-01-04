package com.example.ambulanceapp.services;

import android.content.Context;
import android.view.View;

public class MyCustomFragmentManager {
    public View fragView;
    public Context context;
    public AppData appLocalStorage;
    public DIalogue selfDialogue;
    public API_DB api;
    public MyCustomFragmentManager(View view, Context con){
        this.fragView = view;
        this.context = con;
        this.appLocalStorage = new AppData(con);
        this.selfDialogue = new DIalogue(view);
        this.api = new API_DB(view, selfDialogue);
    }
}
