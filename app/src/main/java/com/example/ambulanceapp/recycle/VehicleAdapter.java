package com.example.ambulanceapp.recycle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ambulanceapp.R;
import com.example.ambulanceapp.bodypkg.bodyMain;
import com.example.ambulanceapp.models.VechicleModel;
import com.example.ambulanceapp.services.API_DB;

import java.util.ArrayList;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleHolder> {

    private ArrayList<VechicleModel> vehicles;
    private Context con;
    private bodyMain parentClass;
    private RecyclerView rec;

    public VehicleAdapter(ArrayList<VechicleModel> vehicles, Context con, bodyMain parentClass, RecyclerView rec) {
        this.vehicles = vehicles;
        this.con = con;
        this.parentClass = parentClass;
        this.rec = rec;
    }

    @NonNull
    @Override
    public VehicleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VehicleHolder(LayoutInflater.from(con).inflate(R.layout.vehicle_cycle_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleHolder holder, int position) {
        holder.veh_name.setText(vehicles.get(position).getVechicleName().toUpperCase());
        holder.compName.setText("Company: ".concat(vehicles.get(position).getCompanyName()));
        holder.edit_btn.setOnClickListener(v -> parentClass.switcheramb(bodyMain.clear_edit_amb_page,vehicles.get(position)));
        holder.delete_btn.setOnClickListener(v -> {
           parentClass.selfDialogue.askquestion("Are you sure you want to delete " + vehicles.get(position).getVechicleName().toUpperCase() + " ?", () -> {
               parentClass.selfDialogue.openLoader("Deleting " + vehicles.get(position).getVechicleName().toUpperCase());
               parentClass.api.deleteRow(API_DB.VehicleTable, vehicles.get(position).getId(), ()->{
                   parentClass.selfDialogue.closeLoader();
                   parentClass.selfDialogue.success(vehicles.get(position).getVechicleName().toUpperCase() + " has been deleted successfully", () -> rec.removeViewAt(position));
               });
           });
        });
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }
}

class VehicleHolder extends RecyclerView.ViewHolder{
    TextView veh_name, compName, edit_btn, delete_btn;
    public VehicleHolder (View view) {
        super(view);
        veh_name = view.findViewById(R.id.vehName);
        compName = view.findViewById(R.id.compName);
        edit_btn = view.findViewById(R.id.edit_btn);
        delete_btn = view.findViewById(R.id.del_btn);
    }
}