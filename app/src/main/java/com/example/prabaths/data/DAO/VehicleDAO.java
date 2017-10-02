package com.example.prabaths.Data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.prabaths.Data.InstantDataContract;
import com.example.prabaths.Data.SQLiteHelper;
import com.example.prabaths.Data.UserContract;
import com.example.prabaths.Data.VehicleContract;
import com.example.prabaths.Fragments.Home_Fragment;
import com.example.prabaths.TravelBuddy.MainActivity;
import com.example.prabaths.VO.InstantData;
import com.example.prabaths.VO.Vehicle;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by prabath s on 5/2/2016.
 */
public class VehicleDAO {
    Context context;
    public VehicleDAO(Context context){
        this.context=context;
    }


    /**
     * This method adds a vehicle to the database
     * @param vehicle vehicle object which contains details of the vehicle
     */
    public void addVehicle(Vehicle vehicle){
        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(VehicleContract.VehicleEntry.COLUMN_NAME_USER_ID,vehicle.getUserID());
        values.put(VehicleContract.VehicleEntry.COLUMN_NAME_REGISTRATION_NO,vehicle.getRegNo());
        values.put(VehicleContract.VehicleEntry.COLUMN_NAME_BRAND, vehicle.getBrand());
        values.put(VehicleContract.VehicleEntry.COLUMN_NAME_MODEL,vehicle.getModel());
        values.put(VehicleContract.VehicleEntry.COLUMN_NAME_LAST_MILEAGE,vehicle.getLastMileage());
        values.put(VehicleContract.VehicleEntry.COLUMN_NAME_YEAR,vehicle.getYear());
        values.put(VehicleContract.VehicleEntry.COLUMN_NAME_IMAGE_URI,vehicle.getImageURI());
        values.put(VehicleContract.VehicleEntry.COLUMN_NAME_TYPE,vehicle.getType());

        db.insert(VehicleContract.VehicleEntry.TABLE_NAME,null,values);

    }


    /**
     * This method returns a vehicle which has given vehicle id
     * @param vehicleID vehicle id of the vehicle
     * @return vehicle which has given vehicle id
     */

    public Vehicle getVehicle(int vehicleID){

        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                VehicleContract.VehicleEntry.COLUMN_NAME_USER_ID,
                VehicleContract.VehicleEntry.COLUMN_NAME_REGISTRATION_NO,
                VehicleContract.VehicleEntry.COLUMN_NAME_BRAND,
                VehicleContract.VehicleEntry.COLUMN_NAME_MODEL,
                VehicleContract.VehicleEntry.COLUMN_NAME_LAST_MILEAGE,
                VehicleContract.VehicleEntry.COLUMN_NAME_YEAR,
                VehicleContract.VehicleEntry.COLUMN_NAME_VEHICLE_ID,
                VehicleContract.VehicleEntry.COLUMN_NAME_IMAGE_URI,
                VehicleContract.VehicleEntry.COLUMN_NAME_TYPE
        };
        String sortOrder = VehicleContract.VehicleEntry.COLUMN_NAME_VEHICLE_ID + " DESC";
        Cursor c = db.query(VehicleContract.VehicleEntry.TABLE_NAME, projection,  VehicleContract.VehicleEntry.COLUMN_NAME_VEHICLE_ID+"="+vehicleID,null,null,null,sortOrder,null);

