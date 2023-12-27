package com.example.ambulanceapp.authpkg;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ambulanceapp.R;
import com.example.ambulanceapp.services.AppData;

public class authMain {
    private View authView;
    private AppData appData;
    public authMain(View view) {
        this.authView = view;
        this.appData = appData;
    }
    private void myCuriosity (){
        EditText edTxt = authView.findViewById(R.id.txtfield);
        edTxt.setText("hello");
    }
}
