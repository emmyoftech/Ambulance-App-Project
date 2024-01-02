package com.example.ambulanceapp.authpkg;

import android.content.Context;
import android.view.View;
import android.widget.*;
import com.example.ambulanceapp.R;
import com.example.ambulanceapp.models.UserModel;
import com.example.ambulanceapp.services.*;
import com.google.android.material.textfield.TextInputEditText;

public class authMain extends MyCustomFragmentManager {
    private final Button submitButton;
    private final Auth_service service;
    private String navigatedAt = "index";
    private String previouslyAt = "null";
    private String userType;
    public authMain(View view, Context con) {
        super(view, con);
        this.submitButton = view.findViewById(R.id.submit_button);
        this.service = new Auth_service(con);
        this.setListeners();
    }
    private void setListeners(){
        LinearLayout switchForAdmin = fragView.findViewById(R.id.admin_pointer);
        LinearLayout switchForUser = fragView.findViewById(R.id.user_pointer);

        switchForAdmin.setOnClickListener(v -> switchFromUserTypeView("admin"));
        switchForUser.setOnClickListener(v -> switchFromUserTypeView("user"));
    }
    private void switchFromUserTypeView(String usertype) {
        LinearLayout container = fragView.findViewById(R.id.select_user_view);
        container.setVisibility(View.GONE);
        this.userType = usertype;
        openFormFor(usertype);
    }
    private void openFormFor(String userType){
        LinearLayout form_con = fragView.findViewById(R.id.auth_form);
        form_con.setVisibility(View.VISIBLE);
        hide_show_back_btn(false);
        regStage1(userType);
    }
    private void hide_show_back_btn (boolean hidden){
        ImageView back_btn = fragView.findViewById(R.id.bck_btn);
        if(hidden){
            back_btn.setVisibility(View.GONE);
        }else{
            back_btn.setVisibility(View.VISIBLE);
        }
    }
    private void regStage1 (String userType) {
        TextView title_header = fragView.findViewById(R.id.reg_main_title);
        ImageView head_image = fragView.findViewById(R.id.regimage);
        EditText fir_name_field = fragView.findViewById(R.id.fir_name_holder);
        EditText las_name_field = fragView.findViewById(R.id.las_name_holder);
        EditText phn_field = fragView.findViewById(R.id.phn_holder);
        TextView fir_err_msg_holder = fragView.findViewById(R.id.fir_err_msg_holder);
        TextView las_err_msg_holder = fragView.findViewById(R.id.las_err_msg_holder);
        TextView phn_err_msg_holder = fragView.findViewById(R.id.phn_err_msg_holder);

        title_header.setText(fragView.getResources().getString(R.string.reg_pre_text).concat(" " + userType.toUpperCase()));
        if(userType.equals("user")){
            head_image.setImageResource(R.mipmap.user);
        }else{
            head_image.setImageResource(R.mipmap.admin);
        }

        CustomListener.onInput(fir_name_field, (e) -> service.reg1Validation(fir_name_field, fir_err_msg_holder, () -> form1InputChecker(fir_name_field, las_name_field, phn_field)));
        CustomListener.onInput(las_name_field, (e) -> service.reg1Validation(las_name_field, las_err_msg_holder, () -> form1InputChecker(fir_name_field, las_name_field, phn_field)));
        CustomListener.onInput(phn_field, (e) -> service.reg1Validation(phn_field, phn_err_msg_holder, () -> form1InputChecker(fir_name_field, las_name_field, phn_field)));


        submitButton.setOnClickListener(v -> regStage2(head_image, title_header, ValidateInput.fieldExtractor(fir_name_field), ValidateInput.fieldExtractor(las_name_field), ValidateInput.fieldExtractor(phn_field)));
        service.button_unclickable(submitButton);
    }
    private void regStage2 (ImageView regImage, TextView headtitle, String first_name, String last_name, String phone_number){
        LinearLayout regstage2Form = fragView.findViewById(R.id.regstage2);
        LinearLayout regstage1Form = fragView.findViewById(R.id.regstage1);

        // textfiels
        EditText usernameHolder = fragView.findViewById(R.id.user_name_holder);
        TextInputEditText passwordHolder = fragView.findViewById(R.id.password_holder);
        TextInputEditText conpassHolder = fragView.findViewById(R.id.con_pass_holder);
        // err msg holders
        TextView userErrMsgHolder = fragView.findViewById(R.id.user_name_err_msg_field);
        TextView conpassErrMsgHolder = fragView.findViewById(R.id.con_pass_err_msg_field);
        // password requirements fields
        TextView passreq1 = fragView.findViewById(R.id.pass_req1);
        TextView passreq2 = fragView.findViewById(R.id.pass_req2);
        TextView passreq3 = fragView.findViewById(R.id.pass_req3);

        regImage.setImageResource(R.drawable.ic_user);
        headtitle.setText(fragView.getResources().getString(R.string.code_log_title_text).toUpperCase());
        regstage1Form.setVisibility(View.GONE);
        regstage2Form.setVisibility(View.VISIBLE);
        service.button_unclickable(submitButton);

        CustomListener.onInput(usernameHolder, (e)-> service.usernameValidInputEvent(usernameHolder, userErrMsgHolder,() -> form2InputChecker(usernameHolder, passwordHolder, conpassHolder)));
        CustomListener.onInput(passwordHolder, (e)-> service.passwordValidInputEvent(passwordHolder,passreq1,passreq2,passreq3, ()-> form2InputChecker(usernameHolder, passwordHolder, conpassHolder)));
        CustomListener.onInput(conpassHolder , (e) -> service.con_passwordValidInputEvent(passwordHolder, conpassHolder, conpassErrMsgHolder, () -> form2InputChecker(usernameHolder, passwordHolder, conpassHolder)));
        submitButton.setOnClickListener(v -> {
            UserModel user = new UserModel(first_name, last_name, phone_number, ValidateInput.fieldExtractor(usernameHolder), ValidateInput.fieldExtractor(passwordHolder), this.userType);
            regStage3(user);
        });
    }
    public void regStage3 (UserModel user){
        LinearLayout form_con = fragView.findViewById(R.id.auth_form);
        LinearLayout secretKeyLayout = fragView.findViewById(R.id.secretKeyContainer);
        Button finishBtn = fragView.findViewById(R.id.finalregbutton);
        TextView keyHolder = fragView.findViewById(R.id.secretKeyHold);
        String secretKey = SecretKey.makeSecretKey();
        form_con.setVisibility(View.GONE);
        secretKeyLayout.setVisibility(View.VISIBLE);
        keyHolder.setText(secretKey);
        hide_show_back_btn(true);
        finishBtn.setOnClickListener(v -> {
            api.createUser(user, secretKey, ()->{
                this.selfDialogue.warn("Success");
            });
        });
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

    private void setLoginListeners(){

    }
}
