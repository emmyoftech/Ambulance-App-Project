package com.example.ambulanceapp.bodypkg;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ambulanceapp.R;
import com.example.ambulanceapp.models.AmbulanceCompanyModel;
import com.example.ambulanceapp.models.LandmarkModel;
import com.example.ambulanceapp.models.VechicleModel;
import com.example.ambulanceapp.services.API_DB;
import com.example.ambulanceapp.services.CustomListener;
import com.example.ambulanceapp.services.DIalogue;
import com.example.ambulanceapp.services.ValidateInput;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Create_Edit_Amb {
    public static final String editState = "edit";
    public static final String createState = "create";
    private final bodyMain parentClass;
    private final View container;
    private  final String state;
    private final Body_service service;
    private final DIalogue dIalogue;
    private final boolean ifCreating;
    private Button sub_btn;
    private AmbulanceCompanyModel amb;
    private LandmarkModel lan;
    private VechicleModel vehs;
    public Create_Edit_Amb(View view, bodyMain parentClass, String state){
        this.parentClass = parentClass;
        this.container = view;
        this.state = state;
        ifCreating = state.equals(Create_Edit_Amb.createState);
        dIalogue = new DIalogue(parentClass.context, container.findViewById(R.id.create_edit_amb_page));
        this.service = new Body_service(parentClass.context);
        this.amb = new AmbulanceCompanyModel();
        starters();
    }
    public Create_Edit_Amb(View view, bodyMain parentClass, String state, Object comp){
        this.parentClass = parentClass;
        this.container = view;
        this.state = state;
        ifCreating = state.equals(Create_Edit_Amb.createState);
        dIalogue = new DIalogue(parentClass.context, container.findViewById(R.id.create_edit_amb_page));
        this.service = new Body_service(parentClass.context);
        sub_btn = container.findViewById(R.id.mainCreate_Edit_button);
        if(comp instanceof AmbulanceCompanyModel){
            this.amb = (AmbulanceCompanyModel) comp;
            starterForAmbulance();
            setUpBackBtn();
        } else if (comp instanceof VechicleModel) {
            VechicleModel vs = (VechicleModel) comp;
            setupVehicle(vs);
        }else{
            LandmarkModel la = (LandmarkModel) comp;
            setupLandmark(la);
        }
    }

    private void starters(){
        setUpBackBtn();
        titleChanger();
        sub_btn = container.findViewById(R.id.mainCreate_Edit_button);
        subButtonSetup();
        onInputListenersAttachment();
        service.un_clickable(sub_btn);
        sub_btn.setOnClickListener(v -> CreateNewCompany());
    }
    private void setUpBackBtn (){
        container.findViewById(R.id.new_amb_back_btn).setOnClickListener(v -> parentClass.switcher(bodyMain.dashboard_page_name, null));
    }
    private void titleChanger (){
        String title = ifCreating ? "create new ambulance company": "edit company";
        TextView titleHolder =  container.findViewById(R.id.createTitile);
        titleHolder.setText(title);
    }
    private  void subButtonSetup (){
        sub_btn.setText(ifCreating ? "Create Company": "Save Changes");
    }
    private void onInputListenersAttachment(){
        TextInputEditText companyNameHold = container.findViewById(R.id.companyNamefield),
                companyDescHold = container.findViewById(R.id.companyDescField),
                companyPhoneNumber = container.findViewById(R.id.companyPhoneNumber),
                longtitudeHold = container.findViewById(R.id.longtitudeField),
                latitudeHold = container.findViewById(R.id.latitudeField),
                addressHold = container.findViewById(R.id.addressField);

        TableLayout singleVehicleContainer = container.findViewById(R.id.vehiclTable),
            singleLandmarkContainer = container.findViewById(R.id.landmarkTable);


        CustomListener.onInput(companyNameHold, v -> {
            amb.setCompanyName(ValidateInput.fieldExtractor(companyNameHold));
            btnOpener(amb.ifInputsAreValidForAuth());
        });
        CustomListener.onInput(companyDescHold, v -> {
            amb.setDescription(ValidateInput.fieldExtractor(companyDescHold));
            btnOpener(amb.ifInputsAreValidForAuth());
        });
        CustomListener.onInput(companyPhoneNumber, v -> {
            amb.setPhoneNumber(ValidateInput.fieldExtractor(companyPhoneNumber));
            btnOpener(amb.ifInputsAreValidForAuth());
        });
        CustomListener.onInput(longtitudeHold, v -> {
            amb.setLongtitude(ValidateInput.fieldExtractor(longtitudeHold));
            btnOpener(amb.ifInputsAreValidForAuth());
        });
        CustomListener.onInput(latitudeHold, v -> {
            amb.setLatitude(ValidateInput.fieldExtractor(latitudeHold));
            btnOpener(amb.ifInputsAreValidForAuth());
        });
        CustomListener.onInput(addressHold, v -> {
            amb.setAddress(ValidateInput.fieldExtractor(addressHold));
            btnOpener(amb.ifInputsAreValidForAuth());
        });
        container.findViewById(R.id.vehAdder).setOnClickListener(v -> service.inflateVehiclerow(singleVehicleContainer, parentClass.context, "veh"));
        container.findViewById(R.id.lanadder).setOnClickListener(v -> service.inflateVehiclerow(singleLandmarkContainer, parentClass.context, "lan"));
    }
    private ArrayList<VechicleModel> getAllVechiclData (){
        ArrayList<VechicleModel> vehs = new ArrayList<VechicleModel>();
        TableLayout vehicleUiTable = container.findViewById(R.id.vehiclTable);

        for(int i = 0; i < vehicleUiTable.getChildCount(); i++){
            TextInputEditText vehName = (TextInputEditText) vehicleUiTable.getChildAt(i).findViewById(R.id.getVehiclename),
                    vehType = (TextInputEditText) vehicleUiTable.getChildAt(i).findViewById(R.id.getVehicletype);
            if(!ValidateInput.fieldExtractor(vehName).equals("") && !ValidateInput.fieldExtractor(vehType).equals("") ){
                vehs.add(new VechicleModel(ValidateInput.fieldExtractor(vehName),  ValidateInput.fieldExtractor(vehType)));
            }else{
                break;
            }
        }
        return vehs;
    }
    private ArrayList<LandmarkModel> getAllLandmarkData(){
        ArrayList<LandmarkModel> vehs = new ArrayList<LandmarkModel>();
        TableLayout vehicleUiTable = container.findViewById(R.id.landmarkTable);

        for(int i = 0; i < vehicleUiTable.getChildCount(); i++){
            TextInputEditText vehName = (TextInputEditText) vehicleUiTable.getChildAt(i).findViewById(R.id.getLandmark);
            if(!ValidateInput.fieldExtractor(vehName).equals("")){
                vehs.add(new LandmarkModel(ValidateInput.fieldExtractor(vehName)));
            }else{
                break;
            }
        }
        return vehs;
    }
    private void btnOpener (boolean t){
        if(t){
            service.clickable(sub_btn);
        }else{
            service.un_clickable(sub_btn);
        }
    }
    private void CreateNewCompany(){
        if(getAllVechiclData().size() > 0 && getAllLandmarkData().size() > 0 && amb.ifInputsAreValidForAuth()){
            parentClass.api.CreateNewComany(amb, getAllVechiclData(), getAllLandmarkData(), () -> {
                dIalogue.success(amb.getCompanyName() + " was made successfully", ()-> parentClass.switcher(bodyMain.dashboard_page_name, null));
            });
        }else {
            dIalogue.warn("Please check again and make sure all inputs are correct");
        }
    }

    private void starterForAmbulance (){
        LinearLayout LandHolder = container.findViewById(R.id.landmarkuihold), vehicleHolder = container.findViewById(R.id.vehicleuiholder);
        LandHolder.setVisibility(View.GONE);
        vehicleHolder.setVisibility(View.GONE);
        sub_btn.setText("SAVE");

        TextInputEditText companyNameHold = container.findViewById(R.id.companyNamefield),
                companyDescHold = container.findViewById(R.id.companyDescField),
                companyPhoneNumber = container.findViewById(R.id.companyPhoneNumber),
                longtitudeHold = container.findViewById(R.id.longtitudeField),
                latitudeHold = container.findViewById(R.id.latitudeField),
                addressHold = container.findViewById(R.id.addressField);

        companyNameHold.setText(amb.getCompanyName());
        companyDescHold.setText(amb.getDescription());
        companyPhoneNumber.setText(amb.getPhoneNumber());
        longtitudeHold.setText(amb.getLongtitude());
        latitudeHold.setText(amb.getLatitude());
        addressHold.setText(amb.getAddress());

        CustomListener.onInput(companyNameHold, v -> {
            amb.setCompanyName(ValidateInput.fieldExtractor(companyNameHold));
            btnOpener(amb.ifInputsAreValidForAuth());
        });
        CustomListener.onInput(companyDescHold, v -> {
            amb.setDescription(ValidateInput.fieldExtractor(companyDescHold));
            btnOpener(amb.ifInputsAreValidForAuth());
        });
        CustomListener.onInput(companyPhoneNumber, v -> {
            amb.setPhoneNumber(ValidateInput.fieldExtractor(companyPhoneNumber));
            btnOpener(amb.ifInputsAreValidForAuth());
        });
        CustomListener.onInput(longtitudeHold, v -> {
            amb.setLongtitude(ValidateInput.fieldExtractor(longtitudeHold));
            btnOpener(amb.ifInputsAreValidForAuth());
        });
        CustomListener.onInput(latitudeHold, v -> {
            amb.setLatitude(ValidateInput.fieldExtractor(latitudeHold));
            btnOpener(amb.ifInputsAreValidForAuth());
        });
        CustomListener.onInput(addressHold, v -> {
            amb.setAddress(ValidateInput.fieldExtractor(addressHold));
            btnOpener(amb.ifInputsAreValidForAuth());
        });


        sub_btn.setOnClickListener(v -> saveAmb(amb));
    }

    private void saveAmb(AmbulanceCompanyModel am) {
        parentClass.api.createRow(am, API_DB.AmbulaneCompanyTable, ()->{
             dIalogue.success(am.getCompanyName().toUpperCase() + "has been successfully updated", ()-> parentClass.switcher(bodyMain.dashboard_page_name, null));
        }, am.getId());
    }

    private void setupVehicle(VechicleModel vs){
        LinearLayout amdHolder = container.findViewById(R.id.ambHolder),
                landHold = container.findViewById(R.id.landmarkuihold);
        ImageView vehAdd = container.findViewById(R.id.vehAdder);

        TextInputEditText vehName = container.findViewById(R.id.getVehiclename),
                vehType = container.findViewById(R.id.getVehicletype);

        amdHolder.setVisibility(View.GONE);
        landHold.setVisibility(View.GONE);
        vehAdd.setVisibility(View.GONE);
        sub_btn.setText("SAVE");
        vehName.setText(vs.getVechicleName());
        CustomListener.onInput(vehName , v -> vs.setVechicleName(vehName.getText().toString()));
        vehType.setText(vs.getVechicleType());
        CustomListener.onInput(vehType , v -> vs.setVechicleType(vehType.getText().toString()));
        sub_btn.setOnClickListener(v -> saveVehicle(vs));
    }
    private void saveVehicle(VechicleModel veh){
        parentClass.api.createRow(veh, API_DB.VehicleTable, ()->{
            dIalogue.success(veh.getVechicleName().toUpperCase() + "has been successfully updated", ()-> parentClass.switcher(bodyMain.dashboard_page_name, null));
        }, veh.getId());
    }
    private void setupLandmark(LandmarkModel la){
        LinearLayout amdHolder = container.findViewById(R.id.ambHolder),
                lanHold = container.findViewById(R.id.vehicleuiholder);
        ImageView vehAdd = container.findViewById(R.id.lanadder);

        TextInputEditText lanName = container.findViewById(R.id.getLandmark);

        amdHolder.setVisibility(View.GONE);
        lanHold.setVisibility(View.GONE);
        vehAdd.setVisibility(View.GONE);
        sub_btn.setText("SAVE");
        lanName.setText(la.getLandmark());
        CustomListener.onInput(lanName , v -> la.setLandmark(lanName.getText().toString()));
        sub_btn.setOnClickListener(v -> saveLandMark(la));
    }
    private void saveLandMark(LandmarkModel la){
        parentClass.api.createRow(la, API_DB.LandMarkTable, ()->{
            dIalogue.success(la.getLandmark().toUpperCase() + " has been successfully updated", ()-> parentClass.switcher(bodyMain.dashboard_page_name, null));
        }, la.getId());
    }
}
