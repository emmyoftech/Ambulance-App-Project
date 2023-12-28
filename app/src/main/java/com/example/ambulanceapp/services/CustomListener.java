package com.example.ambulanceapp.services;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.ambulanceapp.interfaces.PassedFunction_object;

public class CustomListener {
    public static void onInput (EditText editText , PassedFunction_object function){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                function.run(editText);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
