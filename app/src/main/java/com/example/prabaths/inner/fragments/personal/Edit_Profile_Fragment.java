package com.example.prabaths.inner.fragments.personal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prabaths.CryptoUtils.PasswordUtils;
import com.example.prabaths.Data.DAO.UserDAO;
import com.example.prabaths.Dialogs.PickerDialog;
import com.example.prabaths.ImageHandling.CopyImage;
import com.example.prabaths.TravelBuddy.MainActivity;
import com.example.prabaths.TravelBuddy.R;
import com.example.prabaths.VO.User;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by prabath s on 5/1/2016.
 */
public class Edit_Profile_Fragment extends Fragment{

    private static TextView textView;
    private static ImageView iv;
    private MainActivity mainActivity;
    private EditText nameEditTxt;
    private EditText nicEditTxt;
    private TextView emailTxtView;
    private EditText telNoEditTxt;
    private UserDAO userDAO;
    private User user;

    @Nullable
    @Override
    /**
     *
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.edit_profile_fragment, container, false);

        userDAO=new UserDAO(getContext());
        user=userDAO.getUserByUserName(MainActivity.userName);

        iv=(ImageView) v.findViewById(R.id.profPicImView);
        nameEditTxt=(EditText) v.findViewById(R.id.nameEditTxt1);
        nicEditTxt=(EditText) v.findViewById(R.id.nicEditTxt1);
        emailTxtView=(TextView) v.findViewById(R.id.emailTxtView);
        telNoEditTxt=(EditText) v.findViewById(R.id.telNoEditTxt1);

        setUI();
        Calendar calendar=Calendar.getInstance();
        int year= calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day= calendar.get(Calendar.DAY_OF_MONTH);
       // Button btn=(Button)v.findViewById(R.id.dateSelectBtn3);
        iv=(ImageView)v.findViewById(R.id.profPicImView);

        //textView= (TextView)v.findViewById(R.id.da3);
        //textView.setText(year + " - " + month + " - " + day);
        /*btn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        buttonClicked(v);
                    }
                }
        );*/
        Button btn2=(Button)v.findViewById(R.id.changeEmailBtn);
        btn2.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){

                        showChangeEmailDialog();
                    }
                });
        Button btn3=(Button)v.findViewById(R.id.changeProfileBtn);
        btn3.setOnClickListener(
                new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);

                        startActivityForResult(Intent.createChooser(intent,"Select Image"), 1);

                    }
                }
        );

        Button submitBtn=(Button) v.findViewById(R.id.submitBtn3);
        submitBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateUser();
                    }
                }
        );
        return v;
    }

    /**
     * This method is called when save button is clicked and it updates user
     * details
     */
    public void updateUser(){
        int telNo=0;
        if(validateTelNo(telNoEditTxt.getText().toString())){
            if(!telNoEditTxt.getText().toString().equals("")){telNo=Integer.parseInt(telNoEditTxt.getText().toString());}
        }
        userDAO.updateUser(nameEditTxt.getText().toString(),nicEditTxt.getText().toString(),emailTxtView.getText().toString(),telNo);
        Toast.makeText(getContext(),"User details updated successfully",Toast.LENGTH_LONG).show();
    }

    /**
     * This method validate the given telephone number
     * @param telNo1 telephone number that is need to be validated
     * @return validity of the telephone number
     */
    private boolean validateTelNo(String telNo1){
        try{
            Integer.parseInt(telNo1);
        }
        catch (NumberFormatException e){
            if(!telNo1.equals("")){
                showAlertDialog("Invalid telephone number!!!");
            }
            return false;
        }
        return true;
    }

    /**
     * This method show the change email dialog
     */
    private void showChangeEmailDialog(){
        final Dialog dialog=new Dialog(getActivity());
        dialog.setTitle("Change Email Dialog");
        dialog.setContentView(R.layout.change_email_dialog);
        final EditText emailEditTxt=(EditText) dialog.findViewById(R.id.emailEditTxt3);
        final EditText passwordEditTxt=(EditText) dialog.findViewById(R.id.passwordEditTxt3);
        Button cancelBtn=(Button) dialog.findViewById(R.id.cancelChangeEmailBtn);
        final Button submitChangeEmailBtn=(Button) dialog.findViewById(R.id.submitChangeEmailBtn);
        cancelBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }
        );

        submitChangeEmailBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isValid(emailEditTxt,passwordEditTxt)){
                            boolean condition=false;
                            submitEmailChangeClicked(emailEditTxt.getText().toString(),passwordEditTxt.getText().toString(),condition);
                            if(condition) {
                                dialog.dismiss();
                            }
                        }
                    }
                }
        );

        dialog.show();
    }

    /**
     * This method is called in ChangeEmailDialog to validate password and email
     * @param emailEditTxt EditText view contains email
     * @param passwordEditTxt EditTxtField contains password
     * @return validity
     */
    private boolean isValid(EditText emailEditTxt,EditText passwordEditTxt){
        if(emailEditTxt.getText().toString().equals("")){
            showAlertDialog("Email Address should be given!!!");
            return false;
        }
        if(passwordEditTxt.getText().toString().equals("")){
            showAlertDialog("Password should be given!!!");
            return false;
        }
        return true;
    }

    private void  submitEmailChangeClicked(String email,String password,boolean condition){
        PasswordUtils passwordUtils=new PasswordUtils();
        if(passwordUtils.checkPassword(password,user.getPassword())){
            emailTxtView.setText(email);
            condition =true;
        }
        else{
            showAlertDialog("Incorrect password");
        }

    }

    /**
     * This method shows an alert
     * @param message message to show as the alert
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



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       // super.onActivityResult(requestCode,resultCode,data);

        if(resultCode== Activity.RESULT_OK){
            if(requestCode==1){
                //iv.setImageResource(0);
                Uri newUri=data.getData();

                CopyImage copyImage=new CopyImage();
                try {
                    Toast.makeText(getContext(),"copying image",Toast.LENGTH_LONG).show();
                    newUri=copyImage.copyFile(data.getData());
                } catch (IOException e) {
                    Toast.makeText(getContext(),"copying failed",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                iv.setImageURI(newUri);
                userDAO.setImageUri(newUri,MainActivity.userName);
                //Toast.makeText(getActivity(),"success:"+data.getData(),Toast.LENGTH_LONG).show();
            }
        }

    }

    /**
     * This method initially sets up the user interface
     */
    private void setUI(){
        userDAO=new UserDAO(getContext());
        nameEditTxt.setText(user.getName());
        nicEditTxt.setText(user.getNic());
        emailTxtView.setText(user.getEmail());
        if(user.getTelNo()!=0) {
            telNoEditTxt.setText(String.valueOf(user.getTelNo()));
        }
        if(!user.getImageUri().equals("")){
            CopyImage ci=new CopyImage();
            Uri uri= Uri.parse(user.getImageUri());
            String imPath=ci.getPath(uri);
            File imFile=new File(imPath);
            if(!imPath.equals("")){
                if(imFile.exists()) {
                    iv.setImageURI(uri);
                }
                else{
                    iv.setImageResource(R.drawable.person);
                }
            }
            else{
                iv.setImageResource(R.drawable.person);
            }
            //iv.setImageResource(0);

        }

    }


    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

}
