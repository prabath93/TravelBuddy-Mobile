package com.example.prabaths.Data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.prabaths.Data.InstantDataContract;
import com.example.prabaths.Data.SQLiteHelper;
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
public class InstantDataDAO {
    private Context context;

    public InstantDataDAO(Context context) {
        this.context = context;
    }

    /**
     * This method returns all instant data records in the database
     * @return list of instant data
     */

    private List<InstantData> getAllInstantData(){
        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                InstantDataContract.InstantDataEntry.COLUMN_NAME_UNIT_PRICE,
                InstantDataContract.InstantDataEntry.COLUMN_NAME_DATE,
                InstantDataContract.InstantDataEntry.COLUMN_NAME_USER_NAME,
                InstantDataContract.InstantDataEntry.COLUMN_NAME_REGNO,

        };
        String sortOrder = InstantDataContract.InstantDataEntry.COLUMN_NAME_REGNO;
        Cursor c = db.query(InstantDataContract.InstantDataEntry.TABLE_NAME, projection,null,null,null,null,sortOrder,null);

        ArrayList<InstantData> results = new ArrayList<>();
        while (c.moveToNext())
        {
            String dateSring=c.getString(c.getColumnIndex(InstantDataContract.InstantDataEntry.COLUMN_NAME_DATE));
            Date date=null;
            try {
                 date=SQLiteHelper.dateFromString(dateSring);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            InstantData idata = new InstantData(c.getString(c.getColumnIndex(InstantDataContract.InstantDataEntry.COLUMN_NAME_USER_NAME)),
                    c.getString(c.getColumnIndex(InstantDataContract.InstantDataEntry.COLUMN_NAME_REGNO)),
                    c.getDouble(c.getColumnIndex(InstantDataContract.InstantDataEntry.COLUMN_NAME_UNIT_PRICE)),
                    date,
                    c.getDouble(c.getColumnIndex(InstantDataContract.InstantDataEntry.COLUMN_NAME_FINAL_ODOMETER_READING))
                    );

            results.add(idata);
        }
        return results;

    }


    /**
     * This method returns instant data entry related to the current user
     * @return instant data record related to the current user
     * @throws ParseException
     */

    public InstantData getInstantDataByUserName() throws ParseException {
        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                InstantDataContract.InstantDataEntry.COLUMN_NAME_USER_NAME,
                InstantDataContract.InstantDataEntry.COLUMN_NAME_REGNO,
                InstantDataContract.InstantDataEntry.COLUMN_NAME_UNIT_PRICE,
                InstantDataContract.InstantDataEntry.COLUMN_NAME_DATE,
                InstantDataContract.InstantDataEntry.COLUMN_NAME_FINAL_ODOMETER_READING
        };
        String sortOrder = InstantDataContract.InstantDataEntry.COLUMN_NAME_USER_NAME+ " DESC";
        Cursor c = db.query(InstantDataContract.InstantDataEntry.TABLE_NAME, projection,  InstantDataContract.InstantDataEntry.COLUMN_NAME_REGNO+"= '"+ Home_Fragment.regNo+"' and "+ InstantDataContract.InstantDataEntry.COLUMN_NAME_USER_NAME+"= '"+ MainActivity.userName+"'",null,null,null,sortOrder,null);

        if(c.moveToNext()) {
            Date date=helper.dateFromString(c.getString(c.getColumnIndex(InstantDataContract.InstantDataEntry.COLUMN_NAME_DATE)));
            InstantData idata = new InstantData(c.getString(c.getColumnIndex(InstantDataContract.InstantDataEntry.COLUMN_NAME_USER_NAME)),
                    c.getString(c.getColumnIndex(InstantDataContract.InstantDataEntry.COLUMN_NAME_REGNO)),
                    c.getDouble(c.getColumnIndex(InstantDataContract.InstantDataEntry.COLUMN_NAME_UNIT_PRICE)),
                    date,
                    c.getDouble(c.getColumnIndex(InstantDataContract.InstantDataEntry.COLUMN_NAME_FINAL_ODOMETER_READING)));

            return  idata;
        }
        else {
            return null;
        }

    }

    /**
     * This method adds a instant data record to the database under current user
     * @param idata Instant data object
     */

    public void addInstantData(InstantData idata){

        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        String date=helper.dateToString(idata.getDate(),context);
        ContentValues values = new ContentValues();
        values.put(InstantDataContract.InstantDataEntry.COLUMN_NAME_USER_NAME,idata.getUserName());
        values.put(InstantDataContract.InstantDataEntry.COLUMN_NAME_REGNO,idata.getRegNo());
        values.put(InstantDataContract.InstantDataEntry.COLUMN_NAME_DATE,date);
        values.put(InstantDataContract.InstantDataEntry.COLUMN_NAME_UNIT_PRICE,idata.getUnitPrice());
        values.put(InstantDataContract.InstantDataEntry.COLUMN_NAME_FINAL_ODOMETER_READING,idata.getOdometerReading());



        db.insert(InstantDataContract.InstantDataEntry.TABLE_NAME,null,values);
    }

    /**
     * This method update the instant data entry in the database related to the current user
     * @param idata Instant data object
     */

    public void updateInstantData(InstantData idata){

        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        String date=helper.dateToString(idata.getDate(), context);

        ContentValues values = new ContentValues();

        values.put(InstantDataContract.InstantDataEntry.COLUMN_NAME_DATE,date);
        values.put(InstantDataContract.InstantDataEntry.COLUMN_NAME_UNIT_PRICE,idata.getUnitPrice());
        values.put(InstantDataContract.InstantDataEntry.COLUMN_NAME_FINAL_ODOMETER_READING,idata.getOdometerReading());
        // values.put(VehicleContract.VehicleEntry.COLUMN_NAME_MODEL,model);

        db.update(InstantDataContract.InstantDataEntry.TABLE_NAME, values, InstantDataContract.InstantDataEntry.COLUMN_NAME_USER_NAME + "= '" + idata.getUserName() + "' and " + InstantDataContract.InstantDataEntry.COLUMN_NAME_REGNO + "= '" + idata.getRegNo() + "'", null);

    }
}
