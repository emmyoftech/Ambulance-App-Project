package com.example.ambulanceapp.bodypkg;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ambulanceapp.R;
import com.example.ambulanceapp.models.AmbulanceCompanyModel;
import com.example.ambulanceapp.services.DIalogue;


public class CompanyView {

    private bodyMain parentClass;
    private View container;
    private AmbulanceCompanyModel company;
    private DIalogue dIalogue;
    public CompanyView (View view, bodyMain bod, AmbulanceCompanyModel am){
        this.container = view;
        this.parentClass = bod;
        this.company = am;
        this.dIalogue = new DIalogue(parentClass.context, view.findViewById(R.id.comView));
        starter();
        setUpBackBtn();
    }
    private void setUpBackBtn (){
        container.findViewById(R.id.new_amb_back_btn).setOnClickListener(v -> parentClass.switcher(bodyMain.dashboard_page_name, null));
    }
    private void starter (){
        TextView
                compNmeView = container.findViewById(R.id.compNameView),
                descHold = container.findViewById(R.id.desc_hold),
                addressHold = container.findViewById(R.id.addressHold),
                phoneHold = container.findViewById(R.id.phoneHold);
        Button  makeCallButton = container.findViewById(R.id.subbtn);

        compNmeView.setText(company.getCompanyName().toUpperCase());
        descHold.setText(company.getDescription());
        addressHold.setText(company.getAddress());
        phoneHold.setText(company.getPhoneNumber());
        makeCallButton.setOnClickListener(v -> {
            Intent inten = new Intent(Intent.ACTION_DIAL);
            inten.setData(Uri.parse("tel:"+company.getPhoneNumber()));
            parentClass.context.startActivity(inten);
        });
    }
}
