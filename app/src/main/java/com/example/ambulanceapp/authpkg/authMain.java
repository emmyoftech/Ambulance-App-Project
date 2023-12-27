package com.example.ambulanceapp.authpkg;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ambulanceapp.R;
import com.example.ambulanceapp.services.AppData;
import com.google.android.material.snackbar.Snackbar;

public class authMain {
    private View authView;
    private AppData appData;
    public authMain(View view, Context con) {
        this.authView = view;
        this.appData = new AppData(con);
        myCuriosity();
    }
    private void myCuriosity (){
        EditText edTxt = authView.findViewById(R.id.txtfield);
        edTxt.setText(appData.getUserType());
        Button btn = authView.findViewById(R.id.btns);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appData.setUserType(edTxt.getText().toString());
                System.out.println("we dropped");
            }
        });
    }
}
