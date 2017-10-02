package com.example.prabaths.VO;

/**
 * Created by prabath s on 5/2/2016.
 */
public class Vehicle {
    private int id;
    private String userID;
    private String regNo;
    private String brand;
    private String model;
    private String imageURI;
    private double lastMileage;
    private int year;
    private String type;

    public Vehicle(String brand, int id, String imageURI, double lastMileage, String model, String regNo, String userID, int year,String type) {
        this.brand = brand;
        this.id = id;
        this.imageURI = imageURI;
        this.lastMileage = lastMileage;
        this.model = model;
        this.regNo = regNo;
        this.userID = userID;
        this.year = year;
        this.type=type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLastMileage() {
        return lastMileage;
    }

    public void setLastMileage(double lastMileage) {
        this.lastMileage = lastMileage;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
