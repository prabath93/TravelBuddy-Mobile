package com.example.prabaths.Server;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prabaths.Data.SQLiteHelper;
import com.example.prabaths.Fragments.Home_Fragment;
import com.example.prabaths.VO.Expense;
import com.example.prabaths.VO.FuelExpense;
import com.example.prabaths.VO.OtherExpense;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by prabath s on 6/1/2016.
 */
public class InsertToDB {
    RequestQueue requestQueue;
    Context context;
    String insertFuelExpenseUrl = "http://192.168.42.196/tutorial/insertFuelExpense.php";
    String insertOtherExpenseUrl="http://192.168.42.196/tutorial/insertOtherExpense.php";
    SQLiteHelper helper;

    public InsertToDB(Context context){
        this.context=context;
        this.requestQueue = Volley.newRequestQueue(context);
        this.helper=SQLiteHelper.getInstance(context);
    }

    /**
     * This method inserts a fuel expense record to the remote database
     * @param fuelExpense FuelExpense object which contains fuel expense details
     */
    public void insertFuelExpenseReport(final FuelExpense fuelExpense){

        StringRequest request = new StringRequest(Request.Method.POST, insertFuelExpenseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters  = new HashMap<String, String>();
                System.out.println("inside get params");

                parameters.put("username",fuelExpense.getUserID());
                parameters.put("regNo",fuelExpense.getCarRegNo());
                parameters.put("cost",String.valueOf(fuelExpense.getCost()));
                parameters.put("date",helper.dateToString(fuelExpense.getDate(), context));
                parameters.put("location",fuelExpense.getLocation());
                parameters.put("notes",fuelExpense.getNotes());
                parameters.put("odometerReading",String.valueOf(fuelExpense.getOdometerReading()));
                parameters.put("partialTank",String.valueOf(fuelExpense.getPartialTank()));
                parameters.put("type",String.valueOf(fuelExpense.getType()));
                parameters.put("unitPrice",String.valueOf(fuelExpense.getUnitPrice()));
                parameters.put("volume",String.valueOf(fuelExpense.getVolume()));
                parameters.put("economy",String.valueOf(fuelExpense.getEconomy()));
                parameters.put("model", Home_Fragment.model);
                parameters.put("vehicleType",Home_Fragment.type_of_vechicle);
                return parameters;
            }
        };
        requestQueue.add(request);
    }



    /**
     * This method inserts a other kind of expense record to the remote database
     * @param otherExpense OtherExpense object which contains other expense details
     */
    public void insertOtherExpense(final OtherExpense otherExpense){
        StringRequest request = new StringRequest(Request.Method.POST, insertOtherExpenseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters  = new HashMap<String, String>();

                parameters.put("username",otherExpense.getUserID());
                parameters.put("regNo",otherExpense.getCarRegNo());
                parameters.put("cost",String.valueOf(otherExpense.getCost()));
                parameters.put("date",helper.dateToString(otherExpense.getDate(), context));
                parameters.put("location",otherExpense.getLocation());
                parameters.put("notes",otherExpense.getNotes());
                parameters.put("odometerReading",String.valueOf(otherExpense.getOdometerReading()));
                parameters.put("partialTank",String.valueOf(otherExpense.getPartialTank()));
                parameters.put("type",String.valueOf(otherExpense.getType()));
                parameters.put("unitPrice",String.valueOf(otherExpense.getUnitPrice()));
                parameters.put("volume",String.valueOf(otherExpense.getVolume()));
                parameters.put("model", Home_Fragment.model);
                parameters.put("vehicleType",Home_Fragment.type_of_vechicle);
                return parameters;
            }
        };
        requestQueue.add(request);
    }
}
