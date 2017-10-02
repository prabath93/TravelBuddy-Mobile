package com.example.prabaths.Dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by prabath s on 5/1/2016.
 */
public class PickerDialog extends DialogFragment {
    private static TextView textView;
    private static DateSetting dateSetting;



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dateSetting= new DateSetting(getActivity(),textView);
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog;
        datePickerDialog=new DatePickerDialog(getActivity(),dateSetting,year,month,day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        return datePickerDialog;
    }

    public void setTextView(TextView textView){
        this.textView=textView;
    }
    public DateSetting getDateSetting(){return dateSetting;}
}
