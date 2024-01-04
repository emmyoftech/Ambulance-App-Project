package com.example.ambulanceapp.authpkg;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.*;
import com.example.ambulanceapp.R;
import com.example.ambulanceapp.interfaces.PassedFunction;
import com.example.ambulanceapp.models.SecretKeyModel;
import com.example.ambulanceapp.models.UserModel;
import com.example.ambulanceapp.services.*;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class authMain extends MyCustomFragmentManager {
    private final Button submitButton;
    private final Auth_service service;
    private String userType;
    public authMain(View view, Context con) {
        super(view, con);
        if(appLocalStorage.getifloggedInBefore() == false) appLocalStorage.resetLocalData();
        this.submitButton = view.findViewById(R.id.submit_button);
        this.service = new Auth_service(con);
        if(appLocalStorage.getifloggedInBefore()){
            this.userType = appLocalStorage.getUserType();
            service.goToLogin(view, fragView.findViewById(R.id.select_user_view), ()-> setUpLogin(appLocalStorage.getUserType()));
        }else{
            setListeners();
        }
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
    private void regStage3 (UserModel user){
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
                appLocalStorage.setLoggedInBefore(true);
                appLocalStorage.setUserFirstName(user.getFirstName());
                appLocalStorage.setUserType(user.getUserType());
                selfDialogue.success("we are created");
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
    private void setUpLogin(String userType){
        String userFIrstname = appLocalStorage.getFirstName() == null ? "user": appLocalStorage.getFirstName();
        String welcomepremessage = fragView.getResources().getString(R.string.welcomemessage);
        TextInputEditText usernamefield = fragView.findViewById(R.id.logUsernameHold);
        TextInputEditText userPassfield = fragView.findViewById(R.id.logPassHold);
        TextView fp_text_click = fragView.findViewById(R.id.fp_clickable);
        Button logBtn = fragView.findViewById(R.id.logBtn);
        fp_text_click.setOnClickListener(v -> forgot_password_or_ver_phoneNo_setup(fragView.findViewById(R.id.logFirstPage), "ph"));
        if(userType.equals("user")){
            service.imgTitleChange(fragView, R.mipmap.user, welcomepremessage + " " + userFIrstname);
        }else{
            service.imgTitleChange(fragView, R.mipmap.admin, welcomepremessage + " " + userFIrstname);
        }
        if(appLocalStorage.getUserId() != null){
            api.getUserName(appLocalStorage.getUserId(), v -> {
               UserModel user = (UserModel) v;
               usernamefield.setText(user.getUserName());
            });
        }

        logBtn.setOnClickListener(v -> {
            if(ValidateInput.isInputEmpty(usernamefield)){
                selfDialogue.warn("Username field cannot be left empty");
            }else if(ValidateInput.isInputEmpty(userPassfield)){
                selfDialogue.warn("Password field cannot be empty");
            }else{
                api.getAllUseers(v_data -> {
                    ArrayList<UserModel> users = (ArrayList<UserModel>) v_data;
                    UserModel foundUser = null;
                    for(UserModel user: users){
                        if(user.getUserName().equals(ValidateInput.fieldExtractor(usernamefield))){
                            foundUser = user;
                            break;
                        }
                    }
                    if(foundUser != null){
                        if(foundUser.getPassword().equals(ValidateInput.fieldExtractor(userPassfield))){
                            appLocalStorage.setLoggedInBefore(true);
                            appLocalStorage.setUserFirstName(foundUser.getFirstName());
                            appLocalStorage.setUserType(foundUser.getUserType());
                            appLocalStorage.setUserId(foundUser.getId());
                            selfDialogue.success("You are logged in");
                        }else{
                            selfDialogue.warn("Password invalid");
                        }
                    }else{
                        String pattern = "User by \"{0}\" does not exist";
                        selfDialogue.warn(MessageFormat.format(pattern, ValidateInput.fieldExtractor(usernamefield)));
                    }
                }, false);
            }
        });
    }
    private void forgot_password_or_ver_phoneNo_setup(LinearLayout loginView, String state){
        loginView.setVisibility(View.GONE);
        fragView.findViewById(R.id.phn_sek_hold).setVisibility(View.VISIBLE);
        Button secret_key_sub_button = fragView.findViewById(R.id.secret_key_btn);
        TextInputEditText secretkeyHolder = fragView.findViewById(R.id.secretKeyinputfield);
        TextInputLayout par = fragView.findViewById(R.id.secretKey_getPhn_inputfield);
        if(state.equals("ph")){
            par.setHint(R.string.get_phn_number);
            service.imgTitleChange(fragView, R.drawable.ic_user, fragView.getResources().getString(R.string.get_verify_phone));
            CustomListener.onInput(secretkeyHolder, v -> {
                if(ValidateInput.fieldExtractor(secretkeyHolder).length() == 11){
                    service.button_clickable(secret_key_sub_button);
                }else{
                    service.button_unclickable(secret_key_sub_button);
                }
            });
            secret_key_sub_button.setOnClickListener(v -> {
                selfDialogue.openLoader("Verifying phone number");
                api.getAllPhoneNumbers(ph_list -> {
                    selfDialogue.closeLoader();
                    List<String> phnoneNumbers = (LinkedList<String>) ph_list;
                    String foundNumber = null;
                    for (String number: phnoneNumbers){
                        if(number.equals(ValidateInput.fieldExtractor(secretkeyHolder))){
                            foundNumber = number;
                            break;
                        }
                    }
                    if(foundNumber != null){
                        forgot_password_or_ver_phoneNo_setup(loginView, foundNumber);
                    }else{
                        String pattern = "\"{0}\" no user has this number";
                        String msg= MessageFormat.format(pattern, ValidateInput.fieldExtractor(secretkeyHolder));
                        selfDialogue.warn(msg);
                    }
                });
            });
        }else{
            secretkeyHolder.setText("");
            par.setHint(R.string.get_secret_key);
            service.imgTitleChange(fragView, R.mipmap.ic_pass_lock, fragView.getResources().getString(R.string.title_head_get_secret_key));
            CustomListener.onInput(secretkeyHolder, (v)-> {
                if(ValidateInput.fieldExtractor(secretkeyHolder).length() == 6){
                    service.button_clickable(secret_key_sub_button);
                }else {
                    service.button_unclickable(secret_key_sub_button);
                }
            });
            secret_key_sub_button.setOnClickListener(v -> api.getAllUseers(users -> {
                ArrayList<UserModel> allUsers = (ArrayList<UserModel>) users;
                UserModel foundUser = null;
                for(UserModel user: allUsers){
                    if(user.getPhoneNumber().equals(state)){
                        foundUser = user;
                        break;
                    }
                }
                if(foundUser != null){
                    api.getKey(foundUser.getId(), false, userkey -> {
                        SecretKeyModel userKey = (SecretKeyModel) userkey;
                        if(userKey.getSecret_key().equals(ValidateInput.fieldExtractor(secretkeyHolder))){
                            chPassFunc(userKey.getUser_id(), fragView.findViewById(R.id.phn_sek_hold));
                        }else{
                            selfDialogue.warn("Key is invalid");
                        }
                    });
                }
            }, false));
        }
        service.button_unclickable(secret_key_sub_button);
    }
    private void chPassFunc(String userid, LinearLayout lasCon){
        service.imgTitleChange(fragView, R.mipmap.ic_pass_lock, "enter new password");
        lasCon.setVisibility(View.GONE);
        fragView.findViewById(R.id.ch_pass_Container).setVisibility(View.VISIBLE);
        TextInputEditText passwordHold = fragView.findViewById(R.id.ch_pass_holder);
        TextInputEditText con_passwordHold = fragView.findViewById(R.id.ch_con_pass_holder);
        TextView req1 = fragView.findViewById(R.id.ch_pass_req1),
                req2 = fragView.findViewById(R.id.ch_pass_req2),
                req3 = fragView.findViewById(R.id.ch_pass_req3),
                con_err_msger = fragView.findViewById(R.id.ch_con_pass_err_msg);
        Button subBtn = fragView.findViewById(R.id.ch_pass_btn);

        PassedFunction pass = ()->{
            if(!ValidateInput.isInputEmpty(passwordHold) && !ValidateInput.isInputEmpty(con_passwordHold)){
                if(ValidateInput.isPasswordValid(passwordHold) && ValidateInput.ispasswordConfirmed(passwordHold, con_passwordHold)){
                    service.button_clickable(subBtn);
                }else{
                    service.button_unclickable(subBtn);
                }
            }else{
                service.button_unclickable(subBtn);
            }
        };

        CustomListener.onInput(passwordHold, v -> service.passwordValidInputEvent(passwordHold,req1,req2,req3, () -> pass.run()));
        CustomListener.onInput(con_passwordHold, v -> service.con_passwordValidInputEvent(passwordHold, con_passwordHold, con_err_msger, () -> pass.run()));
        service.button_unclickable(subBtn);
        subBtn.setOnClickListener(v -> {
            try {
                api.changePassword(userid, ValidateInput.fieldExtractor(passwordHold));
                selfDialogue.success("Password changed successfully", ()-> {
                    service.goToLogin(fragView, fragView.findViewById(R.id.logFirstPage), ()-> setUpLogin(this.userType));
                });
            }catch (Exception err){
                selfDialogue.error(err.getMessage());
            }
        });
    }
}
