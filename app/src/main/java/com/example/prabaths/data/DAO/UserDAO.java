package com.example.prabaths.Data.DAO;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Toast;

import com.example.prabaths.Data.SQLiteHelper;
import com.example.prabaths.Data.UserContract;
import com.example.prabaths.Data.VehicleContract;
import com.example.prabaths.TravelBuddy.MainActivity;
import com.example.prabaths.VO.User;
import com.example.prabaths.VO.Vehicle;

/**
 * Created by prabath s on 5/2/2016.
 */
public class UserDAO {

    Context context;
    public UserDAO(Context context){
        this.context=context;
    }

    /**
     * This method returns a user which has the given username
     * @param userName User name of the user
     * @return user who has given user name
     */

    public User getUserByUserName(String userName) {

        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                UserContract.UserEntry.COLUMN_NAME_NAME,
                UserContract.UserEntry.COLUMN_NAME_USER_NAME,
                UserContract.UserEntry.COLUMN_NAME_EMAIL,
                UserContract.UserEntry.COLUMN_NAME_IMAGE_URI,
                UserContract.UserEntry.COLUMN_NAME_PASSWORD,
                UserContract.UserEntry.COLUMN_NAME_NIC_NO,
                UserContract.UserEntry.COLUMN_NAME_TELEPHONE_NO
        };
        String sortOrder = UserContract.UserEntry.COLUMN_NAME_NIC_NO + " DESC";
        Cursor c = db.query(UserContract.UserEntry.TABLE_NAME, projection, UserContract.UserEntry.COLUMN_NAME_USER_NAME + "='" + userName + "'", null, null, null, sortOrder, null);

        if (c.moveToNext()) {

            User user=new User(c.getString(c.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_EMAIL)),
                    c.getString(c.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_IMAGE_URI)),
                    c.getString(c.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_NAME)),
                    c.getString(c.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_NIC_NO)),
                    c.getString(c.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_PASSWORD)),
                    c.getInt(c.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_TELEPHONE_NO)),
                    c.getString(c.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_USER_NAME)));

            return user;
        } else {
            return null;
        }
    }


    /**
     * This method adds a user to the database
     * @param user Object contains user details
     */
    public void addUser(User user){

            SQLiteHelper helper = SQLiteHelper.getInstance(context);
            SQLiteDatabase db = helper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(UserContract.UserEntry.COLUMN_NAME_EMAIL,user.getEmail());
            values.put(UserContract.UserEntry.COLUMN_NAME_IMAGE_URI,user.getImageUri());
            values.put(UserContract.UserEntry.COLUMN_NAME_NAME, user.getName());
            values.put(UserContract.UserEntry.COLUMN_NAME_NIC_NO,user.getNic());
            values.put(UserContract.UserEntry.COLUMN_NAME_PASSWORD,user.getPassword());
            values.put(UserContract.UserEntry.COLUMN_NAME_TELEPHONE_NO,user.getTelNo());
            values.put(UserContract.UserEntry.COLUMN_NAME_USER_NAME, user.getUserName());


        try {
            db.insertOrThrow(UserContract.UserEntry.TABLE_NAME, null, values);
            Toast.makeText(context, "User successfully added", Toast.LENGTH_LONG).show();
        }
        catch(android.database.sqlite.SQLiteConstraintException e){
            showAlertDialog("User with given username already exists, please try again with new username!!!");
        }



    }

    /**
     * This method updates the user details of current user
     * @param name new name of the user
     * @param nic new national identity card number of the user
     * @param emailAddress new email address of the user
     * @param telNo new telephone number of the user
     */

    public void updateUser(String name,String nic,String emailAddress,int telNo){
        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(UserContract.UserEntry.COLUMN_NAME_NAME,name);
        values.put(UserContract.UserEntry.COLUMN_NAME_NIC_NO,nic);
        values.put(UserContract.UserEntry.COLUMN_NAME_EMAIL,emailAddress);
        values.put(UserContract.UserEntry.COLUMN_NAME_TELEPHONE_NO,telNo);
        // values.put(VehicleContract.VehicleEntry.COLUMN_NAME_MODEL,model);

        db.update(UserContract.UserEntry.TABLE_NAME, values, UserContract.UserEntry.COLUMN_NAME_USER_NAME + "= '" + MainActivity.userName + "'", null);
    }

    /**
     * This method update the image uri of the profile picture of the current user
     * @param uri image uri of the profile picture
     * @param username username of the current user
     */

    public void setImageUri(Uri uri, String username){
        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(UserContract.UserEntry.COLUMN_NAME_IMAGE_URI,uri.toString());

        db.update(UserContract.UserEntry.TABLE_NAME, values, UserContract.UserEntry.COLUMN_NAME_USER_NAME+ "= '"+username+"'",null);
    }


    /**
     * This method displays alert messages
     * @param message alert message
     */
    private void showAlertDialog(String message){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

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


    /**
     * This method returns the password of currently logged in user
     * @return password
     */
    public String getPassword(){
        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String password="";

        Cursor c=db.rawQuery("select "+ UserContract.UserEntry.COLUMN_NAME_PASSWORD +" from "+ UserContract.UserEntry.TABLE_NAME+" where "+ UserContract.UserEntry.COLUMN_NAME_USER_NAME+"= '"+MainActivity.userName+"'",null);
        while (c.moveToNext())
        {
            password=c.getString(c.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_PASSWORD));
        }
        c.close();
        return password;
    }

    /**
     * This method updates the password entry in the database of the currently logged in user
     * @param password new password
     */
    public void updatePassword(String password){
        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(UserContract.UserEntry.COLUMN_NAME_PASSWORD,password);

        db.update(UserContract.UserEntry.TABLE_NAME, values, UserContract.UserEntry.COLUMN_NAME_USER_NAME + "= '" + MainActivity.userName + "'", null);
        Toast.makeText(context,"Password changed successfully!",Toast.LENGTH_LONG).show();
    }
}
