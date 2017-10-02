package com.example.prabaths.VO;

/**
 * Created by prabath s on 5/2/2016.
 */
public class User {
    private String nic;
    private String userName;
    private String email;
    private int telNo;
    private String password;
    private String name;
    private String imageUri;

    public User(String email, String imageUri, String name, String nic, String password, int telNo, String userName) {
        this.email = email;
        this.imageUri = imageUri;
        this.name = name;
        this.nic = nic;
        this.password = password;
        this.telNo = telNo;
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTelNo() {
        return telNo;
    }

    public void setTelNo(int telNo) {
        this.telNo = telNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
