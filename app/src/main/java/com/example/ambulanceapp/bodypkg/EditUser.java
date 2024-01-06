package com.example.ambulanceapp.bodypkg;

import android.view.View;
import android.widget.Button;

import com.example.ambulanceapp.R;
import com.example.ambulanceapp.models.UserModel;
import com.example.ambulanceapp.services.API_DB;
import com.example.ambulanceapp.services.CustomListener;
import com.example.ambulanceapp.services.DIalogue;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class EditUser {
    private  View container;
    private bodyMain parentClass;

    private DIalogue dialog;

    public EditUser (View view, bodyMain bod){
        this.container = view;
        this.parentClass = bod;
        this.dialog = new DIalogue(parentClass.context, container.findViewById(R.id.editMain));
        fillNuserNode();
        setUpBackBtn();
    }

    private void setUpBackBtn (){
        container.findViewById(R.id.new_amb_back_btn).setOnClickListener(v -> parentClass.switcher(bodyMain.dashboard_page_name, null));
    }
    private void fillNuserNode (){
        if(parentClass.appLocalStorage.getUserId() != null){
            parentClass.api.getAllUseers(v -> {
                ArrayList<UserModel> users = (ArrayList<UserModel>) v;
                UserModel foundUser = null;
                for(UserModel use: users){
                    if(use.getId().equals(parentClass.appLocalStorage.getUserId())){
                        foundUser = use;
                        break;
                    }
                }
                if(foundUser != null){
                    setup(foundUser);
                }else{
                    dialog.warn("This user cannot be found");
                }
            } , false);
        }else{
            dialog.warn("Oops seems like you are an unauthorized personel", ()-> parentClass.switcher(bodyMain.dashboard_page_name, null));
        }
    }


    private void setup (UserModel user){
        TextInputEditText
                firstnameinput = container.findViewById(R.id.firstnameField),
                lastnameinput = container.findViewById(R.id.lastnameField),
                phonenumberinput = container.findViewById(R.id.Phnone_Field),
                usernameinput = container.findViewById(R.id.userNameField);
        Button subBtn = container.findViewById(R.id.subtn);

        firstnameinput.setText(user.getFirstName());
        lastnameinput.setText(user.getLastName());
        phonenumberinput.setText(user.getPhoneNumber());
        usernameinput.setText(user.getUserName());

        CustomListener.onInput(firstnameinput, v -> {
            TextInputEditText rv = (TextInputEditText) v;
            user.setFirstName(rv.getText().toString());
        });
        CustomListener.onInput(lastnameinput, v -> {
            TextInputEditText rv = (TextInputEditText) v;
            user.setLastName(rv.getText().toString());
        });
        CustomListener.onInput(phonenumberinput, v -> {
            TextInputEditText rv = (TextInputEditText) v;
            user.setPhoneNumber(rv.getText().toString());
        });
        CustomListener.onInput(usernameinput, v -> {
            TextInputEditText rv = (TextInputEditText) v;
            user.setUserName(rv.getText().toString());
        });
        subBtn.setOnClickListener(v -> {
            parentClass.api.createRow(user, API_DB.userTable, ()->{
                dialog.success("Congrats, your data has been successfully updated", ()-> parentClass.switcher(bodyMain.dashboard_page_name, null));
            }, user.getId());
        });
    }

}
