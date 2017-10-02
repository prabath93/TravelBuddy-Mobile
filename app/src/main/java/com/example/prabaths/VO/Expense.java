package com.example.prabaths.VO;

import java.util.Date;

/**
 * Created by prabath s on 5/2/2016.
 */
public class Expense {
    private int id;
    private Date date;
    private int type;
    private double cost;
    private int partialTank;
    private double unitPrice;
    private double odometerReading;
    private double volume;
    private String location;
    private String userID;
    private String carRegNo;
    private String notes;
    private double economy;

    public Expense(String carRegNo, double cost, Date date, int id, String location, String notes, double odometerReading, int partialTank, int type, double unitPrice, String userID, double volume) {
        this.carRegNo = carRegNo;
        this.cost = cost;
        this.date = date;
        this.id = id;
        this.location = location;
        this.notes = notes;
        this.odometerReading = odometerReading;
        this.partialTank = partialTank;
        this.type = type;
        this.unitPrice = unitPrice;
        this.userID = userID;
        this.volume = volume;
    }

    public double getEconomy() {
        return economy;
    }

    public void setEconomy(double economy) {
        this.economy = economy;
    }

    public String getCarRegNo() {
        return carRegNo;
    }

    public void setCarRegNo(String carRegNo) {
        this.carRegNo = carRegNo;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getOdometerReading() {
        return odometerReading;
    }

    public void setOdometerReading(double odometerReading) {
        this.odometerReading = odometerReading;
    }

    public int getPartialTank() {
        return partialTank;
    }

    public void setPartialTank(int partialTank) {
        this.partialTank = partialTank;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
