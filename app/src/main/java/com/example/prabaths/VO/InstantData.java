package com.example.prabaths.VO;

import java.util.Date;

/**
 * Created by prabath s on 5/2/2016.
 */
public class InstantData {
    private Date date;
    private double unitPrice;
    private String regNo;
    private String userName;
    private double odometerReading;

    public InstantData(String userName, String regNo, double unitPrice, Date date,double odometerReading) {
        this.regNo = regNo;
        this.date = date;
        this.unitPrice = unitPrice;
        this.userName = userName;
        this.odometerReading=odometerReading;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getOdometerReading() {
        return odometerReading;
    }

    public void setOdometerReading(double odometerReading) {
        this.odometerReading = odometerReading;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
