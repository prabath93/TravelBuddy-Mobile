package com.example.prabaths.inner.fragments.expenses;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.prabaths.Data.DAO.ExpenseDAO;
import com.example.prabaths.Data.SQLiteHelper;
import com.example.prabaths.Dialogs.PickerDialog;
import com.example.prabaths.Fragments.Home_Fragment;
import com.example.prabaths.Server.InsertToDB;
import com.example.prabaths.TravelBuddy.MainActivity;
import com.example.prabaths.TravelBuddy.R;
import com.example.prabaths.VO.OtherExpense;
import com.example.prabaths.inner.fragments.home.Log_Fragment;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by prabath s on 5/1/2016.
 */
public class Add_Other_Expense_Fragment extends Fragment {
    private static TextView textView;
    private MainActivity mainActivity;
    private static EditText cost;
    private static EditText description;
    private static RadioButton serviceRadioBtn;
    private static RadioButton repairRadioBtn;
    private static Button btn;
    private static Button otherExpenseSubmitBtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.add_other_expense_fragment, container, false);
        Calendar calendar=Calendar.getInstance();
        cost=(EditText) v.findViewById(R.id.costTxt);
        description=(EditText) v.findViewById(R.id.descriptionTxt);
        serviceRadioBtn=(RadioButton)v.findViewById(R.id.serviceRadioBtn);
        repairRadioBtn=(RadioButton)v.findViewById(R.id.repairRadioBtn);


        RadioGroup radioGroup=(RadioGroup)v.findViewById(R.id.radioGroup1);
        radioGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        //Toast.makeText(getContext(),group.get,Toast.LENGTH_LONG).show();
                    }
                }
        );
        RadioButton radioButton=(RadioButton) v.findViewById(R.id.serviceRadioBtn);

        String year= checkDigit(calendar.get(Calendar.YEAR));
        String month=checkDigit(calendar.get(Calendar.MONTH)+1);
        String day= checkDigit(calendar.get(Calendar.DAY_OF_MONTH));

        textView= (TextView)v.findViewById(R.id.da2);
        textView.setText(year + "-" + month + "-" + day);

        btn=(Button)v.findViewById(R.id.dateSelectBtn2);

        btn.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        buttonClicked(v);
                    }
                }
        );

        otherExpenseSubmitBtn=(Button)v.findViewById(R.id.otherExpenseSubmitBtn);
        otherExpenseSubmitBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submit();
                    }
                }
        );

        if(Home_Fragment.regNo.equals("")){
            btn.setEnabled(false);
            repairRadioBtn.setEnabled(false);
            serviceRadioBtn.setEnabled(false);
            cost.setEnabled(false);
            description.setEnabled(false);
            otherExpenseSubmitBtn.setEnabled(false);
            showAlertDialog("You should first add a vehicle!!!");
        }
        return v;
    }

    private void buttonClicked(View view){
        PickerDialog pickerDialog= new PickerDialog();

        pickerDialog.setTextView(textView);
        pickerDialog.show(getFragmentManager(), "Pick A Date");


    }
    public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }


    public static void enableUI(){
        if(btn!=null) {
            btn.setEnabled(true);
            repairRadioBtn.setEnabled(true);
            serviceRadioBtn.setEnabled(true);
            cost.setEnabled(true);
            description.setEnabled(true);
            otherExpenseSubmitBtn.setEnabled(true);
        }


    }

    private void submit(){

        if(isFormValid()){
            SQLiteHelper helper = SQLiteHelper.getInstance(getContext());
            Date date = null;
            try {
                date = helper.dateFromString(textView.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int type;
            if(serviceRadioBtn.isChecked()){
                type=2;
            }
            else{type=3;}
            OtherExpense otherExpense=new OtherExpense(Home_Fragment.regNo,Double.parseDouble(cost.getText().toString()),date,3,"",description.getText().toString(),0.0,0,type,0.0,MainActivity.userName,0.0);
            ExpenseDAO expenseDAO =new ExpenseDAO(getContext());
            expenseDAO.addOtherExpense(otherExpense);
            InsertToDB insertToDB=new InsertToDB(getContext());
            insertToDB.insertOtherExpense(otherExpense);
            Log_Fragment.updateLog();
            clearInterface();
        }
    }


    private void clearInterface(){
        serviceRadioBtn.setChecked(false);
        repairRadioBtn.setChecked(false);
        cost.setText("");
        description.setText("");
    }

    public boolean isFormValid(){
        if(!serviceRadioBtn.isChecked() && !repairRadioBtn.isChecked()){
            showAlertDialog("Type of expense should be selected");
            return false;
        }
        if(cost.getText().toString().equals("")){
            showAlertDialog("Cost should be given");
            return false;
        }
        else{
            try{
                Double.parseDouble(cost.getText().toString());
            }
            catch (NumberFormatException e){
                showAlertDialog("Invalid cost");
                return false;
            }
        }
        return true;
    }

    private void refreshUI(){
        cost.setText("");
        description.setText("");
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
    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }



}
