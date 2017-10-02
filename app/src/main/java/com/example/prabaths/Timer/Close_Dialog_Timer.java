package com.example.prabaths.Timer;

import android.app.ProgressDialog;

import java.util.TimerTask;

/**
 * Created by prabath s on 5/24/2016.
 */
public class Close_Dialog_Timer extends TimerTask {
    private ProgressDialog pd;
    Thread t;
    public Close_Dialog_Timer(ProgressDialog pd,Thread t){
        this.pd=pd;
        this.t=t;
    }
    @Override
    public void run() {

        if(pd.isShowing()){
            pd.dismiss();
            t.stop();

        }
    }
}
