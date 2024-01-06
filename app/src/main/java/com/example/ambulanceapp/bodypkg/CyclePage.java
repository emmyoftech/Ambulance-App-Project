package com.example.ambulanceapp.bodypkg;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ambulanceapp.R;
import com.example.ambulanceapp.models.AmbulanceCompanyModel;
import com.example.ambulanceapp.models.LandmarkModel;
import com.example.ambulanceapp.models.VechicleModel;
import com.example.ambulanceapp.recycle.CompanyAdapter;
import com.example.ambulanceapp.recycle.LandmarkAdapter;
import com.example.ambulanceapp.recycle.VehicleAdapter;
import com.example.ambulanceapp.services.DIalogue;

import java.util.ArrayList;

public class CyclePage {
    public static String fromDashboard = "dashboard";
    public static String frommap = "map";

    public static String ArrayTypeAmbulanceCompany = "amb_company";
    public static String ArrayTypeVehicle = "vehicle";
    public static String ArrayTypeLandmark = "landmark";
    private View container;
    private RecyclerView recycle;
    private bodyMain parentClass;
    private String from;
    private DIalogue dIalogue;

    public CyclePage (View view, bodyMain parent, ArrayList lists, String data_type, String from){
        this.container = view;
        this.from = from;
        this.parentClass = parent;
        recycle = view.findViewById(R.id.cycle_view);
        dIalogue = new DIalogue(parentClass.context, view.findViewById(R.id.cycleContainer));
        switch (data_type){
            case "amb_company": setUpAmbulanceList(lists);
                break;
            case "vehicle": setVehicleList(lists);
                break;
            default: setLandmarkList(lists);
                break;
        }
        setUpBackBtn();
    }

    private void setUpAmbulanceList(ArrayList<AmbulanceCompanyModel> mods){
        recycle.setLayoutManager(new LinearLayoutManager(parentClass.context));
        recycle.setAdapter(new CompanyAdapter(mods, parentClass.context, parentClass, recycle));
    }

    private void setVehicleList (ArrayList<VechicleModel> vehs){
        recycle.setLayoutManager(new LinearLayoutManager(parentClass.context));
        recycle.setAdapter(new VehicleAdapter(vehs, parentClass.context, parentClass, recycle));
    }

    private void setLandmarkList(ArrayList<LandmarkModel> land){
        recycle.setLayoutManager(new LinearLayoutManager(parentClass.context));
        recycle.setAdapter(new LandmarkAdapter(land, parentClass, recycle, parentClass.context));
    }

    private void setUpBackBtn (){
        container.findViewById(R.id.new_amb_back_btn).setOnClickListener(v -> {
            if(from.equals(CyclePage.fromDashboard)){
                parentClass.switcher(bodyMain.dashboard_page_name, null);
            } else if (from.equals(CyclePage.frommap)) {

            } else{
                dIalogue.warn("invalid location");
            }
        });
    }
}
