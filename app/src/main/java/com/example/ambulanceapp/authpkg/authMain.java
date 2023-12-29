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
import com.example.ambulanceapp.services.CustomListener;
import com.example.ambulanceapp.services.ValidateInput;

public class authMain {
    private final View authView;
    private final AppData appData;
    private final Button submitButton;
    private final Context appContext;
    private final Auth_service service;
    private String navigatedAt = "index";
    private String previouslyAt = "null";
    private String userType;
    public authMain(View view, Context con) {
        this.authView = view;
        this.appData = new AppData(con);
        this.appContext = con;
        this.submitButton = view.findViewById(R.id.submit_button);
        this.service = new Auth_service(con);
        this.setListeners();
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
        this.userType = usertype;
        openFormFor(usertype);
    }
    private void openFormFor(String userType){
        LinearLayout form_con = authView.findViewById(R.id.auth_form);
        form_con.setVisibility(View.VISIBLE);
        hide_show_back_btn(false);
        regStage1(userType);
    }
    private void hide_show_back_btn (boolean hidden){
        ImageView back_btn = authView.findViewById(R.id.bck_btn);
        if(hidden){
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
        TextView fir_err_msg_holder = authView.findViewById(R.id.fir_err_msg_holder);
        TextView las_err_msg_holder = authView.findViewById(R.id.las_err_msg_holder);
        TextView phn_err_msg_holder = authView.findViewById(R.id.phn_err_msg_holder);

        title_header.setText(authView.getResources().getString(R.string.reg_pre_text).concat(" " + userType.toUpperCase()));
        if(userType.equals("user")){
            head_image.setImageResource(R.mipmap.user);
        }else{
            head_image.setImageResource(R.mipmap.admin);
        }

        CustomListener.onInput(fir_name_field, (e) -> service.reg1Validation(fir_name_field, fir_err_msg_holder, () -> form1InputChecker(fir_name_field, las_name_field, phn_field)));
        CustomListener.onInput(las_name_field, (e) -> service.reg1Validation(las_name_field, las_err_msg_holder, () -> form1InputChecker(fir_name_field, las_name_field, phn_field)));
        CustomListener.onInput(phn_field, (e) -> service.reg1Validation(phn_field, phn_err_msg_holder, () -> form1InputChecker(fir_name_field, las_name_field, phn_field)));

        submitButton.setOnClickListener(v -> {
            if(!ValidateInput.isInputEmpty(fir_name_field) && !ValidateInput.isInputEmpty(las_name_field) && ValidateInput.isPhoneNumberValid(phn_field)){
                regStage2(head_image, title_header, ValidateInput.fieldExtractor(fir_name_field), ValidateInput.fieldExtractor(las_name_field), ValidateInput.fieldExtractor(phn_field));
            }else{
                // TODO: Add proper error dialog model
                Toast.makeText(appContext, "Something went wrong" , Toast.LENGTH_SHORT).show();
            }
        });
        service.button_unclickable(submitButton);
    }
    private void regStage2 (ImageView regImage, TextView headtitle, String first_name, String last_name, String phone_number){
        LinearLayout regstage2Form = authView.findViewById(R.id.regstage2);
        LinearLayout regstage1Form = authView.findViewById(R.id.regstage1);

        // textfiels
        EditText usernameHolder = authView.findViewById(R.id.user_name_holder);
        EditText passwordHolder = authView.findViewById(R.id.password_holder);
        EditText conpassHolder = authView.findViewById(R.id.con_pass_holder);
        // err msg holders
        TextView userErrMsgHolder = authView.findViewById(R.id.user_name_err_msg_field);
        TextView conpassErrMsgHolder = authView.findViewById(R.id.con_pass_err_msg_field);
        // img buttons
//        ImageView passwordViewer = authView.findViewById(R.id.pass_viewer);
        ImageView conpassViewer = authView.findViewById(R.id.con_pass_viewer);
        // password requirements fields
        TextView passreq1 = authView.findViewById(R.id.pass_req1);
        TextView passreq2 = authView.findViewById(R.id.pass_req2);
        TextView passreq3 = authView.findViewById(R.id.pass_req3);

        regImage.setImageResource(R.drawable.ic_user);
        headtitle.setText(authView.getResources().getString(R.string.code_log_title_text).toUpperCase());
        regstage1Form.setVisibility(View.GONE);
        regstage2Form.setVisibility(View.VISIBLE);
        service.button_unclickable(submitButton);

        passwordViewer.setOnClickListener(v -> service.passwordViewer(passwordHolder, passwordViewer));
        conpassViewer.setOnClickListener(v -> service.passwordViewer(conpassHolder, conpassViewer));
        CustomListener.onInput(usernameHolder, (e)-> service.usernameValidInputEvent(usernameHolder, userErrMsgHolder,() -> form2InputChecker(usernameHolder, passwordHolder, conpassHolder)));
        CustomListener.onInput(passwordHolder, (e)-> service.passwordValidInputEvent(passwordHolder,passreq1,passreq2,passreq3, ()-> form2InputChecker(usernameHolder, passwordHolder, conpassHolder)));
        CustomListener.onInput(conpassHolder , (e) -> service.con_passwordValidInputEvent(passwordHolder, conpassHolder, conpassErrMsgHolder, () -> form2InputChecker(usernameHolder, passwordHolder, conpassHolder)));
    }
    private void form2InputChecker (EditText username_view, EditText password_view, EditText con_password_view){
        String password = password_view.getText().toString(),
                con_password = con_password_view.getText().toString();
        boolean usernameOk = ValidateInput.isUsernameValid(username_view)  && !ValidateInput.isInputEmpty(username_view);
        boolean passwordOk = ValidateInput.isPasswordValid(password_view) && !ValidateInput.isInputEmpty(password_view);
        boolean con_passwordOk = !ValidateInput.isInputEmpty(con_password_view) && password.equals(con_password);
        boolean isOk = usernameOk && passwordOk && con_passwordOk;

        mainSubOpener(isOk);
    }
    public void form1InputChecker (EditText first_name, EditText last_name, EditText phn_num){
        boolean isFirstNameValid = ValidateInput.isInputEmpty(first_name),
            isLastNameValid = ValidateInput.isInputEmpty(last_name),
            isPhoneNumberValid = ValidateInput.isPhoneNumberValid(phn_num),
            isOk = !isFirstNameValid && !isLastNameValid && isPhoneNumberValid;

        if(isOk){
            service.button_clickable(submitButton);
        }else{
            service.button_unclickable(submitButton);
        }
    }
    private void mainSubOpener(boolean isValid){
        if(isValid){
            service.button_clickable(submitButton);
        }else{
            service.button_unclickable(submitButton);
        }
    }
}
