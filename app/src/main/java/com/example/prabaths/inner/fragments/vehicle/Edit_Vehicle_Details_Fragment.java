package com.example.prabaths.inner.fragments.vehicle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.prabaths.Data.DAO.ExpenseDAO;
import com.example.prabaths.Data.DAO.VehicleDAO;
import com.example.prabaths.Fragments.Home_Fragment;
import com.example.prabaths.ImageHandling.CopyImage;
import com.example.prabaths.TravelBuddy.MainActivity;
import com.example.prabaths.TravelBuddy.R;
import com.example.prabaths.VO.Vehicle;
import com.example.prabaths.inner.fragments.home.View_Vehicle_For_Home_Fragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by prabath s on 5/1/2016.
 */
public class Edit_Vehicle_Details_Fragment extends Fragment {

    private MainActivity mainActivity;

    private static EditText regNoEditTxt;
    private static EditText modelEditTxt;
    private static EditText brandEditTxt;
    private static EditText yearEditTxt;
    private static Spinner spinner;
    private static Button  submitVehicleEdit;
    private static Button deleteVehicleBtn;
    private static Button changeVehicleImageBtn;
    private static ImageView vehiclePicImView;
    private static VehicleDAO vehicleDAO;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.edit_vehicle_details_fragment,container,false);
        regNoEditTxt=(EditText) v.findViewById(R.id.regNoEditTxt1);
        modelEditTxt=(EditText) v.findViewById(R.id.modelEditTxt1);
        brandEditTxt=(EditText) v.findViewById(R.id.brandEditTxt1);
        yearEditTxt=(EditText) v.findViewById(R.id.yearEditTxt1);
        spinner=(Spinner) v.findViewById(R.id.vehicleTypeSpinner1);
        ArrayList<String> vehicleTypes=new ArrayList<String>();
        vehicleTypes.add("Car");
        vehicleTypes.add("Van");
        vehicleTypes.add("Motor Cycle");
        vehicleTypes.add("Truck");
        vehicleTypes.add("Other");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_spinner_item,vehicleTypes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        submitVehicleEdit=(Button) v.findViewById(R.id.submitEditBtn);
        deleteVehicleBtn= (Button) v.findViewById(R.id.deleteVehicleBtn);
        vehiclePicImView=(ImageView) v.findViewById(R.id.vehiclePicImView2);
        changeVehicleImageBtn=(Button) v.findViewById(R.id.changeVehicleImageBtn2);
        vehicleDAO=new VehicleDAO(getContext());
        setUI();
        submitVehicleEdit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateVehicle();
                    }
                }
        );

        deleteVehicleBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteVehicle();
                        View_Vehicle_For_Home_Fragment.changeSpinner();
                        setUI();
                    }
                }
        );

        changeVehicleImageBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);

                        startActivityForResult(Intent.createChooser(intent,"Select Image"), 3);
                    }
                }
        );
        return v;
    }

    /**
     * This method executes when save button is clicked
     */
    private void updateVehicle() {

        if (isValidUI()) {
            vehicleDAO.updateVehicle(MainActivity.userName, Home_Fragment.regNo, modelEditTxt.getText().toString(), brandEditTxt.getText().toString(), Integer.parseInt(yearEditTxt.getText().toString()), spinner.getSelectedItem().toString());
        }

    }


    /**
     * This method initially set up the user interface
     */
    private void setUI(){

        Vehicle vehicle=vehicleDAO.getVehicleByRegNo(Home_Fragment.regNo,MainActivity.userName);
        if(vehicle!=null) {
            regNoEditTxt.setText(vehicle.getRegNo());
            modelEditTxt.setText(vehicle.getModel());
            brandEditTxt.setText(vehicle.getBrand());
            for (int i = 0; i < 5; i++) {
                if (spinner.getItemAtPosition(i).toString().equals(vehicle.getType())) {
                    spinner.setSelection(i);
                }
            }
            if (vehicle.getYear() != 0) {
                yearEditTxt.setText(String.valueOf(vehicle.getYear()));
            }

            if (!vehicle.getImageURI().equals("")) {
                CopyImage ci = new CopyImage();
                Uri uri = Uri.parse(vehicle.getImageURI());
                String imPath = ci.getPath(uri);
                File imFile = new File(imPath);
                if (!imPath.equals("")) {
                    if (imFile.exists()) {
                        vehiclePicImView.setImageURI(uri);
                    } else {
                        vehiclePicImView.setImageResource(R.drawable.car);
                    }
                } else {
                    vehiclePicImView.setImageResource(R.drawable.car);
                }
            }
        }
        else{
            regNoEditTxt.setText("");
            modelEditTxt.setText("");
            brandEditTxt.setText("");
            yearEditTxt.setText("");
            //regNoEditTxt.setEnabled(false);
            //modelEditTxt.setEnabled(false);
            //brandEditTxt.setEnabled(false);
            //yearEditTxt.setEnabled(false);
            //spinner.setEnabled(false);
            submitVehicleEdit.setEnabled(false);
            deleteVehicleBtn.setEnabled(false);
            changeVehicleImageBtn.setEnabled(false);
        }
    }

    /**
     * This method enables the input fields in the user inteface
     */
    public static void enableUI(){

        if(regNoEditTxt!=null) {

            //regNoEditTxt.setEnabled(true);
            //modelEditTxt.setEnabled(true);
            //brandEditTxt.setEnabled(true);
            //yearEditTxt.setEnabled(true);
           // spinner.setEnabled(true);
            submitVehicleEdit.setEnabled(true);
            deleteVehicleBtn.setEnabled(true);
            changeVehicleImageBtn.setEnabled(true);
        }

    }

    /**
     * This method deletes a vehicle and executed when delete button is clicked
     */
    private void deleteVehicle(){
        ExpenseDAO expenseDAO=new ExpenseDAO(getContext());
        expenseDAO.deleteRecordsOfAVehicle(Home_Fragment.regNo,MainActivity.userName);
        vehicleDAO.deleteVehicle(Home_Fragment.regNo,MainActivity.userName);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode,resultCode,data);

        if(resultCode== Activity.RESULT_OK){
            if(requestCode==3){
                //iv.setImageResource(0);
                Uri newUri=data.getData();

                CopyImage copyImage=new CopyImage();
                try {
                    //Toast.makeText(getContext(), "copying image", Toast.LENGTH_LONG).show();
                    newUri=copyImage.copyFile(data.getData());
                } catch (IOException e) {
                    //Toast.makeText(getContext(),"copying failed",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                vehiclePicImView.setImageURI(newUri);
                vehicleDAO.setImageUri(newUri.toString());
                //Toast.makeText(getActivity(),"success:"+data.getData(),Toast.LENGTH_LONG).show();
            }
        }

    }

    /**
     * This method checks the validity of the data in input fields
     * @return validity
     */
    private boolean isValidUI(){
        if(regNoEditTxt.getText().toString().equals("")){
            showAlertDialog("Registration no of the vehicle should be given");
            return false;
        }
        else if(modelEditTxt.getText().toString().equals("")){
            showAlertDialog("Model of the vehicle should be given");
            return false;
        }
        else if(brandEditTxt.getText().toString().equals("")){
            showAlertDialog("Brand of the vehicle should be given");
            return false;
        }
        else if(!yearEditTxt.getText().toString().equals("")){
            try{
                Integer.parseInt(yearEditTxt.getText().toString());
            }
            catch (NumberFormatException e){
                showAlertDialog("Invalid year");
                return false;
            }
        }
        //else if(yearTxt.getText().toString().)
        return true;
    }


    /**
     * This method shows an alert message
     * @param message message needs to be shown
     */
    private void showAlertDialog(String message){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        // Setting Dialog Title
        alertDialog.setTitle("Alert!");

        // Setting Dialog Message
        alertDialog.setMessage(message);

        alertDialog.setNeutralButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }



    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}

