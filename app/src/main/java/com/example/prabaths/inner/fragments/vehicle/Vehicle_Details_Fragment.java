package com.example.prabaths.inner.fragments.vehicle;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prabaths.Data.DAO.InstantDataDAO;
import com.example.prabaths.Data.DAO.VehicleDAO;
import com.example.prabaths.Fragments.Home_Fragment;
import com.example.prabaths.ImageHandling.CopyImage;
import com.example.prabaths.TravelBuddy.MainActivity;
import com.example.prabaths.TravelBuddy.R;
import com.example.prabaths.VO.InstantData;
import com.example.prabaths.VO.Vehicle;
import com.example.prabaths.inner.fragments.vehicle.Edit_Vehicle_Details_Fragment;

import java.io.File;
import java.text.ParseException;

/**
 * Created by prabath s on 4/30/2016.
 */
public class Vehicle_Details_Fragment extends Fragment {

    private MainActivity mainActivity;
    private TextView regNoTxt;
    private TextView modelTxt;
    private TextView brandTxt;
    private TextView mileageTxt;
    private ImageView imageView;
    private Button editVehicleDetails;
    private InstantDataDAO instantDataDAO;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.vehicle_details_fragment,container,false);
        //Button btn1=(Button)v.findViewById(R.id.changeVehicleBtn);
        regNoTxt=(TextView) v.findViewById(R.id.regNoTxt1);
        modelTxt=(TextView) v.findViewById(R.id.modelTxt1);
        brandTxt=(TextView) v.findViewById(R.id.brandTxt1);
        mileageTxt=(TextView) v.findViewById(R.id.mileageTxt1);
        imageView=(ImageView) v.findViewById(R.id.vehiclePicImView);
        editVehicleDetails=(Button) v.findViewById(R.id.editVehicleDetailBtn);
        instantDataDAO=new InstantDataDAO(getContext());
        InstantData instantData=null;
        try {
            instantData=instantDataDAO.getInstantDataByUserName();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        VehicleDAO vehicleDAO=new VehicleDAO(getContext());
        Vehicle vehicle=vehicleDAO.getVehicleByRegNo(Home_Fragment.regNo,MainActivity.userName);
        if(vehicle!=null) {
            regNoTxt.setText(vehicle.getRegNo());
            modelTxt.setText(vehicle.getModel());
            brandTxt.setText(vehicle.getBrand());
            if(instantData!=null){
                if(instantData.getOdometerReading()!=0){
                    mileageTxt.setText(String.valueOf(instantData.getOdometerReading()));
                }
            }
            if (!vehicle.getImageURI().equals("")) {
                CopyImage ci = new CopyImage();
                Uri uri = Uri.parse(vehicle.getImageURI());
                String imPath = ci.getPath(uri);
                File imFile = new File(imPath);
                if (!imPath.equals("")) {
                    if (imFile.exists()) {
                        imageView.setImageURI(uri);
                    } else {
                        imageView.setImageResource(R.drawable.car);
                    }
                } else {
                    imageView.setImageResource(R.drawable.car);
                }
            }

        }
        else{editVehicleDetails.setEnabled(false);}


        editVehicleDetails.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Edit_Vehicle_Details_Fragment edit_vehicle_details_fragment=new Edit_Vehicle_Details_Fragment();
                        getFragmentManager().beginTransaction().replace(R.id.main_content, edit_vehicle_details_fragment).addToBackStack(null).commit();
                    }
                }
        );
        return v;
    }


    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
