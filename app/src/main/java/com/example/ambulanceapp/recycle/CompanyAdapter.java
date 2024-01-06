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
import com.example.ambulanceapp.models.AmbulanceCompanyModel;
import com.example.ambulanceapp.services.API_DB;
import com.example.ambulanceapp.services.DIalogue;

import java.util.ArrayList;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyRecycle> {

    private ArrayList<AmbulanceCompanyModel> comps;
    private Context context;
    private bodyMain parentClass;
    private RecyclerView rec;

    public CompanyAdapter(ArrayList<AmbulanceCompanyModel> comps, Context context, bodyMain parentClass, RecyclerView rec) {
        this.comps = comps;
        this.context = context;
        this.parentClass = parentClass;
        this.rec = rec;
    }

    @NonNull
    @Override
    public CompanyRecycle onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CompanyRecycle(LayoutInflater.from(context).inflate(R.layout.comp_cycle_view, parent, false)) ;
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyRecycle holder, int position) {
        holder.title.setText(comps.get(position).getCompanyName());
        holder.subText.setText(comps.get(position).getDescription().substring(0, 25).concat("..."));
        holder.editBtn.setOnClickListener(v -> parentClass.switcheramb("edit", comps.get(position)));
        holder.deleteBtn.setOnClickListener(v -> {
            parentClass.selfDialogue.askquestion("Are you sure you want to delete " + comps.get(position).getCompanyName().toUpperCase() + " ?", ()->{
                parentClass.selfDialogue.openLoader("Deleting " + comps.get(position).getCompanyName().toUpperCase());
                parentClass.api.deleteRow(API_DB.AmbulaneCompanyTable, comps.get(position).getId(), ()->{
                    parentClass.selfDialogue.closeLoader();
                    parentClass.selfDialogue.success(comps.get(position).getId().toUpperCase() + " has been successfully deleted", () -> rec.removeViewAt(position));
                });
            });
        });
    }

    @Override
    public int getItemCount() {
        return comps.size();
    }
}

class CompanyRecycle extends RecyclerView.ViewHolder {

    TextView title, subText, editBtn, deleteBtn;

    public CompanyRecycle (View com_cycle_view) {
        super(com_cycle_view);
        title = com_cycle_view.findViewById(R.id.title);
        subText = com_cycle_view.findViewById(R.id.subText);
        editBtn = com_cycle_view.findViewById(R.id.edit_btn);
        deleteBtn = com_cycle_view.findViewById(R.id.del_btn);

    }
}