package com.example.ambulanceapp.authpkg;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ambulanceapp.R;
import com.example.ambulanceapp.interfaces.PassedFunction;
import com.example.ambulanceapp.services.ValidateInput;

public class Auth_service {
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
            err_msg.setText(Integer.toString(R.string.usernameErrMsg));
        }
        finalValidation.run();
    }
    public void passwordValidInputEvent(EditText editText, TextView req1, TextView req2, TextView req3, PassedFunction finalValidation){
        int valLevel = ValidateInput.passwordValidLevel(editText.getText().toString());
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
    private void reqPassShow(TextView textview , boolean pass){
        if(pass)
            textview.setTextColor(textview.getResources().getColor(R.color.pal_green));
        else {
            textview.setTextColor(textview.getResources().getColor(R.color.black));
        }
    }
}
