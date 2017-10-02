package com.example.prabaths.GPS;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;

/**
 * Created by prabath s on 5/13/2016.
 */
public class GPSTracker1 extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    double lat, lon;
    private final Context mContext;
    private final EditText editText;
    boolean gpsEnabled;
    boolean internetEnabled;
    protected LocationManager locationManager;


    public GPSTracker1(Context context,EditText editText) {
        this.mContext=context;

        buildGoogleApiClient();
        mGoogleApiClient.connect();
        this.editText=editText;
        locationManager = (LocationManager) mContext
                .getSystemService(LOCATION_SERVICE);

    }


    public Location getLocation(){
        /*Thread t=new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        boolean condition=true;
                        while(condition){
                            if(mLastLocation!=null){
                                condition=false;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
        );
        t.start();*/
        //Toast.makeText(mActivity.getApplicationContext(),String.valueOf(mLastLocation.getLatitude()),Toast.LENGTH_LONG).show();
        return mLastLocation;

    }

    /**
     * This method set the current location of the device in to a TextView
     */
    public void setLocation(){
        if(mGoogleApiClient.isConnected()){
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(100); // Update location every second

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                lat = mLastLocation.getLatitude();
                lon = mLastLocation.getLongitude();
                editText.setText(getAddress(lat,lon));
                //Toast.makeText(mContext,getAddress(lat,lon),Toast.LENGTH_LONG).show();

            }
        }
        else{
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

            // Setting Dialog Title
            alertDialog.setTitle("GPS settings");

            // Setting Dialog Message
            alertDialog.setMessage("GPS is unable to get location information.Please try again in few seconds or enter the location manually!");

            // On pressing Settings button
            alertDialog.setNeutralButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            // Showing Alert Message
            alertDialog.show();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(100); // Update location every second

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);


        Toast.makeText(mContext,"Connected",Toast.LENGTH_LONG).show();
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        //Toast.makeText(mContext,String.valueOf(mLastLocation.getLatitude()),Toast.LENGTH_LONG).show();
        if (mLastLocation != null) {
            lat = mLastLocation.getLatitude();
            lon = mLastLocation.getLongitude();
            editText.setText(getAddress(lat,lon));
            //Toast.makeText(mContext,getAddress(lat,lon),Toast.LENGTH_LONG).show();

        }

    }

    /**
     * This method returns the status of internet(whether enabled or disabled)
     * @return status of internet
     */
    public boolean isInternetEnabled(){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * This method returns the status of gps(whether enabled or disabled)
     * @return status of gps
     */
    public boolean isGPSEnabled(){
        gpsEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        return gpsEnabled;

    }

    public String getAddress(double latitude,double longitude) {

        if(isInternetEnabled()){
        Location location = getLocation();
        List<Address> address = null;

        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            //Toast.makeText(mContext,String.valueOf(latitude), Toast.LENGTH_SHORT).show();

            Geocoder geocoder;

            geocoder = new Geocoder(mContext);

            try {
                address = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Toast.makeText(mContext, address.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();

            return address.get(0).getAddressLine(0);
        }
        }
        else{return "";}
        /*if(address==null){
            return "";
        }
        else{*/
        //}
        return "";
    }



    @Override
    public void onConnectionSuspended(int i) {
        buildGoogleApiClient();
    }

    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

