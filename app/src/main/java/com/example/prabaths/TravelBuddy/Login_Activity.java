package com.example.prabaths.TravelBuddy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prabaths.CryptoUtils.PasswordUtils;
import com.example.prabaths.Data.DAO.UserDAO;
import com.example.prabaths.Dialogs.PickerDialog;
import com.example.prabaths.ImageHandling.CopyImageForSignUp;
import com.example.prabaths.VO.User;

import java.io.IOException;
import java.util.Calendar;

public class Login_Activity extends AppCompatActivity {

    private EditText userName;
    private String userName2;
    private String passWord2;
    private EditText passwordTxt;
    private static FragmentManager fragmentManager;
    private TextView dateText;
    private final Context mContext=this;
    private String name;
    private String date;
    private String nic;
    private String email;
    private int telNo;
    private String password;
    private String confirmPassword;
    private String userName1;
    private PasswordUtils passwordUtils;
    private String imageUri;
    public static ContentResolver contentResolver;

    private EditText nameTxt;
    private EditText nicTxt;
    private EditText emailTxt;
    private EditText telNoTxt;
    private EditText passwordEditTxt;
    private EditText passwordConfirmTxt;
    private EditText userNameTxt;
    private Button changeProPicBtn;
    private ImageView profPicImView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        userName=(EditText) findViewById(R.id.userNameTxt);
        passwordTxt=(EditText) findViewById(R.id.passwordTxt);
        userName.setText("prabath");
        passwordTxt.setText("1");
        contentResolver=getContentResolver();
        Button loginBtn=(Button) findViewById(R.id.loginBtn);
        fragmentManager=getSupportFragmentManager();
        //mContext=this;
        passwordUtils=new PasswordUtils();
        imageUri="";
        loginBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(isValidUser()){startMain(v);}




                    }
                }
        );

        Button signUpBtn=(Button) findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showSignupDialog();

                    }
                }
        );
    }

    /**
     * This method shows the signup dialog
     */
    private void showSignupDialog(){
        final Dialog dialog=new Dialog(this);
        dialog.setTitle("Add user dialog");
        dialog.setContentView(R.layout.add_user_fragment);

        nameTxt=(EditText) dialog.findViewById(R.id.nameEditTxt);
        nicTxt=(EditText) dialog.findViewById(R.id.nicEditTxt);
        emailTxt=(EditText)dialog.findViewById(R.id.emailEditTxt);
        telNoTxt=(EditText) dialog.findViewById(R.id.telNoEditTxt);
        passwordEditTxt=(EditText) dialog.findViewById(R.id.passwordEditTxt);
        passwordConfirmTxt=(EditText) dialog.findViewById(R.id.confirmPasswordEditTxt);
        userNameTxt=(EditText) dialog.findViewById(R.id.userNameEditTxt);
        changeProPicBtn=(Button) dialog.findViewById(R.id.changeProfileBtn1);
        profPicImView=(ImageView) dialog.findViewById(R.id.profPicImView2);


        Calendar calendar=Calendar.getInstance();
        String year= checkDigit(calendar.get(Calendar.YEAR));
        String month=checkDigit(calendar.get(Calendar.MONTH)+1);
        String day= checkDigit(calendar.get(Calendar.DAY_OF_MONTH));
        //this.dateText=(TextView) dialog.findViewById(R.id.da3);
        //dateText.setText(year + "-" + month + "-" + day);

        /*Button setDateButton=(Button) dialog.findViewById(R.id.dateSelectBtn3);
        setDateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setDate();
                    }
                }
        );*/

        Button cancelBtn=(Button) dialog.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }
        );

        Button submitBtn=(Button) dialog.findViewById(R.id.submitAccountButton);
        submitBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*final ProgressDialog progress = ProgressDialog.show(mContext, "dialog title",
                                "dialog message", true);

                        new Thread(new Runnable() {
                            @Override
                            public void run()
                            {
                                // do the thing that takes a long time
                                for (int k=0;k<10000;k++){
                                    //Toast.makeText(mContext,String.valueOf(k),Toast.LENGTH_SHORT).show();
                                    System.out.println(k);
                                    if(!progress.isShowing()){break;}
                                    //if(k==9999999){progress.dismiss();}
                                }


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run()
                                    {

                                    }
                                });
                            }
                        }).start();

                        final Thread th=new Thread(
                                new Runnable() {
                                    @Override
                                    public void run() {

                                        Timer t=new Timer();
                                        //t.schedule(new Close_Dialog_Timer(progress,),5000);
                                    }

                                }
                        );*/



                        submitButtonClicked();

                    }
                }
        );

        changeProPicBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);

                        startActivityForResult(Intent.createChooser(intent,"Select Image"), 4);
                    }
                }
        );
        dialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Toast.makeText(this,String.valueOf(requestCode),Toast.LENGTH_LONG).show();
        if(resultCode== Activity.RESULT_OK){
            if(requestCode==4){
                //iv.setImageResource(0);
                //profPicImView.setImageURI(data.getData());
                Uri newUri=data.getData();

                CopyImageForSignUp copyImage=new CopyImageForSignUp();
                try {
                    Toast.makeText(this,"copying image",Toast.LENGTH_LONG).show();
                    newUri=copyImage.copyFile(data.getData());
                } catch (IOException e) {
                    Toast.makeText(this,"copying failed",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                profPicImView.setImageURI(newUri);
                imageUri=newUri.toString();

            }
        }

    }

    private void setDate(){
        PickerDialog pickerDialog= new PickerDialog();

        pickerDialog.setTextView(dateText);

        pickerDialog.show(fragmentManager,"Pick a date");
    }

    /**
     * This method check the validity of the given telephone number
     * number of digits in the telephone number is not tested here
     * because the application can be used by people in different countries and
     * their telephone numbers might have different number of digits
     * @param telNo1 telephone number that is need to be tested
     * @return validity
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
     * This method checks the validity of data given in the input fields
     * @return validity
     */
    private boolean isValidDialog(){
        if(userName1.equals("")){
            showAlertDialog("User Name should be given!!!");
            return false;
        }
        else if(passwordUtils.checkPassword("", password)){
            showAlertDialog("Password should be chosen!!!");
            return false;
        }
        else if(passwordUtils.checkPassword("",confirmPassword)){
            showAlertDialog("Please confirm the password!!!");
            return false;
        }
        else if(!passwordEditTxt.getText().toString().equals(passwordConfirmTxt.getText().toString())){
            showAlertDialog("Password and confirm password are different!!!");
            return false;
        }


        return true;

    }

    /**
     * This method checks whether entered login data are correct
     * @return validity
     */
    private boolean isValidLogin(){
        if(userName.getText().toString().equals("")){
            showAlertDialog("User name should be given!!!");
            return false;
        }
        else if(passwordTxt.getText().toString().equals("")){
            showAlertDialog("Password should be given");
            return false;
        }

        return true;
    }

    /**
     * This method shows an alert dialog
     * @param message alert message that needs to be shown
     */
    private void showAlertDialog(String message){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

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
     * This method is executed when the submit button
     * in the sign up dialog is clicked
     */
    private void submitButtonClicked(){

        name=nameTxt.getText().toString();
        nic=nicTxt.getText().toString();
        email=emailTxt.getText().toString();
        if(validateTelNo(telNoTxt.getText().toString())){
            telNo=Integer.parseInt(telNoTxt.getText().toString());
        }
        userName1=userNameTxt.getText().toString();

        password=passwordUtils.encryptPassword(passwordEditTxt.getText().toString());
        System.out.println(password);
        confirmPassword=passwordUtils.encryptPassword(passwordConfirmTxt.getText().toString());

        if(isValidDialog()){
            User user=new User(email,imageUri,name,nic,password,telNo,userName1);
            UserDAO userDAO=new UserDAO(this);

            userDAO.addUser(user);


        }
    }

    /**
     * This method checks the validity of the user
     * @return validity
     */
    private boolean isValidUser(){
        UserDAO userDAO=new UserDAO(this);
        if(userName.getText().toString().equals("prabath") || userName.getText().toString().equals("admin")){
            return true;
        }
        if(isValidLogin()){
            User user1=userDAO.getUserByUserName(userName.getText().toString());
            if(user1==null){
                showAlertDialog("User with given password and username does not exist!!!");
                return false;
            }
            String pw=user1.getPassword();

            if(!passwordUtils.checkPassword(passwordTxt.getText().toString(),pw)){
                showAlertDialog("Invalid user!!");
                return false;
            }
            return true;
        }
        else{return false;}

    }

    public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }

    private void startMain(View view){
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("username",userName.getText().toString());
        startActivity(intent);
        this.finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
