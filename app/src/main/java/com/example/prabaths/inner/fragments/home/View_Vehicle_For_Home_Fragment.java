package com.example.prabaths.inner.fragments.home;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.prabaths.Data.DAO.InstantDataDAO;
import com.example.prabaths.Data.DAO.VehicleDAO;
import com.example.prabaths.Fragments.Home_Fragment;
import com.example.prabaths.ImageHandling.CopyImage;
import com.example.prabaths.Server.GetFromDB;
import com.example.prabaths.TravelBuddy.MainActivity;
import com.example.prabaths.TravelBuddy.R;
import com.example.prabaths.VO.InstantData;
import com.example.prabaths.VO.Vehicle;
import com.example.prabaths.inner.fragments.home.Log_Fragment;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;


/**
 * Created by prabath s on 3/31/2016.
 */
public class View_Vehicle_For_Home_Fragment extends Fragment {

    private MainActivity mainActivity;
    static Spinner spinner;
    TextView regNoTxt;
    TextView modelTxt;
    TextView brandTxt;
    TextView lastMileageTxt;
    TextView typeTxt;
    String userName;
    private static Context context;
    private VehicleDAO vehicleDAO1;
    ImageView vehiclePicImView;
    private InstantDataDAO instantDataDAO;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.view_vehicle_for_home,container,false);
        userName=this.getArguments().getString("u");
        context=getContext();
        //Toast.makeText(getContext(),"user name:"+userName,Toast.LENGTH_LONG).show();
        regNoTxt=(TextView) v.findViewById(R.id.regNoTxt);
        modelTxt=(TextView) v.findViewById(R.id.modelTxt);
        brandTxt=(TextView) v.findViewById(R.id.brandTxt);
        typeTxt=(TextView) v.findViewById(R.id.typeTxt);
        lastMileageTxt=(TextView) v.findViewById(R.id.lastMileageTxt);
        spinner=(Spinner)v.findViewById(R.id.spinner);
        vehicleDAO1=new VehicleDAO(getContext());
        vehiclePicImView=(ImageView) v.findViewById(R.id.vehiclePicImView);
        instantDataDAO=new InstantDataDAO(context);
        GetFromDB getFromDB=new GetFromDB(context);

        createSpinner(userName);


        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        Home_Fragment.regNo = spinner.getSelectedItem().toString();
                        updateUi(spinner.getSelectedItem().toString(), userName);
                        Vehicle vehicle=vehicleDAO1.getVehicleByRegNo(spinner.getSelectedItem().toString(),MainActivity.userName);
                        Home_Fragment.model=vehicle.getModel();
                        Home_Fragment.type_of_vechicle=vehicle.getType();
                        Log_Fragment.clearLog();
                        Log_Fragment.updateLog();


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        return v;
    }

    /**
     * This method changes the selected item of the spinner
     */
    public static void changeSpinner(){

        VehicleDAO vehicleDAO= new VehicleDAO(context);
        ArrayList<Vehicle> vehicles=vehicleDAO.getVehicleByUserID(MainActivity.userName);
        ArrayList<String> vehicleNames=new ArrayList<>();

        for(int i=0;i<vehicles.size();i++){
            vehicleNames.add(vehicles.get(i).getRegNo());
        }
        if(vehicleNames.size()>0) {
            Home_Fragment.regNo = vehicleNames.get(0);
            Home_Fragment.model=vehicles.get(0).getModel();
            Home_Fragment.type_of_vechicle=vehicles.get(0).getType();
            Log_Fragment.clearLog();
            Log_Fragment.updateLog();
        }
        else{Home_Fragment.regNo="";}

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (context, android.R.layout.simple_spinner_item,vehicleNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    /**
     * This method sets up the user interface by updating its fields
     */
    public void setUpUI(){
        Vehicle vehicle=vehicleDAO1.getVehicleByRegNo(Home_Fragment.regNo,MainActivity.userName);
        InstantData instantData=null;
        try {
            instantData=instantDataDAO.getInstantDataByUserName();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        regNoTxt.setText(vehicle.getRegNo());
        modelTxt.setText(vehicle.getModel());
        brandTxt.setText(vehicle.getBrand());
        typeTxt.setText(vehicle.getType());
        if(instantData!=null) {
            lastMileageTxt.setText(String.valueOf(instantData.getOdometerReading()));
        }
        else{lastMileageTxt.setText("Not given");}
        if(!vehicle.getImageURI().equals("")){
            CopyImage ci=new CopyImage();
            Uri uri= Uri.parse(vehicle.getImageURI());
            String imPath=ci.getPath(uri);
            File imFile=new File(imPath);
            if(!imPath.equals("")){
                if(imFile.exists()) {
                    vehiclePicImView.setImageURI(uri);
                }
                else{
                    vehiclePicImView.setImageResource(R.drawable.car);
                }
            }
            else{
                vehiclePicImView.setImageResource(R.drawable.person);
            }
        }

    }

    /**
     * This method update the user interface by updating its fields
     */
    public void updateUi(String regNo,String userId){
        Vehicle vehicle=vehicleDAO1.getVehicleByRegNo(regNo,userId);
        InstantData instantData=null;
        try {
            instantData=instantDataDAO.getInstantDataByUserName();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        regNoTxt.setText(vehicle.getRegNo());
        modelTxt.setText(vehicle.getModel());
        brandTxt.setText(vehicle.getBrand());
        typeTxt.setText(vehicle.getType());
        if(instantData!=null) {
            if(instantData.getOdometerReading()==0){lastMileageTxt.setText("Not Given");}
            else{lastMileageTxt.setText(String.valueOf(instantData.getOdometerReading()));}
        }
        else{lastMileageTxt.setText("Not given");}
        if(!vehicle.getImageURI().equals("")){
            CopyImage ci=new CopyImage();
            Uri uri= Uri.parse(vehicle.getImageURI());
            String imPath=ci.getPath(uri);
            File imFile=new File(imPath);
            if(!imPath.equals("")){
                if(imFile.exists()) {
                    vehiclePicImView.setImageURI(uri);
                }
                else{
                    vehiclePicImView.setImageResource(R.drawable.car);
                }
            }
            else{
                vehiclePicImView.setImageResource(R.drawable.car);
            }
        }

    }


    /**
     * This method initially creates the spinner
     * by setting up the vehicle names as it items by using the names of
     * vehicles owned by the user who has the given username
     * @param username
     */
    public void createSpinner(String username){
        VehicleDAO vehicleDAO= new VehicleDAO(getContext());
        ArrayList<Vehicle> vehicles=vehicleDAO.getVehicleByUserID(username);
        ArrayList<String> vehicleNames=new ArrayList<>();
        for(int i=0;i<vehicles.size();i++){
            vehicleNames.add(vehicles.get(i).getRegNo());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_spinner_item,vehicleNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }



    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
