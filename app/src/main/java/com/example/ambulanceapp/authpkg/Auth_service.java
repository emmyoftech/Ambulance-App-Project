package com.example.ambulanceapp.authpkg;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.ambulanceapp.R;
import com.example.ambulanceapp.interfaces.PassedFunction;
import com.example.ambulanceapp.models.UserModel;
import com.example.ambulanceapp.services.API_DB;
import com.example.ambulanceapp.services.ValidateInput;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public void passwordValidInputEvent(@NonNull TextInputEditText editText, TextView req1, TextView req2, TextView req3, PassedFunction finalValidation){
        String pass = editText.getText().toString();

        Pattern patternNum = Pattern.compile(ValidateInput.regexAtleastNumber),
                patternSym = Pattern.compile(ValidateInput.regexAtleastSymbol);
        Matcher matcherNum = patternNum.matcher(pass),
                matcherSym = patternSym.matcher(pass);

        if(pass.length() >= 6){
            reqPassShow(req1, true);
        }else{
            reqPassShow(req1, false);
        }

        if(matcherNum.matches()){
            reqPassShow(req2, true);
        }else{
            reqPassShow(req2, false);
        }

        if(matcherSym.matches()){
            reqPassShow(req3, true);
        }else{
            reqPassShow(req3, false);
        }

        finalValidation.run();
    }
    public void con_passwordValidInputEvent (TextInputEditText orgeditText, TextInputEditText editText, TextView err_msg, PassedFunction finalValidation){
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

    public void imgTitleChange (View view, int imageId, String title){
        ImageView img = view.findViewById(R.id.logimage);
        TextView textHolder = view.findViewById(R.id.logTitleTexe);

        img.setImageResource(imageId);
        textHolder.setText(title);
    }
    public void goToLogin (View view, LinearLayout currentPage, PassedFunction function){
        LinearLayout loginPage = view.findViewById(R.id.loginPage);
        currentPage.setVisibility(View.GONE);
        loginPage.setVisibility(View.VISIBLE);
        resetLogin(view);
        function.run();
    }
    private void resetLogin(View view){
        LinearLayout sec = view.findViewById(R.id.phn_sek_hold),
                     ch_pass_Con = view.findViewById(R.id.ch_pass_Container),
                     logFirestPage = view.findViewById(R.id.logFirstPage);
        sec.setVisibility(View.GONE);
        ch_pass_Con.setVisibility(View.GONE);
        logFirestPage.setVisibility(View.VISIBLE);

    }
}
