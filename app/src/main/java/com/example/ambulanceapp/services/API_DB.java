package com.example.ambulanceapp.services;


import android.view.View;
import androidx.annotation.NonNull;
import com.example.ambulanceapp.interfaces.PassedFunction;
import com.example.ambulanceapp.interfaces.PassedFunction_object;
import com.example.ambulanceapp.models.SecretKeyModel;
import com.example.ambulanceapp.models.UserModel;
import com.google.android.gms.common.api.Api;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class API_DB {
    private DIalogue dialog;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    public static String userTable = "users";
    public static String secretKeyTable = "secret_table";
    public API_DB (View view, DIalogue dialog){
        database = FirebaseDatabase.getInstance();
        this.dialog = dialog;
    }
    private void createRow(Object data, String table, PassedFunction function, String rowId) {
        dialog.openLoader();
        dbRef = database.getReference(table);
        dbRef.child(rowId).setValue(data).addOnCompleteListener(v -> {
            dialog.closeLoader();
            function.run();
        });
    }
    private void createRow(Object data, String table, PassedFunction function, String rowId, String loadText) {
        dialog.openLoader(loadText);
        dbRef = database.getReference(table);
        dbRef.child(rowId).setValue(data).addOnCompleteListener(v -> {
            dialog.closeLoader();
            function.run();
        });
    }
//    IMPLEMENTATONS

    public void createUser (UserModel user, String userSecretKey, PassedFunction whenSuccessful){
        String id = SecretKey.makeUniqueKey();
        user.setId(id);
        createRow(user, API_DB.userTable, ()-> registerSecretKey(userSecretKey, id, whenSuccessful), id, "creating user...");
    }
    private void registerSecretKey(String key, String userid, PassedFunction function){
        String id = SecretKey.makeUniqueKey();
        SecretKeyModel keyModel = new SecretKeyModel(id, userid, key);
        createRow(keyModel, API_DB.secretKeyTable, function, id, "registering secret key...");
    }
    public void getUserName (String userid, PassedFunction_object functionObject){
        dbRef = database.getReference(API_DB.userTable);
        dbRef.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel user = snapshot.getValue(UserModel.class);
                functionObject.run(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.error(error.getDetails());
            }
        });
    }
    public void getAllUseers(PassedFunction_object functionObject, boolean silent){
        ArrayList datas = new ArrayList();
        dbRef = FirebaseDatabase.getInstance().getReference(API_DB.userTable);
        if(!silent) dialog.openLoader("Authenticating...");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot: datasnapshot.getChildren()){
                    UserModel user = snapshot.getValue(UserModel.class);
                    datas.add(user);
                }
                dialog.closeLoader();
                functionObject.run(datas);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.error(error.getDetails());
            }
        });
    }
    public void getAllPhoneNumbers (PassedFunction_object functionObject){
        List<String> phoneNumbers = new LinkedList<String>();
        dbRef = FirebaseDatabase.getInstance().getReference(API_DB.userTable);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    UserModel user = snapshot.getValue(UserModel.class);
                    phoneNumbers.add(user.getPhoneNumber());
                }
                functionObject.run(phoneNumbers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.error(error.getDetails());
            }
        });
    }
    public void getKey (String userId, boolean silent, PassedFunction_object functionObject){
        dbRef = FirebaseDatabase.getInstance().getReference(API_DB.secretKeyTable);
        if(silent) dialog.openLoader("Authenticating key..");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshots) {
                SecretKeyModel key = null;
                for(DataSnapshot snapshot: snapshots.getChildren()){
                    SecretKeyModel userkey = snapshot.getValue(SecretKeyModel.class);
                    if(userkey.getUser_id().equals(userId)){
                        key = userkey;
                        break;
                    }
                }
                functionObject.run(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.error(error.getDetails());
            }
        });
    }
    public void changePassword (String user, String pass){
        dbRef = database.getReference(API_DB.userTable);
        DatabaseReference table = dbRef.child(user);
        table.child("password").setValue(pass);
    }
}
