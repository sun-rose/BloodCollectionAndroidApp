package com.example.bloodcollectionandroidapp.FindDonorFragment;

public class Donor {


    public Donor(String fromTakerName, String date, String location, String bloodType) {
        this.fromTakerName = fromTakerName;
        this.bloodType = bloodType;
        this.location = location;
        this.date = date;
    }

    public Donor() {
    }

    private String user_mode;
    private String user_id;
    private String toDonorName;

    public Donor(String fromTakerName, String toDonorName, String date, String location, String bloodType ) {
        this.toDonorName = toDonorName;
        this.date = date;
        this.location = location;
        this.bloodType = bloodType;
        this.fromTakerName = fromTakerName;
    }

    private String date;
    private String location;
    private String bloodType;
    private String fromTakerName;




    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_mode() {
        return user_mode;
    }

    public void setUser_mode(String user_mode) {
        this.user_mode = user_mode;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getToDonorName() {
        return toDonorName;
    }

    public void setToDonorName(String toDonorName) {
        this.toDonorName = toDonorName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getFromTakerName() {
        return fromTakerName;
    }

    public void setFromTakerName(String fromTakerName) {
        this.fromTakerName = fromTakerName;
    }








}
