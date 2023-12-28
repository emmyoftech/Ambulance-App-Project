package com.example.ambulanceapp.services;

import android.widget.EditText;

public class ValidateInput {
    public static String ERR_MSG = "null";
    public static String FAILED_MSG = "null";
    public static boolean isInputEmpty (EditText edtext){
        return  edtext.getText().toString().isEmpty();
    }
    public static boolean isPhoneNumberValid (EditText edtext) {
        String inpValue = edtext.getText().toString();
        int edTextName= edtext.getId();
        boolean val = true;
        try{
            if(inpValue.charAt(0) != '0'){
                ValidateInput.FAILED_MSG = Integer.toString(edTextName) + ": didnt start with 0: " + inpValue;
                val = false;
            }else if(inpValue.length() < 11){
                ValidateInput.FAILED_MSG = Integer.toString(edTextName) + ": is less than 11: " + inpValue;
                return false;
            }
        }catch (Exception e){
            ValidateInput.ERR_MSG = Integer.toString(edTextName) + ": is less than 11: " + inpValue;
            val = false;
        }
        return val;
    }
    public static String fieldExtractor (EditText editText){
        return editText.getText().toString();
    }
}
