package com.example.prabaths.inner.fragments.personal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.prabaths.CryptoUtils.PasswordUtils;
import com.example.prabaths.Data.DAO.UserDAO;
import com.example.prabaths.TravelBuddy.R;

/**
 * Created by prabath s on 5/8/2016.
 */
public class Change_Password_Fragment extends Fragment {
    EditText oldPasswordTxt;
    EditText newPassWordTxt;
    EditText confirmNewPasswordTxt;
    UserDAO userDAO;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.change_password_fragment,container,false);
        Button btn=(Button) v.findViewById(R.id.change_password_btn);
        oldPasswordTxt=(EditText) v.findViewById(R.id.oldPasswordTxt);
        newPassWordTxt=(EditText)v.findViewById(R.id.newPasswordTxt);
        confirmNewPasswordTxt=(EditText) v.findViewById(R.id.confirmNewPasswordTxt);
        userDAO=new UserDAO(getContext());

        btn.setOnClickListener(
                new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        submitButtonPressed();
                    }
                }
        );
        return v;
    }

    private boolean isValid(){



        String oldPassword=userDAO.getPassword();
        if(!newPassWordTxt.getText().toString().equals(confirmNewPasswordTxt.getText().toString())){
            showAlertDialog("New password and confirm password are not equal!!!");
            return false;
        }
        else if(newPassWordTxt.getText().toString().equals("")){
            showAlertDialog("Password can not be an empty string!!!");
            return false;
        }
        else if(!PasswordUtils.checkPassword(oldPasswordTxt.getText().toString(),oldPassword)){
            showAlertDialog("Old password is wrong!!!");
            return false;
        }

        return true;
    }


    private void submitButtonPressed(){
        if(isValid()){
            userDAO.updatePassword(PasswordUtils.encryptPassword(newPassWordTxt.getText().toString()));
            newPassWordTxt.setText("");
            oldPasswordTxt.setText("");
            confirmNewPasswordTxt.setText("");
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
}
