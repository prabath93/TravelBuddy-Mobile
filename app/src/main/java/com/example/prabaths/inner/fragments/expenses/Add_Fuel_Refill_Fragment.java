package com.example.prabaths.inner.fragments.expenses;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.prabaths.Data.DAO.ExpenseDAO;
import com.example.prabaths.Data.DAO.InstantDataDAO;
import com.example.prabaths.Data.SQLiteHelper;
import com.example.prabaths.Dialogs.PickerDialog;
import com.example.prabaths.Fragments.Home_Fragment;
import com.example.prabaths.GPS.GPSTracker;
import com.example.prabaths.GPS.GPSTracker1;
import com.example.prabaths.Server.InsertToDB;
import com.example.prabaths.TravelBuddy.MainActivity;
import com.example.prabaths.TravelBuddy.R;
import com.example.prabaths.VO.FuelExpense;
import com.example.prabaths.VO.InstantData;
import com.example.prabaths.inner.fragments.home.Log_Fragment;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by prabath s on 5/1/2016.
 */
public class Add_Fuel_Refill_Fragment extends Fragment {

    private static TextView textView;
    private static MainActivity mainActivity;
    private static EditText totalCostTxt;
    private static EditText odometerReadingTxt;
    private static EditText volTxt;
    private static EditText unitPriceTxt;
    private static EditText locationTxt;
    private static GPSTracker gpsTracker;
    private static Date date1;
    private static Button autoLocationBtn;
    private static Button turnGPSOnBtn;
    private static Button turnInternetBtn;
    private String userName;
    private String regNo;
    private InstantData idata;
    private static CheckBox partialTankChkBox;
    private InstantDataDAO instantDataDAO;
    private static EditText notesTxt;
    private static Button fuelRefillSubmitBtn;
    private static Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.add_fuel_refill_fragment, container, false);
        this.instantDataDAO=new InstantDataDAO(getContext());
        volTxt=(EditText) v.findViewById(R.id.volumeTxt);
        totalCostTxt=(EditText) v.findViewById(R.id.totalCostTxt);
        gpsTracker=new GPSTracker(getContext());
        textView= (TextView)v.findViewById(R.id.da);
        unitPriceTxt=(EditText) v.findViewById(R.id.unitPriceTxt);
        fuelRefillSubmitBtn=(Button)v.findViewById(R.id.fuelRefillSubmitBtn);
        odometerReadingTxt=(EditText) v.findViewById(R.id.odometerReadingTxt);
        partialTankChkBox=(CheckBox)v.findViewById(R.id.partialTankChkBox);

        locationTxt=(EditText) v.findViewById(R.id.locationTxt);
        notesTxt=(EditText) v.findViewById(R.id.notesTxt);
        //locationTxt.setText(gpsTracker.getAddress());
        final GPSTracker1 gpsTracker1=new GPSTracker1(getContext(),locationTxt);

        turnGPSOnBtn=(Button)v.findViewById(R.id.turnOnGPSBtn);
        //turnGPSOnBtn.setFocusable(true);
        turnInternetBtn=(Button)v.findViewById(R.id.turnOnInternetBtn);

        autoLocationBtn=(Button) v.findViewById(R.id.autoLocationBtn);

        Calendar calendar=Calendar.getInstance();
        date1=calendar.getTime();
        String year= checkDigit(calendar.get(Calendar.YEAR));
        String month=checkDigit(calendar.get(Calendar.MONTH)+1);
        String day= checkDigit(calendar.get(Calendar.DAY_OF_MONTH));


        textView.setText(year + "-" + month + "-" + day);

        try {
            idata=instantDataDAO.getInstantDataByUserName();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(idata==null){
            SQLiteHelper helper = SQLiteHelper.getInstance(getContext());
            Date date1 = null;
            try {
                date1 = helper.dateFromString(year+"-"+month+"-"+day);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            InstantData instantData=new InstantData(MainActivity.userName,Home_Fragment.regNo,0.0,date1,0.0);
            instantDataDAO.addInstantData(instantData);
            try {
                idata=instantDataDAO.getInstantDataByUserName();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if(idata.getUnitPrice()!=0.0){
            unitPriceTxt.setText(String.valueOf(idata.getUnitPrice()));
        }

        btn=(Button)v.findViewById(R.id.dateSelectBtn);
        btn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        buttonClicked();
                    }
                }
        );



        volTxt.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        calculateTotalCost(s.toString(),unitPriceTxt.getText().toString());
                    }
                });


        unitPriceTxt.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        calculateTotalCost(volTxt.getText().toString(), s.toString());
                    }
                });



        fuelRefillSubmitBtn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        submitBtnClicked();

                    }
                }
        );


        autoLocationBtn.setEnabled(false);
        if(gpsTracker.getIsGPSEnabled()){
            turnGPSOnBtn.setEnabled(false);
        }
        if(gpsTracker.getIsInternetEnabled()){
            turnInternetBtn.setEnabled(false);
        }
        if(gpsTracker.getIsGPSEnabled() && gpsTracker.getIsInternetEnabled()){
            autoLocationBtn.setEnabled(true);
        }
        turnGPSOnBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gpsTracker.showSettingsAlert();
            }
        });

        turnGPSOnBtn.setFocusableInTouchMode(true);

        turnGPSOnBtn.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if (gpsTracker.getIsGPSEnabled()) {
                        turnGPSOnBtn.setEnabled(false);
                        if (gpsTracker.getIsInternetEnabled()) {
                            autoLocationBtn.setEnabled(true);
                        }
                        //turnGPSOnBtn.clearFocus();
                    }
                } else {
                    //Toast.makeText(getContext(), "focus lost", Toast.LENGTH_LONG).show();
                    //turnGPSOnBtn.clearFocus();
                    if (gpsTracker.getIsGPSEnabled()) {
                        turnGPSOnBtn.setEnabled(false);
                        if (gpsTracker.getIsInternetEnabled()) {
                            autoLocationBtn.setEnabled(true);
                        }
                        //turnGPSOnBtn.clearFocus();
                    }
                }
            }
        });

        turnInternetBtn.setFocusableInTouchMode(true);
        turnInternetBtn.setOnFocusChangeListener(
                new OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (gpsTracker.getIsInternetEnabled()) {
                            turnInternetBtn.setEnabled(false);
                            if (gpsTracker.getIsInternetEnabled()) {
                                autoLocationBtn.setEnabled(true);
                            }
                            turnGPSOnBtn.clearFocus();
                        } else {
                            //turnGPSOnBtn.clearFocus();
                            if (gpsTracker.getIsInternetEnabled()) {
                                turnInternetBtn.setEnabled(false);
                                if (gpsTracker.getIsInternetEnabled()) {
                                    autoLocationBtn.setEnabled(true);
                                }
                                turnGPSOnBtn.clearFocus();
                            }
                        }
                    }
                }
        );
        turnInternetBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gpsTracker.showInternetAlert();
                    }
                });


        autoLocationBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (locationTxt.getText().toString().equals("")) {
                    gpsTracker1.setLocation();
                }
            }
        });

        autoLocationBtn.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {

                }
            }
        });

        if(Home_Fragment.regNo.equals("")){
            btn.setEnabled(false);
            odometerReadingTxt.setEnabled(false);
            volTxt.setEnabled(false);
            partialTankChkBox.setEnabled(false);
            unitPriceTxt.setEnabled(false);
            totalCostTxt.setEnabled(false);
            notesTxt.setEnabled(false);
            locationTxt.setEnabled(false);
            turnGPSOnBtn.setEnabled(false);
            turnInternetBtn.setEnabled(false);
            autoLocationBtn.setEnabled(false);
            fuelRefillSubmitBtn.setEnabled(false);
            showAlertDialog("You should first add a vehicle!!!");
        }




        return v;
    }

    /**
     * This method is called when the date selection button is clicked
     * it will invoke setTextView method in picker dialog class and
     * set selected date to the TextView
     */
    private void buttonClicked(){
        PickerDialog pickerDialog= new PickerDialog();

        pickerDialog.setTextView(textView);
        pickerDialog.show(getFragmentManager(), "Pick A Date");

    }

    /**
     * This method checks the digits of the date
     * to keep it in correct format and return the corrected digit
     * @param number number that needs to be checked
     * @return corrected digit
     */
    public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }

    /**
     * This method will invoked when submit button is pressed
     */
    private void submitBtnClicked(){
        boolean formIsValidated=validateForm();


        if(!Home_Fragment.regNo.equals("")) {
            if (formIsValidated) {
                SQLiteHelper helper = SQLiteHelper.getInstance(getContext());
                Date date = null;
                try {
                    date = helper.dateFromString(textView.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                int partialTank = 0;
                double economy = 0;
                if (partialTankChkBox.isChecked()) {
                    partialTank = 1;


                } else {
                    partialTank = 0;
                    if (idata.getOdometerReading() != 0) {
                        double distance;
                        if (idata.getDate().before(date)) {
                            distance = Double.parseDouble(odometerReadingTxt.getText().toString()) - idata.getOdometerReading();
                        } else {
                            distance = idata.getOdometerReading() - Double.parseDouble(odometerReadingTxt.getText().toString());
                        }
                        if (distance > 0) {
                            economy = distance / Double.parseDouble(volTxt.getText().toString());
                        }
                        //Toast.makeText(getContext(), "Economy: ("+odometerReadingTxt.getText().toString()+"-"+distance+")/"+volTxt.getText(), Toast.LENGTH_SHORT).show();
                    }


                }
                FuelExpense fuelExpense = new FuelExpense(Home_Fragment.regNo, Double.parseDouble(totalCostTxt.getText().toString()), date, 1, locationTxt.getText().toString(), notesTxt.getText().toString(), Double.parseDouble(odometerReadingTxt.getText().toString()), partialTank, 1, Double.parseDouble(unitPriceTxt.getText().toString()), MainActivity.userName, Double.parseDouble(volTxt.getText().toString()), economy);
                ExpenseDAO expenseDAO = new ExpenseDAO(getContext());
                expenseDAO.addFuelExpense(fuelExpense);
                idata.setDate(date);
                idata.setOdometerReading(Double.parseDouble(odometerReadingTxt.getText().toString()));
                idata.setUnitPrice(Double.parseDouble(unitPriceTxt.getText().toString()));
                instantDataDAO.updateInstantData(idata);
                InsertToDB insertToDB = new InsertToDB(getContext());
                insertToDB.insertFuelExpenseReport(fuelExpense);
                Log_Fragment.updateLog();
                clearInterface();

            }
        }


    }

    /**
     * This method is called when the submit button is clicked,
     * to clear EditText views in the interface
     */
    private void clearInterface(){
        odometerReadingTxt.setText("");
        volTxt.setText("");
        partialTankChkBox.setChecked(false);
        totalCostTxt.setText("");
        notesTxt.setText("");
        locationTxt.setText("");

    }

    /**
     * This method validate the
     * data entered in the EditText views
     * @return
     */
    private boolean validateForm(){
        //Toast.makeText(getContext(),"inside validate",Toast.LENGTH_LONG).show();
        if(odometerReadingTxt.getText().toString().equals("")){
            showAlertDialog("Odometer reading should be given");
            return false;
        }
        else if(volTxt.getText().toString().equals("")){
            showAlertDialog("Quantity of fuel should be given");
            return false;
        }
        else if(unitPriceTxt.getText().toString().equals(""))
        {
            showAlertDialog("Unit price of fuel should be given");
            return false;
        }
        else if(totalCostTxt.getText().toString().equals("")){
            showAlertDialog("Total cost should be given");
            return false;
        }
        else if(locationTxt.getText().toString().equals("")){
            //Toast.makeText(getContext(),"inside location validation",Toast.LENGTH_LONG).show();
            if(!gpsTracker.getIsGPSEnabled()) {

                gpsTracker.showSettingsAlert();

                return false;
            }
            else if(!gpsTracker.getIsInternetEnabled()){
                //Toast.makeText(getContext(),"inside location validation",Toast.LENGTH_LONG).show();
                gpsTracker.showInternetAlert();
                return false;
            }
        }

        else{
            int k=1;
            try{
                Double.parseDouble(odometerReadingTxt.getText().toString());

            }
            catch (java.lang.NumberFormatException e){
                showAlertDialog("Invalid odometer reading");
                k=0;
                return false;
            }
            try{
                Double.parseDouble(volTxt.getText().toString());

            }
            catch (java.lang.NumberFormatException e){
                showAlertDialog("Invalid quantity");
                k=0;
                return false;
            }
            try{
                Double.parseDouble(unitPriceTxt.getText().toString());

            }
            catch (java.lang.NumberFormatException e){
                showAlertDialog("Invalid unit price");
                k=0;
                return false;
            }
            try{
                Double.parseDouble(totalCostTxt.getText().toString());

            }
            catch (java.lang.NumberFormatException e){
                showAlertDialog("Invalid total cost");
                k=0;
                return false;

            }
            if(k==1){

                return true;
            }
            else return false;

        }

        return true;
    }

    /**
     * This static method is called to enble ui components
     */
    public static void enableUI(){
        if(btn!=null) {
            btn.setEnabled(false);
            odometerReadingTxt.setEnabled(false);
            volTxt.setEnabled(false);
            partialTankChkBox.setEnabled(false);
            unitPriceTxt.setEnabled(false);
            totalCostTxt.setEnabled(false);
            notesTxt.setEnabled(false);
            locationTxt.setEnabled(false);
            turnGPSOnBtn.setEnabled(false);
            turnInternetBtn.setEnabled(false);
            autoLocationBtn.setEnabled(false);
            fuelRefillSubmitBtn.setEnabled(false);
        }
    }


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
    private void calculateTotalCost(String volume,String unit){
        Double vol=0.0;
        Double unitPrice=0.0;
        int k=0;
        try{
            vol=Double.parseDouble(volume);

        }
        catch (java.lang.NumberFormatException e){
            //Toast.makeText(getContext(),"Invalid volume",Toast.LENGTH_LONG).show();
            totalCostTxt.setText("");
            k=1;
        }
        try{
            unitPrice=Double.parseDouble(unit);
        }
        catch (java.lang.NumberFormatException e){
            //Toast.makeText(getContext(),"Invalid unit price",Toast.LENGTH_LONG).show();
            totalCostTxt.setText("");
            k=1;
        }
        if(k==0){
            Double totCost=vol*unitPrice;
            String totCostStr=String.valueOf(totCost);
            totalCostTxt.setText(totCostStr);
        }

    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }
}