        if(c.moveToNext()) {
            Vehicle v = new Vehicle(c.getString(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_BRAND)),
                    c.getInt(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_VEHICLE_ID)),
                    c.getString(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_IMAGE_URI)),
                    c.getDouble(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_LAST_MILEAGE)),
                    c.getString(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_MODEL)),
                    c.getString(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_REGISTRATION_NO)),
                    c.getString(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_USER_ID)),
                    c.getInt(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_YEAR)),
                    c.getString(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_TYPE)));

            return  v;
        }
        else {
            return null;
        }

    }

    /**
     * This method returns a vehicle which has the given registration number
     * and registered under the given user
     * @param regNo registration number of the vehicle
     * @param userId user name of the user who owns the vehicle
     * @return vehicle with given registration number and registered under given user
     */

    public Vehicle getVehicleByRegNo(String regNo,String userId){

        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                VehicleContract.VehicleEntry.COLUMN_NAME_USER_ID,
                VehicleContract.VehicleEntry.COLUMN_NAME_REGISTRATION_NO,
                VehicleContract.VehicleEntry.COLUMN_NAME_BRAND,
                VehicleContract.VehicleEntry.COLUMN_NAME_MODEL,
                VehicleContract.VehicleEntry.COLUMN_NAME_LAST_MILEAGE,
                VehicleContract.VehicleEntry.COLUMN_NAME_YEAR,
                VehicleContract.VehicleEntry.COLUMN_NAME_VEHICLE_ID,
                VehicleContract.VehicleEntry.COLUMN_NAME_IMAGE_URI,
                VehicleContract.VehicleEntry.COLUMN_NAME_TYPE
        };
        String sortOrder = VehicleContract.VehicleEntry.COLUMN_NAME_VEHICLE_ID + " DESC";
        Cursor c = db.query(VehicleContract.VehicleEntry.TABLE_NAME, projection,  VehicleContract.VehicleEntry.COLUMN_NAME_REGISTRATION_NO+"= '"+regNo+"' and "+ VehicleContract.VehicleEntry.COLUMN_NAME_USER_ID+"= '"+userId+"'",null,null,null,sortOrder,null);

        if(c.moveToNext()) {
            Vehicle v = new Vehicle(c.getString(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_BRAND)),
                    c.getInt(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_VEHICLE_ID)),
                    c.getString(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_IMAGE_URI)),
                    c.getDouble(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_LAST_MILEAGE)),
                    c.getString(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_MODEL)),
                    c.getString(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_REGISTRATION_NO)),
                    c.getString(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_USER_ID)),
                    c.getInt(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_YEAR)),
                    c.getString(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_TYPE)));

            return  v;
        }
        else {
            return null;
        }

    }

    /**
     * This method returns a list of vehicles owned by a user
     * @param userName user name of the user
     * @return list of vehicles owned by the user who has given username
     */

    public ArrayList<Vehicle> getVehicleByUserID(String userName){
        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                VehicleContract.VehicleEntry.COLUMN_NAME_USER_ID,
                VehicleContract.VehicleEntry.COLUMN_NAME_REGISTRATION_NO,
                VehicleContract.VehicleEntry.COLUMN_NAME_BRAND,
                VehicleContract.VehicleEntry.COLUMN_NAME_MODEL,
                VehicleContract.VehicleEntry.COLUMN_NAME_LAST_MILEAGE,
                VehicleContract.VehicleEntry.COLUMN_NAME_YEAR,
                VehicleContract.VehicleEntry.COLUMN_NAME_VEHICLE_ID,
                VehicleContract.VehicleEntry.COLUMN_NAME_IMAGE_URI,
                VehicleContract.VehicleEntry.COLUMN_NAME_TYPE

        };
        String sortOrder = VehicleContract.VehicleEntry.COLUMN_NAME_VEHICLE_ID;
        Cursor c = db.query(VehicleContract.VehicleEntry.TABLE_NAME, projection,VehicleContract.VehicleEntry.COLUMN_NAME_USER_ID + "='" + userName +"'",null,null,null,sortOrder,null);

        ArrayList<Vehicle> results = new ArrayList<>();
        while (c.moveToNext())
        {

            Vehicle v = new Vehicle(c.getString(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_BRAND)),
                    c.getInt(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_VEHICLE_ID)),
                    c.getString(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_IMAGE_URI)),
                    c.getDouble(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_LAST_MILEAGE)),
                    c.getString(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_MODEL)),
                    c.getString(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_REGISTRATION_NO)),
                    c.getString(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_USER_ID)),
                    c.getInt(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_YEAR)),
                    c.getString(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_TYPE)));

            results.add(v);
        }
        return results;

    }

    /**
     * This method returns the image uri of the vehicle image
     * @param vehicleID vehicle id of the vehicle
     * @return image uri of the vehicle image of vehicle which has given vehicleId
     */
    public Uri getImageUri(int vehicleID){
        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                VehicleContract.VehicleEntry.COLUMN_NAME_IMAGE_URI,

        };
        String sortOrder = VehicleContract.VehicleEntry.COLUMN_NAME_VEHICLE_ID;
        Cursor c = db.query(VehicleContract.VehicleEntry.TABLE_NAME, projection,VehicleContract.VehicleEntry.COLUMN_NAME_VEHICLE_ID + "=" + vehicleID ,null,null,null,sortOrder,null);

        String results=null;
        while (c.moveToNext())
        {

            results=c.getString(c.getColumnIndex(VehicleContract.VehicleEntry.COLUMN_NAME_IMAGE_URI));

        }
        return Uri.parse(results);
    }

    /**
     * This method sets the image uri of the vehicle image of currently selected vehicle
     * @param uri image uri of the image
     */

    public void setImageUri(String uri){
        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(VehicleContract.VehicleEntry.COLUMN_NAME_IMAGE_URI,uri);

        db.update(VehicleContract.VehicleEntry.TABLE_NAME, values, VehicleContract.VehicleEntry.COLUMN_NAME_USER_ID + "= '"+ MainActivity.userName+"' and "+VehicleContract.VehicleEntry.COLUMN_NAME_REGISTRATION_NO+"= '"+ Home_Fragment.regNo+"'",null);
    }

    /**
     * This method delete the vehicle entry from the database which has
     * given registration number and owned by user who has given username
     * @param regNo
     * @param userName
     */
    public void deleteVehicle(String regNo, String userName) {
        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        String selection = VehicleContract.VehicleEntry.COLUMN_NAME_REGISTRATION_NO + "= ? and "+ VehicleContract.VehicleEntry.COLUMN_NAME_USER_ID+"= ?";
        String[] selectionArgs = { regNo,userName};
        db.delete(VehicleContract.VehicleEntry.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * This method updates details of the vehicle which has given registration number
     * and owned by the user who has given username
     * @param userName user name of the user
     * @param regNo registration number of the vehicle
     * @param model new model of the vehicle
     * @param brand new brand of the vehicle
     * @param year new manufactured year of the vehicle
     * @param type new type of the vehicle
     */
    public void updateVehicle(String userName,String regNo,String model,String brand,int year,String type){
        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(VehicleContract.VehicleEntry.COLUMN_NAME_MODEL,model);
        values.put(VehicleContract.VehicleEntry.COLUMN_NAME_BRAND,brand);
        values.put(VehicleContract.VehicleEntry.COLUMN_NAME_YEAR,year);
        values.put(VehicleContract.VehicleEntry.COLUMN_NAME_TYPE,type);
       // values.put(VehicleContract.VehicleEntry.COLUMN_NAME_MODEL,model);

        db.update(VehicleContract.VehicleEntry.TABLE_NAME, values, VehicleContract.VehicleEntry.COLUMN_NAME_USER_ID + "= '" + userName + "' and " + VehicleContract.VehicleEntry.COLUMN_NAME_REGISTRATION_NO + "= '" + regNo + "'", null);
    }

}
