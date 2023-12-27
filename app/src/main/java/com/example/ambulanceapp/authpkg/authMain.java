package com.example.ambulanceapp.authpkg;

import android.content.Context;
import android.view.View;
import com.example.ambulanceapp.services.AppData;

public class authMain {
    private View authView;
    private AppData appData;
    public authMain(View view, Context con) {
        this.authView = view;
        this.appData = new AppData(con);
    }

}
