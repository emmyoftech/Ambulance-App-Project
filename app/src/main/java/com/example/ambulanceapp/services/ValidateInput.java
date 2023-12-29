package com.example.ambulanceapp.services;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateInput {
    public static String ERR_MSG = "null";
    public static String FAILED_MSG = "null";

    public static String regexAtleastNumber = ".*\\d+.*";
    public static String regexAtleastSymbol = ".*[!@#$%^&*()_+{}\\[\\]:;<>,.?~\\\\-].*";
    public static String regexfullvalidation = "^(?=.*[0-9])(?=.*[^a-zA-Z0-9\\s]).+$";
    public static boolean isInputEmpty (EditText edtext){
        return  edtext.getText().toString().isEmpty();
    }
    public static boolean isPhoneNumberValid (EditText edtext) {
        String inpValue = edtext.getText().toString();
        boolean val = true;
        try{
            if(inpValue.charAt(0) != '0'){
                ValidateInput.ErrMsgHandler(edtext, ": didnt start with 0: " + inpValue, false);
                val = false;
            }else if(inpValue.length() != 11){
                ValidateInput.ErrMsgHandler(edtext, ": is less than 11: " + inpValue, false);
                return false;
            }
        }catch (Exception e){
            ValidateInput.ErrMsgHandler(edtext, e.getMessage(), false);

            val = false;
        }
        return val;
    }
    public static String fieldExtractor (EditText editText){
        return editText.getText().toString();
    }
    public static boolean isUsernameValid (EditText editText){
        String inpValue = editText.getText().toString();
        if(inpValue.length() < 4){
            return false;
        } else if (inpValue.length() > 10) {
            return false;
        }else {
            return true;
        }
    }
    public static boolean isPasswordValid(EditText editText){
        String inpValue = editText.getText().toString();
        Pattern pattern = Pattern.compile(ValidateInput.regexfullvalidation);
        Matcher matcher = pattern.matcher(inpValue);
        if(inpValue.length() < 6){
            return false;
        } else if (!matcher.matches()) {
            ValidateInput.ErrMsgHandler(editText, ": dose'nt match", false);
            return false;
        }else {
            return true;
        }
    }
    public static boolean ispasswordConfirmed (EditText orgPassHolder, EditText conPassHolder){
        return orgPassHolder.getText().toString().equals(conPassHolder.getText().toString());
    }
    private static void ErrMsgHandler (EditText editText , String msg, boolean ifException){
        int edTextName= editText.getId();
        if(ifException){
            ValidateInput.ERR_MSG = Integer.toString(edTextName).concat(msg);
        }else{
            ValidateInput.FAILED_MSG = Integer.toString(edTextName).concat(msg);
        }
    }
}
