package com.example.ambulanceapp.services;


import android.view.View;
import androidx.annotation.NonNull;
import com.example.ambulanceapp.interfaces.PassedFunction;
import com.example.ambulanceapp.interfaces.PassedFunction_object;
import com.example.ambulanceapp.models.AmbulanceCompanyModel;
import com.example.ambulanceapp.models.LandmarkModel;
import com.example.ambulanceapp.models.SecretKeyModel;
import com.example.ambulanceapp.models.UserModel;
import com.example.ambulanceapp.models.VechicleModel;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnSuccessListener;
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
    public static String AmbulaneCompanyTable = "amb_company_table";
    public static String VehicleTable = "vehicle_table";
    public static String LandMarkTable = "landmark_table";
    public API_DB (View view, DIalogue dialog){
        database = FirebaseDatabase.getInstance();
        this.dialog = dialog;
    }
    public void getAllRows (String table ,PassedFunction_object functionObject){
        ArrayList coms = new ArrayList();
        dbRef = FirebaseDatabase.getInstance().getReference(table);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot fetchedData: datasnapshot.getChildren()){
                    Object com;
                    switch (table){
                        case "users": com = fetchedData.getValue(UserModel.class);break;
                        case "secret_table": com = fetchedData.getValue(SecretKeyModel.class); break;
                        case "amb_company_table": com = fetchedData.getValue(AmbulanceCompanyModel.class); break;
                        case "vehicle_table": com = fetchedData.getValue(VechicleModel.class); break;
                        default: com = fetchedData.getValue(LandmarkModel.class);break;
                    }
                    coms.add(com);
                }
                functionObject.run(coms);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.error(error.getDetails());
            }
        });
    }

    public void createRow(Object data, String table, PassedFunction function, String rowId) {
        dialog.openLoader();
        dbRef = database.getReference(table);
        dbRef.child(rowId).setValue(data).addOnCompleteListener(v -> {
            dialog.closeLoader();
            function.run();
        });
    }
    public void deleteRow(String table, String rowid, PassedFunction function){
        dbRef = FirebaseDatabase.getInstance().getReference();
        String delPath = table + "/" + rowid;
        dbRef.child(delPath).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                function.run();
            }
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
    private static int vehhelper = 0;
    private static int lanhelper = 0;
    private static boolean conhelper = false;
    private void reset (){
        API_DB.vehhelper = 0;
        API_DB.lanhelper = 0;
        API_DB.conhelper = false;
    }
    public void CreateNewComany(AmbulanceCompanyModel com, ArrayList<VechicleModel> veh, ArrayList<LandmarkModel> lan, PassedFunction function){
        String secKey = SecretKey.makeUniqueKey();
        com.setId(secKey);
        createRow(com,API_DB.AmbulaneCompanyTable,()->{
            API_DB.conhelper = true;
            for (int i = 0; i < veh.size(); i++) {
                String secKey2 = SecretKey.makeUniqueKey();
                VechicleModel  veeh = veh.get(i);
                veeh.setId(secKey2);
                veeh.setCompanyId(com.getId());
                veeh.setCompanyName(com.getCompanyName());

                createRow(veeh, API_DB.VehicleTable, ()->{
                    if(API_DB.conhelper && API_DB.vehhelper == veh.size() && API_DB.lanhelper == lan.size()) {function.run(); reset();}
                }, secKey2, "Registering " + veeh.getVechicleName());
                API_DB.vehhelper++;
            }

            for(int j = 0; j < lan.size(); j++){
                String secKey3 = SecretKey.makeUniqueKey();
                LandmarkModel landMark = lan.get(j);
                landMark.setId(secKey3);
                landMark.setCompanyId(com.getId());
                landMark.setCompanyName(com.getCompanyName());

                createRow(landMark, API_DB.LandMarkTable, ()->{
                    if(API_DB.conhelper && API_DB.vehhelper == veh.size() && API_DB.lanhelper == lan.size()) {function.run(); reset();}
                }, secKey3, "Registering landmark");
                API_DB.lanhelper++;
            }
            if(API_DB.conhelper && API_DB.vehhelper == veh.size() && API_DB.lanhelper == lan.size()) {function.run(); reset();}
        },secKey, "Registering Company");
    }
}
