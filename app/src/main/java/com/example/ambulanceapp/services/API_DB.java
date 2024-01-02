package com.example.ambulanceapp.services;


import android.view.View;

import com.example.ambulanceapp.interfaces.PassedFunction;
import com.example.ambulanceapp.models.SecretKeyModel;
import com.example.ambulanceapp.models.UserModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class API_DB {
    private DIalogue dialog;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    public static String userTable = "users";
    public static String secretKeyTable = "secret_table";
    public API_DB (View view){
        database = FirebaseDatabase.getInstance();
        dialog = new DIalogue(view);
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
}
