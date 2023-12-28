package com.example.ambulanceapp.authpkg;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ambulanceapp.R;
import com.example.ambulanceapp.services.AppData;
import com.example.ambulanceapp.services.ValidateInput;

import java.util.Locale;

public class authMain {
    private View authView;
    private AppData appData;
    private Button submitButton;
    Context appContext;
    private String navigatedAt = "index";
    private String previouslyAt = "null";
    public authMain(View view, Context con) {
        this.authView = view;
        this.appData = new AppData(con);;
        this.appContext = con;
        this.setListeners();
        this.submitButton = view.findViewById(R.id.submit_button);
    }
    private void setListeners(){
        LinearLayout switchForAdmin = authView.findViewById(R.id.admin_pointer);
        LinearLayout switchForUser = authView.findViewById(R.id.user_pointer);

        switchForAdmin.setOnClickListener(v -> switchFromUserTypeView("admin"));
        switchForUser.setOnClickListener(v -> switchFromUserTypeView("user"));
    }
    private void switchFromUserTypeView(String usertype) {
        LinearLayout container = authView.findViewById(R.id.select_user_view);
        container.setVisibility(View.GONE);
        openFormFor(usertype);
    }
    private void openFormFor(String userType){
        LinearLayout form_con = authView.findViewById(R.id.auth_form);
        form_con.setVisibility(View.VISIBLE);
        hide_show_back_btn(false);
        regStage1(userType);
    }
    private void hide_show_back_btn (boolean hide){
        ImageView back_btn = authView.findViewById(R.id.bck_btn);
        if(hide){
            back_btn.setVisibility(View.GONE);
        }else{
            back_btn.setVisibility(View.VISIBLE);
        }
    }
    private void regStage1 (String userType) {
        TextView title_header = authView.findViewById(R.id.reg_main_title);
        ImageView head_image = authView.findViewById(R.id.regimage);
        EditText fir_name_field = authView.findViewById(R.id.fir_name_holder);
        EditText las_name_field = authView.findViewById(R.id.las_name_holder);
        EditText phn_field = authView.findViewById(R.id.phn_holder);

        title_header.setText("REGISTER AS " + userType.toUpperCase());
        if(userType.equals("user")){
            head_image.setImageResource(R.mipmap.user);
        }else{
            head_image.setImageResource(R.mipmap.admin);
        }
        submitButton.setOnClickListener(v -> {
            if(ValidateInput.isInputEmpty(fir_name_field) && ValidateInput.isInputEmpty(las_name_field) && ValidateInput.isPhoneNumberValid(phn_field)){
                regStage2(ValidateInput.fieldExtractor(fir_name_field), ValidateInput.fieldExtractor(las_name_field), ValidateInput.fieldExtractor(phn_field));
            }else{
                Toast.makeText(appContext, ValidateInput.fieldExtractor(fir_name_field) + " " + "hello"  , Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void regStage2 (String first_name, String last_name, String phone_number){
        LinearLayout regstage2Form = authView.findViewById(R.id.regstage2);
        LinearLayout regstage1Form = authView.findViewById(R.id.regstage1);
        regstage1Form.setVisibility(View.GONE);
        regstage2Form.setVisibility(View.VISIBLE);
        Toast.makeText(appContext, phone_number, Toast.LENGTH_SHORT).show();
    }
}
