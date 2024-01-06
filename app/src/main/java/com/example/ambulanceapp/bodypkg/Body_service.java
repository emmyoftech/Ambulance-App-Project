package com.example.ambulanceapp.bodypkg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.ambulanceapp.R;

public class Body_service {
    Context con;
    public Body_service(Context con){
        this.con = con;
    }
    public void clickable (View view){
        view.setClickable(true);
        view.setAlpha(1.0f);
    }
    public void un_clickable (View view){
        view.setClickable(false);
        view.setAlpha(0.5f);
    }
    public void inflateVehiclerow (TableLayout con, Context ccon, String veh_lan){
        int id = veh_lan.equals("veh") ? R.layout.vehicle_partial: R.layout.landmark_partials;
        View tbRow = LayoutInflater.from(ccon).inflate(id , null);
        ImageView img = veh_lan.equals("veh") ? tbRow.findViewById(R.id.reVechicle) : tbRow.findViewById(R.id.mainCreate_Edit_btn);
        img.setOnClickListener(v -> con.removeView(tbRow));
        con.addView(tbRow);
    }
}
