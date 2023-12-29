package com.example.ambulanceapp.authpkg;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ambulanceapp.R;
import com.example.ambulanceapp.interfaces.PassedFunction;
import com.example.ambulanceapp.services.ValidateInput;

public class Auth_service {

    Context authContext;

    public Auth_service (Context con){
        this.authContext = con;
    }

    public void reg1Validation (EditText editText, TextView err_msg_holder, PassedFunction function){
        int editextId = editText.getId();
        String editextIdString = editText.getResources().getResourceName(editextId).split("/")[1];

        if(editextIdString.equals("phn_holder")){
            if(ValidateInput.isInputEmpty(editText)){
                err_msg_holder.setText(editText.getResources().getString(R.string.empty_field_msg));
            } else if (!ValidateInput.isPhoneNumberValid(editText)) {
                err_msg_holder.setText(editText.getResources().getString(R.string.invalid_phn_num_text));
            }else {
                err_msg_holder.setText("");
            }
        }else {
            if(ValidateInput.isInputEmpty(editText)){
                err_msg_holder.setText(editText.getResources().getString(R.string.empty_field_msg));
            }else {
                err_msg_holder.setText("");
            }
        }
        function.run();
    }
    public void button_unclickable(Button btn){
        btn.setClickable(false);
        btn.setAlpha(0.5f);
    }
    public void button_clickable(Button btn){
        btn.setClickable(true);
        btn.setAlpha(1.0f);
    }
    public void usernameValidInputEvent (EditText editText, TextView err_msg, PassedFunction finalValidation){
        if(ValidateInput.isUsernameValid(editText)){
            err_msg.setText("");
        }else{
            err_msg.setText(editText.getResources().getString(R.string.usernameErrMsg));
        }
        finalValidation.run();
    }
    public void passwordValidInputEvent(EditText editText, TextView req1, TextView req2, TextView req3, PassedFunction finalValidation){
        int valLevel = ValidateInput.passwordValidLevel(editText.getText().toString());
        req2.setText(Integer.toString(valLevel));
        switch (valLevel){
            case 1:
                reqPassShow(req1, true);
                break;
            case 2:
                reqPassShow(req2, true);
                break;
            case 3:
                reqPassShow(req3, true);
                break;
            default:
                reqPassShow(req1, false);
                reqPassShow(req2, false);
                reqPassShow(req3, false);
                break;
        }
        finalValidation.run();
    }
    public void con_passwordValidInputEvent (EditText orgeditText, EditText editText, TextView err_msg, PassedFunction finalValidation){
        if(ValidateInput.ispasswordConfirmed(orgeditText, editText)){
            err_msg.setText("");
        } else if (ValidateInput.isInputEmpty(editText)) {
            err_msg.setText(editText.getResources().getString(R.string.empty_field_msg));
        }else {
            err_msg.setText(editText.getResources().getString(R.string.invalid_text));
        }
        finalValidation.run();
    }
    private void reqPassShow(TextView textview , boolean pass){
        if(pass)
            textview.setTextColor(textview.getResources().getColor(R.color.pal_green));
        else {
            textview.setTextColor(textview.getResources().getColor(R.color.black));
        }
    }
    public void passwordViewer (EditText passwordHolder, ImageView btn){
        int passwordCurrentState = passwordHolder.getInputType();

        if(passwordCurrentState == InputType.TYPE_TEXT_VARIATION_PASSWORD){
            btn.setImageResource(R.drawable.ic_eye_open);
            passwordHolder.setInputType(InputType.TYPE_CLASS_TEXT);
            Toast.makeText(authContext, "visible", Toast.LENGTH_SHORT).show();
        }else{
            btn.setImageResource(R.drawable.ic_eye_close);
            passwordHolder.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            Toast.makeText(authContext, "not visible", Toast.LENGTH_SHORT).show();
        }
    }
}
