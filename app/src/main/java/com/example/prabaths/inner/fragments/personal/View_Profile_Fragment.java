package com.example.prabaths.inner.fragments.personal;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prabaths.Data.DAO.UserDAO;
import com.example.prabaths.Data.SQLiteHelper;
import com.example.prabaths.ImageHandling.CopyImage;
import com.example.prabaths.TravelBuddy.MainActivity;
import com.example.prabaths.TravelBuddy.R;
import com.example.prabaths.VO.User;

import java.io.File;

/**
 * Created by prabath s on 5/2/2016.
 */
public class View_Profile_Fragment extends Fragment {

    private MainActivity mainActivity;
    private TextView nameTxt;
    private TextView nicTxt;
    private TextView emailTxt;
    private TextView telNoTxt;
    private ImageView iv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.profile_fragment,container,false);
        nameTxt=(TextView) v.findViewById(R.id.nameTxt);
        nicTxt=(TextView) v.findViewById(R.id.nicTxt);
        emailTxt=(TextView) v.findViewById(R.id.emailTxt);
        telNoTxt=(TextView) v.findViewById(R.id.telNoTxt3);
        iv=(ImageView) v.findViewById(R.id.profPicImView1);
        setUI();
        return v;
    }

    private void setUI(){
        UserDAO userDAO=new UserDAO(getContext());
        User user= userDAO.getUserByUserName(MainActivity.userName);
        SQLiteHelper sqLiteHelper=SQLiteHelper.getInstance(getContext());
        nameTxt.setText(user.getName());
        nicTxt.setText(user.getNic());
        emailTxt.setText(user.getEmail());
        if(user.getTelNo()!=0) {
            telNoTxt.setText(String.valueOf(user.getTelNo()));
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
