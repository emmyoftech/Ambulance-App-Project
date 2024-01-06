package com.example.ambulanceapp.bodypkg;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ambulanceapp.MainActivity;
import com.example.ambulanceapp.R;
import com.example.ambulanceapp.authpkg.Auth_service;
import com.example.ambulanceapp.models.AmbulanceCompanyModel;
import com.example.ambulanceapp.models.LandmarkModel;
import com.example.ambulanceapp.models.VechicleModel;
import com.example.ambulanceapp.services.API_DB;
import com.example.ambulanceapp.services.DIalogue;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;

public class Dashboard {
    private bodyMain parentClass;
    private String userType;
    private View container;
    private Body_service service;
    private DIalogue dIalogue;
    public Dashboard (View view,  bodyMain body){
        this.parentClass = body;
        this.container = view;
        service = new Body_service(body.context);
        container.setVisibility(View.VISIBLE);
        setlisteners();
        container.findViewById(R.id.SignOut).setOnClickListener(v -> MainActivity.fragmentSwitcher.run("auth"));
    }

    private void setlisteners (){
        TableRow creCom = container.findViewById(R.id.createCompany);
        TableRow edCom = container.findViewById(R.id.editCompany);
        TableRow edProf = container.findViewById(R.id.editProf);
        TableRow edVehicle = container.findViewById(R.id.editVehicle);
        TableRow edLandmark = container.findViewById(R.id.editLandmark);

        if(parentClass.appLocalStorage.getUserType().equals("user")){
            edCom.setVisibility(View.GONE);
            edProf.setVisibility(View.GONE);
            edVehicle.setVisibility(View.GONE);
            edLandmark.setVisibility(View.GONE);
            TextView text = creCom.findViewById(R.id.userclick);
            text.setText("View Ambulance Company");

            service.un_clickable(creCom);
            parentClass.api.getAllRows(API_DB.AmbulaneCompanyTable, (v) -> {
                ArrayList<AmbulanceCompanyModel> amp = (ArrayList<AmbulanceCompanyModel>) v;
                if(amp.size() > 0){
                    creCom.setOnClickListener(s -> parentClass.cycleSwitcher(bodyMain.cycle_page, amp, CyclePage.ArrayTypeAmbulanceCompany));
                    service.clickable(creCom);
                }
            });
        }else{
            service.un_clickable(edCom);
            service.un_clickable(edVehicle);
            service.un_clickable(edLandmark);
            creCom.setOnClickListener(v -> parentClass.switcher(bodyMain.clear_edit_amb_page, Create_Edit_Amb.createState));

            edProf.setOnClickListener(c -> {
                parentClass.switchEdit();
            });

            parentClass.api.getAllRows(API_DB.AmbulaneCompanyTable, (v) -> {
                ArrayList<AmbulanceCompanyModel> amp = (ArrayList<AmbulanceCompanyModel>) v;
                if(amp.size() > 0){
                    edCom.setOnClickListener(s -> parentClass.cycleSwitcher(bodyMain.cycle_page, amp, CyclePage.ArrayTypeAmbulanceCompany));
                    service.clickable(edCom);
                }
            });
            parentClass.api.getAllRows(API_DB.VehicleTable, (v) -> {
                ArrayList<VechicleModel> vehs = (ArrayList<VechicleModel>) v;
                if(vehs.size() > 0){
                    edVehicle.setOnClickListener( veh -> parentClass.cycleSwitcher(bodyMain.cycle_page, vehs, CyclePage.ArrayTypeVehicle));
                    service.clickable(edVehicle);
                }
            });

            parentClass.api.getAllRows(API_DB.LandMarkTable, (v) -> {
                ArrayList<LandmarkModel> lands = (ArrayList<LandmarkModel>) v;
                if(lands.size() > 0){
                    edLandmark.setOnClickListener(vLand -> parentClass.cycleSwitcher(bodyMain.cycle_page, lands, CyclePage.ArrayTypeLandmark));
                    service.clickable(edLandmark);
                }
            });
        }
    }
}
