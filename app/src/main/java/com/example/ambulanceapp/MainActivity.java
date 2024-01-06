package com.example.ambulanceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import android.os.Bundle;
import com.example.ambulanceapp.interfaces.PassedFunction_string;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    FragmentContainerView parent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parent = findViewById(R.id.fragmentContainerView);
        MainActivity.fragmentSwitcher.run("admin");
    }
        {
            MainActivity.func(this::switchFragView);
        }
    public static void func (PassedFunction_string function){
        MainActivity.fragmentSwitcher = function;
    }
    public static PassedFunction_string fragmentSwitcher = null;

    public void switchFragView (String fragName){
        switch (fragName){
            case "auth":
                getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, authFragment.newInstance(""))
                .commit();
                break;
            case "admin":
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, bodyFragment.newInstance("" , ""))
                        .commit();
                break;
            default:
                Snackbar.make(parent , "This is not available: " + fragName, Snackbar.LENGTH_LONG).show();
                break;
        }
    }
}