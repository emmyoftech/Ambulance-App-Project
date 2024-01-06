package com.example.ambulanceapp.bodypkg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ambulanceapp.R;
import com.example.ambulanceapp.interfaces.PassedFunction;
import com.example.ambulanceapp.models.AmbulanceCompanyModel;
import com.example.ambulanceapp.models.UserModel;
import com.example.ambulanceapp.services.MyCustomFragmentManager;

import java.util.ArrayList;

public class bodyMain extends MyCustomFragmentManager {
    private final Body_service service;
    private final RelativeLayout main_body;
    public static String dashboard_page_name = "dashboard";
    public static String clear_edit_amb_page = "create_edit_ambulance";
    public static String cycle_page = "cycle";
    public bodyMain(View view, Context con){
        super(view, con);
        service = new Body_service(con);
        main_body = view.findViewById(R.id.main_body);
        switcher(bodyMain.dashboard_page_name, null);
    }
    public void switcher (String page, String page_items){
        View inflaated_view;
        if(main_body.getChildCount() > 0) main_body.removeViewAt(0);
        switch (page){
            case "dashboard":
                inflaated_view = LayoutInflater.from(this.context).inflate(R.layout.dashboard, null);
                main_body.addView(inflaated_view);
                new Dashboard(inflaated_view, this);
                break;
            case "create_edit_ambulance":
                inflaated_view = LayoutInflater.from(this.context).inflate(R.layout.new_amb_company, null);
                main_body.addView(inflaated_view);
                if(page_items != null) new Create_Edit_Amb(inflaated_view, this, page_items);
                break;
            default:
                this.selfDialogue.error("no page of " + page + " available");
                break;
        }
    }

    public  void cycleSwitcher (String name, ArrayList comp, String dataType){
        View inflated_view;
        if(name.equals(bodyMain.cycle_page)){
            if(main_body.getChildCount() > 0) main_body.removeViewAt(0);
            inflated_view = LayoutInflater.from(this.context).inflate(R.layout.cycle_view, null);
            main_body.addView(inflated_view);
            new CyclePage(inflated_view,this, comp, dataType, CyclePage.fromDashboard);
        }
    }

    public void switcheramb (String page, Object ambulanceCompanyModel){
        View inflaated_view;
        if(main_body.getChildCount() > 0) main_body.removeViewAt(0);
        inflaated_view = LayoutInflater.from(this.context).inflate(R.layout.new_amb_company, null);
        main_body.addView(inflaated_view);
        new Create_Edit_Amb(inflaated_view, this, page, ambulanceCompanyModel);
    }

    public void switchEdit(){
        View inflaated_view;
        if(main_body.getChildCount() > 0) main_body.removeViewAt(0);
        inflaated_view = LayoutInflater.from(this.context).inflate(R.layout.edit_user_profile, null);
        LinearLayout.LayoutParams lay = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        main_body.addView(inflaated_view, lay);
        new EditUser(inflaated_view, this);
    }
    public void switchComView(AmbulanceCompanyModel com){
        View inflaated_view;
        if(main_body.getChildCount() > 0) main_body.removeViewAt(0);
        inflaated_view = LayoutInflater.from(this.context).inflate(R.layout.company_view, null);
        main_body.addView(inflaated_view);
        new CompanyView(inflaated_view, this, com);
    }
}