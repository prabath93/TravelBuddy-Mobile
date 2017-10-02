package com.example.prabaths.Data.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.prabaths.Data.ExpenseContract;
import com.example.prabaths.Data.SQLiteHelper;
import com.example.prabaths.Data.VehicleContract;
import com.example.prabaths.Fragments.Home_Fragment;
import com.example.prabaths.TravelBuddy.MainActivity;
import com.example.prabaths.VO.Expense;
import com.example.prabaths.VO.FuelExpense;
import com.example.prabaths.VO.OtherExpense;
import com.example.prabaths.VO.Vehicle;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by prabath s on 5/2/2016.
 */
public class ExpenseDAO {


    Context context;
    public ExpenseDAO(Context context){
        this.context=context;
    }

    /**
     * This method adds a fuel consumption record to the database
     * @param fuelExpense object contains the details that is needed to be added to the database
     *
     */

    public void addFuelExpense(FuelExpense fuelExpense){

        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_COST,fuelExpense.getCost());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_LOCATION,fuelExpense.getLocation());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_DATE,helper.dateToString(fuelExpense.getDate(), context));
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_VOLUME,fuelExpense.getVolume());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_ODOMETER_READING,fuelExpense.getOdometerReading());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_TYPE,fuelExpense.getType());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_REG_NO,fuelExpense.getCarRegNo());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_PARTIAL_TANK,fuelExpense.getPartialTank());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_USER_ID,fuelExpense.getUserID());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_UNIT_PRICE,fuelExpense.getUnitPrice());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_NOTES,fuelExpense.getNotes());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_ECONOMY,fuelExpense.getEconomy());

        db.insert(ExpenseContract.ExpenseEntry.TABLE_NAME,null,values);
    }

    /**
     *This method adds other kind of expense( repair or service) to the database
     * @param expense object contains the details that is needed to be added to the database
     */
    public void addOtherExpense(OtherExpense expense){

        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_COST,expense.getCost());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_LOCATION,expense.getLocation());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_DATE,helper.dateToString(expense.getDate(), context));
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_VOLUME,expense.getVolume());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_ODOMETER_READING,expense.getOdometerReading());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_TYPE,expense.getType());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_REG_NO,expense.getCarRegNo());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_PARTIAL_TANK,expense.getPartialTank());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_USER_ID,expense.getUserID());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_UNIT_PRICE,expense.getUnitPrice());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_NOTES,expense.getNotes());

        db.insert(ExpenseContract.ExpenseEntry.TABLE_NAME,null,values);
    }



    /**
     * This method returns all expenses of a vehicle that have the given regNo
     * @param regNo registration number of the vehicle
     * @return List of expenses related with the vehicle
     */

    public ArrayList<Expense> getAllExpensesOfAVehicle(String regNo) {
        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c=db.rawQuery("select * from "+ ExpenseContract.ExpenseEntry.TABLE_NAME+" where "+ ExpenseContract.ExpenseEntry.COLUMN_NAME_USER_ID+"= '"+MainActivity.userName+"' and "+ ExpenseContract.ExpenseEntry.COLUMN_NAME_REG_NO+"= '"+ Home_Fragment.regNo+"' order by "+ ExpenseContract.ExpenseEntry.COLUMN_NAME_ID+" DESC",null);
        ArrayList<Expense> results = new ArrayList<>();
        while (c.moveToNext())
        {

            Date date=null;
            try {
                date=SQLiteHelper.dateFromString(c.getString(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_DATE)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Expense expense=new Expense(c.getString(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_REG_NO)),
                    c.getDouble(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_COST)),
                    date,
                    c.getInt(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_ID)),
                    c.getString(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_LOCATION)),
                    c.getString(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_NOTES)),
                    c.getDouble(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_ODOMETER_READING)),
                    c.getInt(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_PARTIAL_TANK)),
                    c.getInt(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_TYPE)),
                    c.getDouble(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_UNIT_PRICE)),
                    c.getString(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_USER_ID)),
                    c.getDouble(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_VOLUME))
                    );
            expense.setEconomy(c.getDouble(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_ECONOMY)));

            results.add(expense);
        }
        c.close();
        return results;
    }


    /**
     * This method deletes the records of a vehicle that has given regNo and owned by the owner who has the given username
     * @param regNo  registration number of the vehicle
     * @param userName user name of the vehicle owner
     */
    public void deleteRecordsOfAVehicle(String regNo,String userName){
        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        String selection = ExpenseContract.ExpenseEntry.COLUMN_NAME_REG_NO + "= ? and "+ ExpenseContract.ExpenseEntry.COLUMN_NAME_USER_ID+"= ?";
        String[] selectionArgs = { regNo,userName};
        db.delete(ExpenseContract.ExpenseEntry.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * This method returns list of expense records of a vehicle
     * @param regNo registration number of the vehicle
     * @return list of fuel expense records
     */
    public ArrayList<Expense> getFuelExpensesOfAVehicle(String regNo) {
        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c=db.rawQuery("select * from "+ ExpenseContract.ExpenseEntry.TABLE_NAME+" where "+ ExpenseContract.ExpenseEntry.COLUMN_NAME_USER_ID+"= '"+MainActivity.userName+"' and "+ ExpenseContract.ExpenseEntry.COLUMN_NAME_REG_NO+"= '"+ Home_Fragment.regNo+"' and "+ ExpenseContract.ExpenseEntry.COLUMN_NAME_TYPE+"= 1 order by "+ ExpenseContract.ExpenseEntry.COLUMN_NAME_DATE,null);
        ArrayList<Expense> results = new ArrayList<>();
        while (c.moveToNext())
        {

            Date date=null;
            try {
                date=SQLiteHelper.dateFromString(c.getString(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_DATE)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Expense expense=new Expense(c.getString(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_REG_NO)),
                    c.getDouble(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_COST)),
                    date,
                    c.getInt(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_ID)),
                    c.getString(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_LOCATION)),
                    c.getString(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_NOTES)),
                    c.getDouble(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_ODOMETER_READING)),
                    c.getInt(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_PARTIAL_TANK)),
                    c.getInt(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_TYPE)),
                    c.getDouble(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_UNIT_PRICE)),
                    c.getString(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_USER_ID)),
                    c.getDouble(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_VOLUME))
            );
            expense.setEconomy(c.getDouble(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_ECONOMY)));

            results.add(expense);
        }
        c.close();
        return results;
    }


    /**
     * This method returns fuel expense records of a vehicle which has economy value>0
     * @param regNo registration number of the vehicle
     * @return list of fuel expense records
     */
    public ArrayList<Expense> getFuelExpensesOfAVehicleWithEconomy(String regNo) {
        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();


        Cursor c=db.rawQuery("select * from "+ ExpenseContract.ExpenseEntry.TABLE_NAME+" where "+ ExpenseContract.ExpenseEntry.COLUMN_NAME_USER_ID+"= '"+MainActivity.userName+"' and "+ ExpenseContract.ExpenseEntry.COLUMN_NAME_REG_NO+"= '"+ Home_Fragment.regNo+"' and "+ ExpenseContract.ExpenseEntry.COLUMN_NAME_TYPE+"= 1 and "+ ExpenseContract.ExpenseEntry.COLUMN_NAME_ECONOMY+"!=0 order by "+ ExpenseContract.ExpenseEntry.COLUMN_NAME_DATE,null);
        ArrayList<Expense> results = new ArrayList<>();
        while (c.moveToNext())
        {

            Date date=null;
            try {
                date=SQLiteHelper.dateFromString(c.getString(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_DATE)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Expense expense=new Expense(c.getString(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_REG_NO)),
                    c.getDouble(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_COST)),
                    date,
                    c.getInt(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_ID)),
                    c.getString(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_LOCATION)),
                    c.getString(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_NOTES)),
                    c.getDouble(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_ODOMETER_READING)),
                    c.getInt(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_PARTIAL_TANK)),
                    c.getInt(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_TYPE)),
                    c.getDouble(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_UNIT_PRICE)),
                    c.getString(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_USER_ID)),
                    c.getDouble(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_VOLUME))
            );
            expense.setEconomy(c.getDouble(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_ECONOMY)));

            results.add(expense);
        }
        c.close();
        return results;
    }

    /**
     * This method returns all other expenses of a vehicle which has given regNo
     * @param regNo registration number of the vehicle
     * @return List of expenses related to the vehicle
     */
    public ArrayList<Expense> getOtherExpensesOfAVehicle(String regNo){
        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();




        Cursor c=db.rawQuery("select * from "+ ExpenseContract.ExpenseEntry.TABLE_NAME+" where "+ ExpenseContract.ExpenseEntry.COLUMN_NAME_USER_ID+"= '"+MainActivity.userName+"' and "+ ExpenseContract.ExpenseEntry.COLUMN_NAME_REG_NO+"= '"+ Home_Fragment.regNo+"' and "+ ExpenseContract.ExpenseEntry.COLUMN_NAME_TYPE+"!= 1 order by "+ ExpenseContract.ExpenseEntry.COLUMN_NAME_DATE,null);
        ArrayList<Expense> results = new ArrayList<>();
        while (c.moveToNext())
        {

            Date date=null;
            try {
                date=SQLiteHelper.dateFromString(c.getString(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_DATE)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Expense expense=new Expense(c.getString(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_REG_NO)),
                    c.getDouble(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_COST)),
                    date,
                    c.getInt(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_ID)),
                    c.getString(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_LOCATION)),
                    c.getString(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_NOTES)),
                    c.getDouble(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_ODOMETER_READING)),
                    c.getInt(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_PARTIAL_TANK)),
                    c.getInt(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_TYPE)),
                    c.getDouble(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_UNIT_PRICE)),
                    c.getString(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_USER_ID)),
                    c.getDouble(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_VOLUME))
            );
            expense.setEconomy(c.getDouble(c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_ECONOMY)));

            results.add(expense);
        }
        c.close();
        return results;
    }



}
