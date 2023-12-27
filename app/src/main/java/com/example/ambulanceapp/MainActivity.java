package com.example.ambulanceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;
import android.view.View;

import com.example.ambulanceapp.services.AppData;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    FragmentContainerView parent;
    AppData ap = new AppData(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parent = findViewById(R.id.fragmentContainerView);
        switchFragView("auth");
    }

    public void switchFragView (String fragName){
        switch (fragName){
            case "auth":
                getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, authFragment.newInstance(""))
                .commit();
                break;
            default:
                Snackbar.make(parent , "This is not available: " + fragName, Snackbar.LENGTH_LONG);
        }
    }
}