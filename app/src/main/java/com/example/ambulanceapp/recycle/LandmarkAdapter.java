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
import com.example.ambulanceapp.models.LandmarkModel;
import com.example.ambulanceapp.services.API_DB;

import java.util.ArrayList;
import java.util.List;

public class LandmarkAdapter extends RecyclerView.Adapter<LandmarkViewholder> {

    private ArrayList<LandmarkModel> land;
    private bodyMain parentClass;
    private RecyclerView rec;
    private Context context;

    public LandmarkAdapter(ArrayList<LandmarkModel> land, bodyMain parentClass, RecyclerView rec, Context context) {
        this.land = land;
        this.parentClass = parentClass;
        this.rec = rec;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull LandmarkViewholder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @NonNull
    @Override
    public LandmarkViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LandmarkViewholder(LayoutInflater.from(context).inflate(R.layout.landmark_cycle_view,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LandmarkViewholder holder, int position) {
        holder.landName.setText(land.get(position).getLandmark());
        holder.compName.setText(land.get(position).getCompanyName());
        holder.editName.setOnClickListener(v -> parentClass.switcheramb("edit", land.get(position)));
        holder.del_name.setOnClickListener(v -> parentClass.selfDialogue.askquestion("Are you sure you want to delete " + land.get(position).getLandmark().toUpperCase() + " ?", ()->{
            parentClass.selfDialogue.openLoader("Deleting " + land.get(position).getLandmark().toUpperCase());
            parentClass.api.deleteRow(API_DB.LandMarkTable, land.get(position).getId(), ()->{
               parentClass.selfDialogue.closeLoader();
               parentClass.selfDialogue.success(land.get(position).getLandmark().toUpperCase() + " has been successfully deleted", ()->{
                   rec.removeViewAt(position);
               });
            });
        }));
    }

    @Override
    public int getItemCount() {
        return land.size();
    }
}
class LandmarkViewholder extends RecyclerView.ViewHolder{

    TextView landName, compName, editName, del_name;
    public LandmarkViewholder (View view){
        super(view);
        landName = view.findViewById(R.id.landmarkName);
        compName = view.findViewById(R.id.compName);
        editName = view.findViewById(R.id.edit_btn);
        del_name = view.findViewById(R.id.del_btn);
    }
}