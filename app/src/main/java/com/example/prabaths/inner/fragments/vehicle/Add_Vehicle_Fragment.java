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

import com.example.prabaths.Data.DAO.VehicleDAO;
import com.example.prabaths.Fragments.Home_Fragment;
import com.example.prabaths.ImageHandling.CopyImage;
import com.example.prabaths.VO.Vehicle;
import com.example.prabaths.TravelBuddy.MainActivity;
import com.example.prabaths.TravelBuddy.R;
import com.example.prabaths.inner.fragments.expenses.Add_Fuel_Refill_Fragment;
import com.example.prabaths.inner.fragments.expenses.Add_Other_Expense_Fragment;
import com.example.prabaths.inner.fragments.home.View_Vehicle_For_Home_Fragment;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by prabath s on 5/1/2016.
 */
public class Add_Vehicle_Fragment extends Fragment{

    EditText regNoTxt;
    EditText modelTxt;
    EditText brandTxt;
    EditText yearTxt;
    VehicleDAO vehicleDAO;
    ImageView vehicleImage;
    public static String imageUri;
    private MainActivity mainActivity;
    private Spinner spinner;

    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.add_vehicle_details_fragment,container,false);
        vehicleDAO=new VehicleDAO(this.getContext());
        Button btn1=(Button) v.findViewById(R.id.addVehicleBtn);
        regNoTxt=(EditText) v.findViewById(R.id.regNoTxt);
        modelTxt=(EditText) v.findViewById(R.id.modelTxt);
        brandTxt=(EditText) v.findViewById(R.id.brandTxt);
        yearTxt=(EditText) v.findViewById(R.id.yearTxt);
        imageUri="";
        vehicleImage=(ImageView) v.findViewById(R.id.vehiclePicImView1);
        spinner=(Spinner) v.findViewById(R.id.vehicleTypeSpinner);
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

        btn1.setOnClickListener(
                new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        addButtonClicked();
                    }
                }
        );

        Button changeVehicleImageBtn=(Button) v.findViewById(R.id.changeVehicleImageBtn1);
        changeVehicleImageBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent();
                        intent.setType("image/.jpg");
                        intent.setAction(Intent.ACTION_GET_CONTENT);

                        startActivityForResult(Intent.createChooser(intent,"Select Image"), 2);
                    }
                }
        );
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode,resultCode,data);

        if(resultCode== Activity.RESULT_OK){
            if(requestCode==2){
                //iv.setImageResource(0);
                Uri newUri=data.getData();

                CopyImage copyImage=new CopyImage();
                try {
                    //Toast.makeText(getContext(),"copying image",Toast.LENGTH_LONG).show();
                    newUri=copyImage.copyFile(data.getData());
                } catch (IOException e) {
                    //Toast.makeText(getContext(),"copying failed",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                vehicleImage.setImageURI(newUri);
                imageUri=newUri.toString();
               // Toast.makeText(getActivity(),"success:"+data.getData(),Toast.LENGTH_LONG).show();
            }
        }

    }


    /**
     * This method executes when the add button is clicked
     */
    private void addButtonClicked() {
        int year = 0;
        if(isValidUI()){

        if (yearTxt.getText().toString().equals("")) {
            year = 0;
        } else {
            year = Integer.parseInt(yearTxt.getText().toString());
        }
            if(Home_Fragment.regNo.equals("")){
                Add_Fuel_Refill_Fragment.enableUI();
                Add_Other_Expense_Fragment.enableUI();
                Edit_Vehicle_Details_Fragment.enableUI();

            }

        Vehicle v = new Vehicle(brandTxt.getText().toString(), 3, imageUri, 99.0, modelTxt.getText().toString(), regNoTxt.getText().toString(), MainActivity.userName, year,spinner.getSelectedItem().toString());
        vehicleDAO.addVehicle(v);


        //Toast.makeText(getContext(), "Vehicle Successfully added", Toast.LENGTH_LONG).show();
        regNoTxt.setText("");
        modelTxt.setText("");
        brandTxt.setText("");
        yearTxt.setText("");
            View_Vehicle_For_Home_Fragment.changeSpinner();



    }
    }

    /**
     * This method checks the validity of the data in EditText views
     * @return validity
     */
    private boolean isValidUI(){
        if(regNoTxt.getText().toString().equals("")){
            showAlertDialog("Registration no of the vehicle should be given");
            return false;
        }
        else if(modelTxt.getText().toString().equals("")){
            showAlertDialog("Model of the vehicle should be given");
            return false;
        }
        else if(brandTxt.getText().toString().equals("")){
            showAlertDialog("Brand of the vehicle should be given");
            return false;
        }
        else if(!yearTxt.getText().toString().equals("")){
            try{
                Integer.parseInt(yearTxt.getText().toString());
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
     * This method shows an error message
     * @param message message which needs to be displayed
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
